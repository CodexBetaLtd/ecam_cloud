package com.codex.ecam.service.admin.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.CertificationDao;
import com.codex.ecam.dao.admin.CurrencyDao;
import com.codex.ecam.dao.admin.UserCredentialDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.admin.UserGroupDao;
import com.codex.ecam.dao.admin.UserJobTitleDao;
import com.codex.ecam.dao.admin.UserSkillLevelDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.UserCertificationDTO;
import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.dto.admin.cmmssetting.UserSiteDTO;
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;
import com.codex.ecam.mappers.admin.UserCertificationMapper;
import com.codex.ecam.mappers.admin.UserMapper;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.admin.UserCertification;
import com.codex.ecam.model.admin.UserCredential;
import com.codex.ecam.model.admin.UserSite;
import com.codex.ecam.model.admin.UserSiteGroup;
import com.codex.ecam.params.VelocityMail;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.UserResult;
import com.codex.ecam.service.admin.api.UserService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.FileDownloadUtil;
import com.codex.ecam.util.VelocityEmailSender;
import com.codex.ecam.util.aws.AmazonS3ObjectUtil;
import com.codex.ecam.util.search.admin.UserSearchPropertyMapper;

@Service
public class UserServiceImpl implements UserService {

	private final String USER_DEFAULT_IMAGE = "/resources/images/user-default.png";

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserGroupDao userGroupDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private UserCredentialDao userCredentialDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CertificationDao certificationDao;

	@Autowired
	private UserJobTitleDao userJobTitelDao;

	@Autowired
	private UserSkillLevelDao userSkillLevelDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private VelocityEmailSender velocityEmailSender;

	@Autowired
	private AmazonS3ObjectUtil amazonS3ObjectUtil;

	private void setUserData(User domain, UserDTO dto) throws Exception {
		setAssignedUserSites(domain, dto);
	}

