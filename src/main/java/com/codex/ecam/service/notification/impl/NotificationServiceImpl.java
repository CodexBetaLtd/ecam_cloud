package com.codex.ecam.service.notification.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import com.codex.ecam.dto.biz.notification.server.NotificationServerDTO;
import com.codex.ecam.dto.biz.notification.server.NotificationServerType;
import com.codex.ecam.exception.admin.UserException;
import com.codex.ecam.exception.setting.notification.NotificationException;
import com.codex.ecam.mappers.admin.NotificationMapper;
import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.model.biz.notification.NotificationRecipientUser;
import com.codex.ecam.params.VelocityMail;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.notification.NotificationResult;
import com.codex.ecam.service.notification.api.NotificationService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.KafkaUtil;
import com.codex.ecam.util.VelocityEmailSender;
import com.codex.ecam.util.search.biz.NotificationSearchPropertyMapper;

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
	@Autowired
	Environment environment; 

	private NotificationDTO findDTOById(Integer id) throws NotificationException {
		try {
			return NotificationMapper.getInstance().domainToDto(findEntityById(id));
		} catch (Exception e) {
			throw new NotificationException("ERROR! Notification mapper not worked!");
		}
	}

	private Notification findEntityById(Integer id) throws NotificationException {
		try {
			return notificationDao.findOne(id);
		} catch (Exception e) {
			throw new NotificationException("ERROR! Notification entity FETCH not completed.!");
		}
	}

	@Override
	public NotificationResult newNotification() {
		NotificationResult result = new NotificationResult(new Notification(), new NotificationDTO());
		try {
			newNotification(result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification create operation completed.");
		} catch (NotificationException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Notification NOT created.");
		}
		return result;
	}

	@Override
	public NotificationResult openMessage(Integer notificationId) {
		NotificationResult result = findNotificationById(notificationId);
		updateNotificationOpenStatus(result);
		result.setResultStatusSuccess();
		result.addToMessageList("SUCCESS! Notification viewed");
		return result;
	}

	private void updateNotificationOpenStatus(NotificationResult result) {
		if (result.getDtoEntity().getId() != null && AuthenticationUtil.getAuthenticatedUser() != null) {
			notificationDao.updateOpenStatusNotification(AuthenticationUtil.getAuthenticatedUser().getId(),
					result.getDtoEntity().getId());

		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void newNotification(NotificationResult result) throws NotificationException {
		if ((result.getDtoEntity().getBusinessId() != null) && (result.getDtoEntity().getBusinessId() > 0)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		} else if (!AuthenticationUtil.isAuthUserAdminLevel()) {
			result.getDomainEntity().setBusiness(AuthenticationUtil.getLoginUserBusiness());
		}
		if ((result.getDtoEntity().getSiteId() != null) && (result.getDtoEntity().getSiteId() > 0)) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		} else if (AuthenticationUtil.isAuthUserGeneralLevel()) {
			result.getDomainEntity().setSite(AuthenticationUtil.getLoginSite().getSite());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public NotificationResult saveNotification(NotificationDTO dto) {
		NotificationResult result = new NotificationResult(new Notification(), dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList("SUCCESS! Notification SAVE operation completed.");
			result.setResultStatusSuccess();
		} catch (NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Notification SAVE operation not completed");
			logger.error(ex.getMessage());
		} catch (UserException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(
					"FAILED! Notification SAVE operation not completed due to send/received user failure.");
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
		setSendUser(result);
		setReceivedUsers(result);
		notificationDao.save(result.getDomainEntity());
		pushToKafkaSever(result);
	}

	private void pushToKafkaSever(NotificationResult result) {
		for (NotificationRecipientUser recipientUser : result.getDomainEntity().getRecipients()) {
			if(result.getDtoEntity().getIsSystemMessage()){
				KafkaUtil.getInstance(environment).sendKafakaNotification(result, recipientUser);
			}else{
				KafkaUtil.getInstance(environment).sendKafakaMessage(result, recipientUser);
			}
		}
	}

	private void setBusinessSite(NotificationResult result) throws NotificationException {
		if ((result.getDtoEntity().getBusinessId() != null) && (result.getDtoEntity().getBusinessId() > 0)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		} else {
			result.getDomainEntity().setBusiness(AuthenticationUtil.getLoginUserBusiness());
		}
		if ((result.getDtoEntity().getSiteId() != null) && (result.getDtoEntity().getSiteId() > 0)) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		} else if (AuthenticationUtil.isAuthUserGeneralLevel()) {
			result.getDomainEntity().setSite(AuthenticationUtil.getLoginSite().getSite());
		}
	}

	private void setNotificationType(NotificationResult result) throws NotificationException {
		result.getDomainEntity().setNotificationType(NotificationType.OUTBOX_NOTIFICATION);
	}

	private void setReceivedUser(NotificationResult result) throws UserException {
		if (result.getDtoEntity().getReceivedUserId() != null) {
			// result.getDomainEntity().setReceiver(userDao.findOne(result.getDtoEntity().getReceivedUserId()));
		}
	}

	private void setSendUser(NotificationResult result) throws UserException {
		result.getDomainEntity().setSender(AuthenticationUtil.getAuthenticatedUser());
	}

	private void setReceivedUsers(NotificationResult result) {
		String userIds = result.getDtoEntity().getReceiverName();
		String[] itemIds = userIds.split(",");
		List<Integer> userList = Arrays.asList(itemIds).stream().map(Integer::parseInt).collect(Collectors.toList());
		Set<NotificationRecipientUser> notificationRecipientUsers = new HashSet();
		for (Integer userid : userList) {
			NotificationRecipientUser recipientUser = new NotificationRecipientUser();
			if(result.getDtoEntity().getIsSystemMessage()){
				recipientUser.setId(null);
				recipientUser.setVersion(null);
				recipientUser.setIsDeleted(Boolean.FALSE);
				recipientUser.setIsOpen(Boolean.FALSE);
				recipientUser.setIsSystemMessage(Boolean.TRUE);
				recipientUser.setIsPopup(Boolean.FALSE);
				recipientUser.setRecipient(userDao.findOne(userid));
				recipientUser.setNotification(result.getDomainEntity());
			}else{
				recipientUser.setId(null);
				recipientUser.setVersion(null);
				recipientUser.setIsDeleted(Boolean.FALSE);
				recipientUser.setIsOpen(Boolean.FALSE);
				recipientUser.setIsPopup(Boolean.FALSE);
				recipientUser.setRecipient(userDao.findOne(userid));
				recipientUser.setNotification(result.getDomainEntity());
				recipientUser.setIsSystemMessage(Boolean.FALSE);

			}

			notificationRecipientUsers.add(recipientUser);
		}
		result.getDomainEntity().setRecipients(notificationRecipientUsers);
		// createPurchaseOrderFromRFQItems(itemList, supplierList);
		// return null;
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
		NotificationResult result = new NotificationResult(notification, new NotificationDTO());
		try {
			createInboxMail(notification);
			result.addToMessageList("SUCCESS! Email notification send operation complete!");
			result.setResultStatusSuccess();
		} catch (NotificationException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Email notification send error!");
		}
		return result;
	}

	private void createInboxMail(Notification notification) throws NotificationException {
		VelocityMail velocityMail = new VelocityMail();
		// velocityMail.getModel().put("user",
		// notification.getReceiver().getFullName());
		velocityMail.getModel().put("notificationFrom", notification.getSender().getFullName());
		// velocityMail.getModel().put("notificationTo",
		// notification.getReceiver().getFullName());
		velocityMail.getModel().put("notificationSubject", notification.getSubject());
		velocityMail.getModel().put("notificationContent", notification.getContent());
		velocityMail.setSubject("[FOCUS-NOTIFICATION] - You have received inbox notification");
		// velocityMail.setTo(notification.getReceiver().getEmailAddress());
		velocityMail.setVmTemplate("notificationInbox");
		velocityEmailService.sendEmail(velocityMail);
	}

	@Override
	public NotificationResult saveSystemNotification(NotificationDTO dto) {
		NotificationResult result = new NotificationResult(new Notification(), dto);
		try {
			saveOrUpdateSystemNotification(result);
			result.addToMessageList("SUCCESS! System Notification SAVE operation completed.");
			result.setResultStatusSuccess();
		} catch (NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! System Notification SAVE operation not completed");
			logger.error(ex.getMessage());
		} catch (UserException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(
					"FAILED! System Notification SAVE operation not completed due to send/received user failure.");
			logger.error(ex.getMessage());
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdateSystemNotification(NotificationResult result) throws NotificationException, UserException {
		NotificationMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setSystemNotificationDomainData(result);
	}

	private void setSystemNotificationDomainData(NotificationResult result)
			throws NotificationException, UserException {
		setBusinessSite(result);
		result.getDomainEntity().setNotificationType(NotificationType.INBOX_NOTIFICATION);
		result.getDomainEntity().setSystemMessage(Boolean.TRUE);
		setReceivedUser(result);
		notificationDao.save(result.getDomainEntity());
	}

	@Override
	public NotificationResult toggleTrashedNotification(Boolean isTrashed, Integer id) throws Exception {
		NotificationResult result = new NotificationResult(findEntityById(id), new NotificationDTO());
		try {
			setToggleTrashedData(result, isTrashed);
			result.addToMessageList(isTrashed == Boolean.TRUE ? "SUCCESS! Notification moved to Trash Bin."
					: "SUCCESS! Trashed notification restored.");
			result.setResultStatusSuccess();
		} catch (NotificationException ex) {
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
		NotificationResult result = new NotificationResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification operation deleted operation completed.");
		} catch (DataIntegrityViolationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Notification Already Used. Cannot delete.");
			logger.error(ex.getMessage());
		} catch (NotificationException ex) {
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
		NotificationResult result = new NotificationResult(null, null);
		try {
			setReplyNotification(id, result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification fetch operation completed.");
		} catch (NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Notification fetch operation failed.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	private void setReplyNotification(Integer id, NotificationResult result) throws NotificationException {
		NotificationDTO notificationDTO = findDTOById(id);
		result.setDtoEntity(new NotificationDTO());
		// newNotification(result);
		result.getDtoEntity().setSentUserId(notificationDTO.getReceivedUserId());
		result.getDtoEntity().setSentUserName(notificationDTO.getReceivedUserName());
		result.getDtoEntity().setSenderName(notificationDTO.getReceiverName());

		result.getDtoEntity().setReceivedUserId(notificationDTO.getSentUserId());
		result.getDtoEntity().setReceivedUserName(notificationDTO.getSentUserName());
		result.getDtoEntity().setReceiverName(notificationDTO.getSenderName());

		result.getDtoEntity().setSubject(" [Reply] - ".concat(notificationDTO.getSubject()));
		result.getDtoEntity()
				.setContent(" ---------- LAST Notification ------------ <br/>  ".concat(notificationDTO.getContent()));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NotificationException.class)
	public NotificationResult forwardNotification(Integer id) {
		NotificationResult result = new NotificationResult(null, null);
		try {
			setForwardNotification(id, result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification fetch operation completed.");
		} catch (NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Notification fetch operation failed.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	private void setForwardNotification(Integer id, NotificationResult result) throws NotificationException {
		NotificationDTO notificationDTO = findDTOById(id);
		result.setDtoEntity(new NotificationDTO());
		// newNotification(result);
		result.getDtoEntity().setSentUserId(notificationDTO.getSentUserId());
		result.getDtoEntity().setSentUserName(notificationDTO.getSentUserName());
		result.getDtoEntity().setSenderName(notificationDTO.getSenderName());

		result.getDtoEntity().setReceivedUserId(null);
		result.getDtoEntity().setReceivedUserName("");
		result.getDtoEntity().setReceiverName("");

		result.getDtoEntity().setSubject(" [Forward] - ".concat(notificationDTO.getSubject()));
		result.getDtoEntity()
				.setContent(" ---------- LAST Notification ------------ <br/>".concat(notificationDTO.getContent()));
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NotificationException.class)
	private void deleteEntityById(Integer id) throws NotificationException {
		notificationDao.delete(id);
	}

	@Override
	public NotificationResult findNotificationById(Integer id) {
		NotificationResult result = new NotificationResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Notification fetch operation completed.");
		} catch (NotificationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Notification fetch operation failed.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Override
	public NotificationViewDTO notificationUtility() {
		NotificationViewDTO notificationViewDTO = new NotificationViewDTO();
		notificationViewDTO.setInboxItemCount(
				notificationDao.findInboxUnreadItemCount(AuthenticationUtil.getAuthenticatedUser().getId()));
		return notificationViewDTO;
	}

	@Override
	public List<NotificationDTO> findAllNotification(NotificationType notificationType) {
		try {
			List<Notification> notifications = new ArrayList<>();
			notifications = notificationDao.findAll(notificationSpecification(notificationType));
			return NotificationMapper.getInstance().domainToDTOList(notifications);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public DataTablesOutput<NotificationDTO> findAllNotification(FocusDataTablesInput input,
			NotificationType notificationType) throws Exception {
		try {
			NotificationSearchPropertyMapper.getInstance().generateDataTableInput(input);
			DataTablesOutput<Notification> domainOut = notificationDao.findAll(input,
					notificationSpecification(notificationType));
			return NotificationMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}

	}

	@Override
	public DataTablesOutput<NotificationDTO> findAllInboxNotificationByUser(FocusDataTablesInput input)
			throws Exception {
		try {
			// NotificationSearchPropertyMapper.getInstance().generateDataTableInput(input);
			List<Notification> domainOut = notificationDao
					.findAllInboxByUser(AuthenticationUtil.getAuthenticatedUser().getId());
			DataTablesOutput<Notification> domainDataTableOut = new DataTablesOutput<Notification>();
			domainDataTableOut.setData(domainOut);
			return NotificationMapper.getInstance().domainToDTODataTablesOutput(domainDataTableOut);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
		
	}
	public DataTablesOutput<NotificationDTO> findAllSystemInboxNotificationByUser(FocusDataTablesInput input)
			throws Exception {
		try {
			// NotificationSearchPropertyMapper.getInstance().generateDataTableInput(input);
			List<Notification> domainOut = notificationDao
					.findAllInboxSystemByUser(AuthenticationUtil.getAuthenticatedUser().getId());
			DataTablesOutput<Notification> domainDataTableOut = new DataTablesOutput<Notification>();
			domainDataTableOut.setData(domainOut);
			return NotificationMapper.getInstance().domainToDTODataTablesOutput(domainDataTableOut);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}

	}

	public List<NotificationServerDTO> findAllListInboxNotificationByUser() throws Exception {
		List<NotificationServerDTO> notificationServerDTOs = new ArrayList<>();
		try {

			// NotificationSearchPropertyMapper.getInstance().generateDataTableInput(input);
			List<Notification> domainOut = notificationDao
					.findAllNotOpenInboxByUser(AuthenticationUtil.getAuthenticatedUser().getId());
			for (Notification notification : domainOut) {
				NotificationServerDTO notificationServerDTO = new NotificationServerDTO();
				notificationServerDTO.setId(notification.getId().longValue());
				notificationServerDTO.setNotifyTime(notification.getCreatedDate());
				notificationServerDTO.setSendUser(notification.getSender().getUserCredential().getUserName());
				notificationServerDTO.setUserId(AuthenticationUtil.getAuthenticatedUser().getId());
				notificationServerDTO
						.setUserName(AuthenticationUtil.getAuthenticatedUser().getUserCredential().getUserName());
				notificationServerDTO.setContent(notification.getContent());
				notificationServerDTO.setSubject(notification.getSubject());
				notificationServerDTO.setType(NotificationServerType.INFO);
				notificationServerDTOs.add(notificationServerDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return notificationServerDTOs;
	}
	
	
	public List<NotificationServerDTO> findAllListSystemNotificationByUser() throws Exception {
		List<NotificationServerDTO> notificationServerDTOs = new ArrayList<>();
		try {

			// NotificationSearchPropertyMapper.getInstance().generateDataTableInput(input);
			List<Notification> domainOut = notificationDao
					.findAllNotInboxSystemByUser(AuthenticationUtil.getAuthenticatedUser().getId());
			for (Notification notification : domainOut) {
				NotificationServerDTO notificationServerDTO = new NotificationServerDTO();
				notificationServerDTO.setId(notification.getId().longValue());
				notificationServerDTO.setNotifyTime(notification.getCreatedDate());
				notificationServerDTO.setSendUser(notification.getSender().getUserCredential().getUserName());
				notificationServerDTO.setUserId(AuthenticationUtil.getAuthenticatedUser().getId());
				notificationServerDTO
						.setUserName(AuthenticationUtil.getAuthenticatedUser().getUserCredential().getUserName());
				notificationServerDTO.setContent(notification.getContent());
				notificationServerDTO.setSubject(notification.getSubject());
				notificationServerDTO.setType(NotificationServerType.INFO);
				notificationServerDTOs.add(notificationServerDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return notificationServerDTOs;
	}

	private Specification<Notification> inboxNotificationSpecification() {
		Specification<Notification> specification = (root, query, cb) -> {
			Join<Notification, NotificationRecipientUser> recipientUser = root.joinList("recipients");
			query.distinct(true);
			;
			return cb.equal(recipientUser.get("recipient").get("id"), 53);
		};
		return specification;
	}

	private Specification<Notification> notificationSpecification(NotificationType notificationType) {
		Specification<Notification> spec = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (AuthenticationUtil.isAuthUserAdminLevel()) {

			} else {
				predicates.add(
						cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId()));
			}
			if (notificationType.equals(NotificationType.INBOX_NOTIFICATION)) {
				predicates.add(
						cb.equal(root.get("receiver").get("id"), AuthenticationUtil.getAuthenticatedUser().getId()));
				predicates.add(cb.notEqual(root.get("isTrashed"), Boolean.TRUE));
				predicates.add(cb.equal(root.get("notificationType"), notificationType));
			} else if (notificationType.equals(NotificationType.OUTBOX_NOTIFICATION)) {
				predicates
						.add(cb.equal(root.get("sender").get("id"), AuthenticationUtil.getAuthenticatedUser().getId()));
				predicates.add(cb.notEqual(root.get("isTrashed"), Boolean.TRUE));
				predicates.add(cb.equal(root.get("notificationType"), notificationType));
			} else if (notificationType.equals(NotificationType.TRASH_NOTIFICATION)) {
				predicates.add(cb.equal(root.get("isTrashed"), Boolean.TRUE));
				predicates.add(
						cb.equal(root.get("updatedUser").get("id"), AuthenticationUtil.getAuthenticatedUser().getId()));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return spec;
	}

}
