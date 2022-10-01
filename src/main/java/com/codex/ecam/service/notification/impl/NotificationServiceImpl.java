package com.codex.ecam.service.notification.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.NotificationType;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.biz.NotificationDao;
import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.dto.biz.notification.NotificationViewDTO;
import com.codex.ecam.exception.admin.UserException;
import com.codex.ecam.exception.setting.notification.NotificationException;
import com.codex.ecam.mappers.admin.NotificationMapper;
import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.params.VelocityMail;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.notification.NotificationResult;
import com.codex.ecam.service.notification.api.NotificationService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.VelocityEmailSender;
import com.codex.ecam.util.search.biz.NotificationSearchPropertyMapper;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

	private final static Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationDao notificationDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private VelocityEmailSender velocityEmailService;

	private NotificationDTO findDTOById(Integer id) throws NotificationException {
		try {
			return NotificationMapper.getInstance().domainToDto(findEntityById(id));
		} catch (final Exception e) {
			throw new NotificationException("ERROR! Notification mapper not worked!");
		}
	}

	private Notification findEntityById(Integer id) throws NotificationException {
		try {
			return notificationDao.findOne(id);
		} catch (final Exception e) {
			throw new NotificationException("ERROR! Notification entity FETCH not completed.!");
		}
	}

	@Override
	public NotificationResult newNotification() {
		final NotificationResult result = new NotificationResult(new Notification(), new NotificationDTO());
		try {
			newNotification(result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification create operation completed.");
		} catch (final NotificationException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Notification NOT created.");
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void newNotification(NotificationResult result) throws NotificationException {
		if (result.getDtoEntity().getBusinessId() != null && result.getDtoEntity().getBusinessId() > 0) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		} else if (!AuthenticationUtil.isAuthUserAdminLevel()) {
			result.getDomainEntity().setBusiness(AuthenticationUtil.getLoginUserBusiness());
		}
		if (result.getDtoEntity().getSiteId() != null && result.getDtoEntity().getSiteId() > 0) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		} else if (AuthenticationUtil.isAuthUserGeneralLevel()) {
			result.getDomainEntity().setSite(AuthenticationUtil.getLoginSite().getSite());
		}
	}

	@Override
	public NotificationResult saveNotification(NotificationDTO dto) {
		final NotificationResult result = new NotificationResult(new Notification(), dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList("SUCCESS! Notification SAVE operation completed.");
			result.setResultStatusSuccess();
		} catch (final NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Notification SAVE operation not completed");
			logger.error(ex.getMessage());
		} catch (final UserException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Notification SAVE operation not completed due to send/received user failure.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(NotificationResult result) throws NotificationException, UserException {
		NotificationMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setDomainData(result);
	}

	private void setDomainData(NotificationResult result) throws NotificationException, UserException {
		setBusinessSite(result);
		setNotificationType(result);
		setReceivedUser(result);
		setSendUser(result);
		notificationDao.save(result.getDomainEntity());
		try {
			final Notification clone = (Notification) BeanUtils.cloneBean(notificationDao.findOne(result.getDomainEntity().getId()));
			notificationDao.save(setReceivedUserNotification(clone));
		} catch (final Exception e) {
			e.printStackTrace();
			throw new NotificationException("ERROR! Notification clone failed.");
		}
	}

	private void setBusinessSite(NotificationResult result) throws NotificationException {
		if (result.getDtoEntity().getBusinessId() != null && result.getDtoEntity().getBusinessId() > 0) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		} else {
			result.getDomainEntity().setBusiness(AuthenticationUtil.getLoginUserBusiness());
		}
		if (result.getDtoEntity().getSiteId() != null && result.getDtoEntity().getSiteId() > 0) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		} else {
			try {
				if (AuthenticationUtil.isAuthUserGeneralLevel()) {
					result.getDomainEntity().setSite(AuthenticationUtil.getLoginSite().getSite());
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setNotificationType(NotificationResult result) throws NotificationException {
		result.getDomainEntity().setNotificationType(NotificationType.OUTBOX_NOTIFICATION);
	}

	private void setReceivedUser(NotificationResult result) throws UserException {
		if (result.getDtoEntity().getReceivedUserId() != null) {
			result.getDomainEntity().setReceiver(userDao.findOne(result.getDtoEntity().getReceivedUserId()));
		}
	}

	private void setSendUser(NotificationResult result) throws UserException {
		result.getDomainEntity().setSender(AuthenticationUtil.getAuthenticatedUser());
	}

	private Notification setReceivedUserNotification(Notification cloneDomain) throws NotificationException {
		cloneDomain.setId(null);
		cloneDomain.setVersion(null);
		cloneDomain.setIsDeleted(Boolean.FALSE);
		cloneDomain.setIsOpen(Boolean.FALSE);
		cloneDomain.setIsPopup(Boolean.TRUE);
		cloneDomain.setNotificationType(NotificationType.INBOX_NOTIFICATION);
		return cloneDomain;
	}


	@Override
	public NotificationResult fireInboxNotification(Notification notification) {
		final NotificationResult result = new NotificationResult(notification, new NotificationDTO());
		try {
			createInboxMail(notification);
			result.addToMessageList("SUCCESS! Email notification send operation complete!");
			result.setResultStatusSuccess();
		} catch (final NotificationException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Email notification send error!");
		}
		return result;
	}

	private void createInboxMail(Notification notification) throws NotificationException {
		final VelocityMail velocityMail = new VelocityMail();
		velocityMail.getModel().put("user", notification.getReceiver().getFullName());
		velocityMail.getModel().put("notificationFrom", notification.getSender().getFullName());
		velocityMail.getModel().put("notificationTo", notification.getReceiver().getFullName());
		velocityMail.getModel().put("notificationSubject", notification.getSubject());
		velocityMail.getModel().put("notificationContent", notification.getContent());
		velocityMail.setSubject("[FOCUS-NOTIFICATION] - You have received inbox notification");
		velocityMail.setTo(notification.getReceiver().getEmailAddress());
		velocityMail.setVmTemplate("notificationInbox");
		velocityEmailService.sendEmail(velocityMail);
	}




	@Override
	public NotificationResult saveSystemNotification(NotificationDTO dto) {
		final NotificationResult result = new NotificationResult(new Notification(), dto);
		try {
			saveOrUpdateSystemNotification(result);
			result.addToMessageList("SUCCESS! System Notification SAVE operation completed.");
			result.setResultStatusSuccess();
		} catch (final NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! System Notification SAVE operation not completed");
			logger.error(ex.getMessage());
		} catch (final UserException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! System Notification SAVE operation not completed due to send/received user failure.");
			logger.error(ex.getMessage());
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdateSystemNotification(NotificationResult result) throws NotificationException, UserException {
		NotificationMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setSystemNotificationDomainData(result);
	}

	private void setSystemNotificationDomainData(NotificationResult result) throws NotificationException, UserException {
		setBusinessSite(result);
		result.getDomainEntity().setNotificationType(NotificationType.INBOX_NOTIFICATION);
		result.getDomainEntity().setSystemMessage(Boolean.TRUE);
		setReceivedUser(result);
		notificationDao.save(result.getDomainEntity());
	}

	@Override
	public NotificationResult toggleTrashedNotification(Boolean isTrashed, Integer id) throws Exception {
		final NotificationResult result = new NotificationResult(findEntityById(id), new NotificationDTO());
		try {
			setToggleTrashedData(result, isTrashed);
			result.addToMessageList(isTrashed == Boolean.TRUE ? "SUCCESS! Notification moved to Trash Bin." : "SUCCESS! Trashed notification restored.");
			result.setResultStatusSuccess();
		} catch (final NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Notification Trash/Restore operation not completed");
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void setToggleTrashedData(NotificationResult result, Boolean isTrashed) throws NotificationException {
		result.getDomainEntity().setIsTrashed(isTrashed);
		notificationDao.save(result.getDomainEntity());
	}


	@Override
	public NotificationResult deleteNotification(Integer id) {
		final NotificationResult result = new NotificationResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification operation deleted operation completed.");
		} catch (final DataIntegrityViolationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Notification Already Used. Cannot delete.");
			logger.error(ex.getMessage());
		} catch (final NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Notification delete operation not completed");
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NotificationException.class)
	public NotificationResult replyNotification(Integer id) {
		final NotificationResult result = new NotificationResult(null, null);
		try {
			setReplyNotification(id, result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification fetch operation completed.");
		} catch (final NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Notification fetch operation failed.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	private void setReplyNotification(Integer id, NotificationResult result) throws NotificationException {
		final NotificationDTO notificationDTO = findDTOById(id);
		result.setDtoEntity(new NotificationDTO());
		//        newNotification(result);
		result.getDtoEntity().setSentUserId(notificationDTO.getReceivedUserId());
		result.getDtoEntity().setSentUserName(notificationDTO.getReceivedUserName());
		result.getDtoEntity().setSenderName(notificationDTO.getReceiverName());

		result.getDtoEntity().setReceivedUserId(notificationDTO.getSentUserId());
		result.getDtoEntity().setReceivedUserName(notificationDTO.getSentUserName());
		result.getDtoEntity().setReceiverName(notificationDTO.getSenderName());

		result.getDtoEntity().setSubject(" [Reply] - ".concat(notificationDTO.getSubject()));
		result.getDtoEntity().setContent(" ---------- LAST Notification ------------ <br/>  ".concat(notificationDTO.getContent()));
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NotificationException.class)
	public NotificationResult forwardNotification(Integer id) {
		final NotificationResult result = new NotificationResult(null, null);
		try {
			setForwardNotification(id, result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification fetch operation completed.");
		} catch (final NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Notification fetch operation failed.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	private void setForwardNotification(Integer id, NotificationResult result) throws NotificationException {
		final NotificationDTO notificationDTO = findDTOById(id);
		result.setDtoEntity(new NotificationDTO());
		//        newNotification(result);
		result.getDtoEntity().setSentUserId(notificationDTO.getSentUserId());
		result.getDtoEntity().setSentUserName(notificationDTO.getSentUserName());
		result.getDtoEntity().setSenderName(notificationDTO.getSenderName());

		result.getDtoEntity().setReceivedUserId(null);
		result.getDtoEntity().setReceivedUserName("");
		result.getDtoEntity().setReceiverName("");

		result.getDtoEntity().setSubject(" [Forward] - ".concat(notificationDTO.getSubject()));
		result.getDtoEntity().setContent(" ---------- LAST Notification ------------ <br/>".concat(notificationDTO.getContent()));
	}



	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NotificationException.class)
	private void deleteEntityById(Integer id) throws NotificationException {
		notificationDao.delete(id);
	}


	@Override
	public NotificationResult findNotificationById(Integer id) {

		final NotificationResult result = new NotificationResult(null, null);

		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification fetch operation completed.");
		} catch (final NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Notification fetch operation failed.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public NotificationResult previewById(Integer id) throws Exception{

		final NotificationResult result = new NotificationResult(null, null);

		try {
			setDomainData(id);
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification fetch operation completed.");
		} catch (final NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Notification fetch operation failed.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	private void setDomainData(Integer id) throws NotificationException {
		final Notification domain = findEntityById(id);
		if (!domain.getIsOpen()) {
			domain.setIsOpen(true);
			notificationDao.save(domain);
		}
	}

	@Override
	public NotificationViewDTO notificationUtility() {
		final NotificationViewDTO notificationViewDTO = new NotificationViewDTO();
		notificationViewDTO.setInboxItemCount(notificationDao.findInboxUnreadItemCount(AuthenticationUtil.getAuthenticatedUser().getId()));
		return notificationViewDTO;
	}

	@Override
	public List<NotificationDTO> findAllNotification(NotificationType notificationType) {
		try {
			List<Notification> notifications = new ArrayList<>();
			notifications = notificationDao.findAll(notificationSpecification(notificationType));
			return NotificationMapper.getInstance().domainToDTOList(notifications);
		} catch (final Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public DataTablesOutput<NotificationDTO> findAllNotification(FocusDataTablesInput input, NotificationType notificationType) throws Exception {
		try {
			NotificationSearchPropertyMapper.getInstance().generateDataTableInput(input);
			final DataTablesOutput<Notification> domainOut = notificationDao.findAll(input, notificationSpecification(notificationType));
			return NotificationMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}

	}

	private Specification<Notification> notificationSpecification(NotificationType notificationType) {
		final Specification<Notification> spec = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			if (AuthenticationUtil.isAuthUserAdminLevel()) {

			} else {
				predicates.add(cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId()));
			}
			if (notificationType.equals(NotificationType.INBOX_NOTIFICATION)) {
				predicates.add(cb.equal(root.get("receiver").get("id"), AuthenticationUtil.getAuthenticatedUser().getId()));
				predicates.add(cb.notEqual(root.get("isTrashed"), Boolean.TRUE));
				predicates.add(cb.equal(root.get("notificationType"), notificationType));
			} else if (notificationType.equals(NotificationType.OUTBOX_NOTIFICATION)) {
				predicates.add(cb.equal(root.get("sender").get("id"), AuthenticationUtil.getAuthenticatedUser().getId()));
				predicates.add(cb.notEqual(root.get("isTrashed"), Boolean.TRUE));
				predicates.add(cb.equal(root.get("notificationType"), notificationType));
			} else if (notificationType.equals(NotificationType.TRASH_NOTIFICATION)) {
				predicates.add(cb.equal(root.get("isTrashed"), Boolean.TRUE));
				predicates.add(cb.equal(root.get("updatedUser").get("id"), AuthenticationUtil.getAuthenticatedUser().getId()));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return spec;
	}

	@Override
	public Integer getInboxUnreadCount() throws Exception {
		final Specification<Notification> spec = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("receiver").get("id"), AuthenticationUtil.getAuthenticatedUser().getId()));
			predicates.add(cb.equal(root.get("isOpen"), Boolean.FALSE));
			predicates.add(cb.equal(root.get("notificationType"), NotificationType.INBOX_NOTIFICATION));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return (int) notificationDao.count(spec);
	}

}