	@Override
	public UserResult delete(Integer id) {
		final UserResult result = new UserResult(null, null);
		try {
			userDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("User Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("User Already Assigned. Please Remove from Assigned User and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserResult deleteMultiple(Integer[] ids) throws Exception {
		final UserResult result = new UserResult(null, null);
		try {
			for (final Integer id : ids) {
				userDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("User(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("User(s) Already Assigned. Please Remove from Assigned User and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public UserResult save(UserDTO dto) throws Exception {
		final UserResult result = createUserResult(dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("User Already updated. Please Reload User.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(UserResult result) throws Exception {
		UserMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setUserData(result);
		userDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private UserResult createUserResult(UserDTO dto) {
		UserResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new UserResult(userDao.findOne(dto.getId()), dto);
		} else {
			result = new UserResult(new User(), dto);
		}

		return result;
	}

	private String getMessageByAction(UserDTO dto) {
		if (dto.getId() == null) {
			return "User Added Successfully.";
		} else {
			return "User Updated Successfully.";
		}
	}

	private void setUserData(UserResult result) throws Exception {
		setBusiness(result);
		setUserSite(result);
		setUserCredentials(result);
		setUserCertification(result);
		setUserBusiness(result);
		setUserJobTitle(result);
		setUserSkillLevel(result);
		setUserCurrency(result.getDtoEntity(), result.getDomainEntity());
	}

	private void setBusiness(UserResult result) throws Exception {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setUserBusiness(UserResult result) throws Exception {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setUserJobTitle(UserResult result) {
		result.getDomainEntity().setUserJobTitel(userJobTitelDao.findById(result.getDtoEntity().getJobTitle()));
	}

	private void setUserSkillLevel(UserResult result) {
		result.getDomainEntity().setUserSkillLevel(userSkillLevelDao.findById(result.getDtoEntity().getSkillLevel()));
	}

	private void setUserCurrency(UserDTO dto, User domain) {
		if (dto.getCurrencyId() != null) {
			domain.setCurrency(currencyDao.findOne(dto.getCurrencyId()));
		}
	}

	private void setUserCredentials(UserResult result) {
		UserCredential userCredentials = new UserCredential();
		if (result.getDtoEntity().getId() != null && result.getDtoEntity().getId() > 0) {
			userCredentials = userCredentialDao.findOne(result.getDtoEntity().getUserCredentialDTO().getId());
			if (result.getDtoEntity().getChangePassword() == true) {
				userCredentials.setPassword(
						passwordEncoder.encode(result.getDtoEntity().getUserCredentialDTO().getPassword()));
				sendEmailToUser(result);
			}
			userCredentials.setUserName(result.getDtoEntity().getUserCredentialDTO().getUserName());
		} else {
			userCredentials = new UserCredential();
			setUserName(userCredentials, result);
			setPassword(userCredentials, result);
			userCredentials.setIsDeleted(Boolean.FALSE);
			sendEmailToUser(result);
		}
		userCredentials.setUser(result.getDomainEntity());
		result.getDomainEntity().setUserCredentials(userCredentials);
	}

	private void setUserName(UserCredential userCredential, UserResult result) {
		if (result.getDtoEntity().getUserCredentialDTO().getUserName() != null) {
			userCredential.setUserName(result.getDtoEntity().getUserCredentialDTO().getUserName());
		} else {
			userCredential.setUserName(getSystemUserName(result.getDtoEntity().getFullName()));
		}
	}

	private void setPassword(UserCredential userCredential, UserResult result) {
		if (result.getDtoEntity().getUserCredentialDTO().getPassword() != null) {
			userCredential
			.setPassword(passwordEncoder.encode(result.getDtoEntity().getUserCredentialDTO().getPassword()));
		} else {
			userCredential.setPassword(passwordEncoder.encode(getSystemPassword()));
		}
	}

	private String getSystemUserName(String fullName) {
		final String username[] = fullName.trim().split("\\s+");
		return username[0].toLowerCase();
	}

	@Override
	public String getSystemPassword() {
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$&";
		return RandomStringUtils.random(6, characters);
	}

	private void sendEmailToUser(UserResult result) {
		try {
			final VelocityMail velocityMail = new VelocityMail();
			velocityMail.getModel().put("user", result.getDtoEntity().getFullName());
			velocityMail.getModel().put("password", result.getDtoEntity().getUserCredentialDTO().getPassword());
			velocityMail.getModel().put("userName", result.getDtoEntity().getUserCredentialDTO().getUserName());
			velocityMail.setSubject("Login Information");
			velocityMail.setTo(result.getDtoEntity().getEmailAddress());
			velocityMail.setVmTemplate("usercredentials");
			velocityEmailSender.sendEmail(velocityMail);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	protected void setUserSiteGroups(UserSite userSite, UserSiteDTO dto) {
		final Set<UserSiteGroup> userSiteGroups = new HashSet<UserSiteGroup>();
		for (final UserGroupDTO userGroupId : dto.getSiteUserGroupDTOList()) {
			final UserSiteGroup userSiteGroup = new UserSiteGroup();
			userSiteGroup.setIsDeleted(Boolean.FALSE);
			userSiteGroup.setUserSite(userSite);
			userSiteGroup.setUserGroup(userGroupDao.findById(userGroupId.getId()));
			userSiteGroups.add(userSiteGroup);
		}
		userSite.setUserSiteGroups(userSiteGroups);
	}

	private void setUserSite(UserResult result) {
		final Set<UserSite> sites = new HashSet<>();
		for (final UserSiteDTO site : result.getDtoEntity().getUserSiteDTOList()) {
			UserSite userSite;

			if (site.getSiteId() != null) {
				if (result.getDomainEntity().getUserSites() != null
						&& result.getDomainEntity().getUserSites().size() > 0) {
					userSite = result.getDomainEntity().getUserSites().stream()
							.filter((x) -> x.getId().equals(site.getSiteId())).findAny().orElseGet(UserSite::new);
				} else {
					userSite = new UserSite();
				}
			} else {
				userSite = new UserSite();
			}
			userSite.setIsDeleted(Boolean.FALSE);
			setUserSiteGroups(userSite, site);
			userSite.setUser(result.getDomainEntity());
			userSite.setSite(assetDao.findOne(site.getSiteSiteId()));
			sites.add(userSite);
		}
		result.getDomainEntity().setUserSites(sites);
	}

	private void setUserCertification(UserResult result) throws Exception {
		final Set<UserCertification> userCertifications = new HashSet<UserCertification>();

		for (final UserCertificationDTO userCertificationDTO : result.getDtoEntity().getUseCertificationDTOs()) {

			UserCertification userCertification;

			if (userCertificationDTO.getId() != null) {
				userCertification = result.getDomainEntity().getUserCertifications().stream()
						.filter((x) -> x.getId().equals(userCertificationDTO.getId())).findAny()
						.orElseGet(UserCertification::new);
			} else {
				userCertification = new UserCertification();
			}

			UserCertificationMapper.getInstance().dtoToDomain(userCertificationDTO, userCertification);
			userCertification
			.setCertification(certificationDao.findById(userCertificationDTO.getCertificationTypeId()));
			userCertification.setUser(result.getDomainEntity());
			userCertifications.add(userCertification);
		}

		result.getDomainEntity().setUserCertifications(userCertifications);

	}

	private void setAssignedUserSites(User domain, UserDTO dto) {
		final List<Integer> assignedUserSites = domain.getUserSites().stream().map(e -> e.getSite().getId())
				.collect(Collectors.toList());
		dto.setAssignedUserSites(assignedUserSites);
	}

	@Override
	public UserDTO findById(Integer id) throws Exception {
		final User domain = userDao.findOne(id);
		if (domain != null) {
			final UserDTO dto = UserMapper.getInstance().domainToDto(domain);
			setUserData(domain, dto);
			return dto;
		}
		return null;
	}

	@Override
	public UserDTO findUserById(Integer userId) throws Exception {
		final User user = findEntityById(userId);
		final UserDTO dto = UserMapper.getInstance().domainToDto(user);
		final List<Integer> assignedUserSites = user.getUserSites().stream().map(e -> e.getSite().getId())
				.collect(Collectors.toList());
		dto.setAssignedUserSites(assignedUserSites);

		return dto;
	}

	@Override
	public DataTablesOutput<UserDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<User> domainOut;

		UserSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = userDao.findAll(input);
		} else {
			final Specification<User> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = userDao.findAll(input, specification);
		}

		final DataTablesOutput<UserDTO> out = UserMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public UserDTO findUser(UserDTO dto) {
		try {
			return UserMapper.getInstance().domainToDto(userDao.findOne(dto.getId()));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserDTO> findUserList() {
		try {
			return UserMapper.getInstance().domainToDTOList(userDao.findAll());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findByEmail(String email) throws Exception {
		final User user = userDao.findByEmail(email);
		return user;
	}

	@Override
	public User findEntityById(Integer id) {
		final User user = userDao.findOne(id);
		return user;
	}

	@Override
	public DataTablesOutput<UserDTO> findAllUsersByGroup(FocusDataTablesInput dataTablesInput, Integer groupId) {

		final Specification<User> specification = (root, query, cb) -> {
			final Join<User, UserSite> joinUserSite = root.joinList("userSites");
			final Join<UserSite, UserSiteGroup> joinUserSiteGroup = joinUserSite.joinList("userSiteGroups");
			query.distinct(true);
			return cb.equal(joinUserSiteGroup.get("userGroup").get("id"), groupId);
		};
		final DataTablesOutput<User> domainOut = userDao.findAll(dataTablesInput, specification);
		DataTablesOutput<UserDTO> out = null;
		try {
			out = UserMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public DataTablesOutput<UserDTO> findAllByBusiness(FocusDataTablesInput input, Integer id) {
		try {
			final DataTablesOutput<User> domainOut = userDao.findAll(input, findAllUserByBusinessSpec(id));
			return UserMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	@Override
	public DataTablesOutput<UserDTO> findAllUsersByLoggedUserBusiness(FocusDataTablesInput input) {
		try {
			DataTablesOutput<User> domainOut = new DataTablesOutput<>();
			if (!AuthenticationUtil.isAuthUserAdminLevel()) {
				domainOut = userDao.findAll(input,
						findAllUserByBusinessSpec(AuthenticationUtil.getLoginUserBusiness().getId()));
			} else {
				domainOut = userDao.findAll(input);
			}

			return UserMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	private Specification<User> findAllUserByBusinessSpec(Integer id) {
		final Specification<User> spec = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			//			if (!AuthenticationUtil.isAuthUserAdminLevel()) {
			predicates.add(cb.equal(root.get("business").get("id"), id));
			//			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return spec;
	}

	@Override
	public DataTablesOutput<UserDTO> findUsersBySite(FocusDataTablesInput dataTablesInput, Integer id) {
		final Specification<User> specification = (root, query, cb) -> {
			final Join<User, UserSite> joinUserSite = root.joinList("userSites");
			query.distinct(true);
			return cb.equal(joinUserSite.get("site").get("id"), id);
		};
		final DataTablesOutput<User> domainOut = userDao.findAll(dataTablesInput, specification);
		DataTablesOutput<UserDTO> out = null;
		try {
			out = UserMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public List<UserDTO> findAll() {
		final List<User> userList = (List<User>) userDao.findAll();
		try {
			return UserMapper.getInstance().domainToDTOList(userList);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] getUserAvatar(Integer id, HttpServletRequest request) throws IOException {

		if (id != null) {
			final String imagePath = userDao.getUserAvatarPath(id);
			if (imagePath != null) {
				// return FileDownloadUtil.getByteInputStream( uploadLocation + imagePath );
				return amazonS3ObjectUtil.downloadByteArray(imagePath);

			}
		}

		return FileDownloadUtil
				.getByteInputStream(request.getServletContext().getRealPath("").concat(USER_DEFAULT_IMAGE));
	}
}
