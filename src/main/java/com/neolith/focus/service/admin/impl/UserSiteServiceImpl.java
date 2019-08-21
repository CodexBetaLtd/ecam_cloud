package com.neolith.focus.service.admin.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.dao.admin.UserGroupDao;
import com.neolith.focus.dao.admin.UserSiteDao;
import com.neolith.focus.dao.admin.UserSiteGroupDao;
import com.neolith.focus.dao.asset.AssetDao;
import com.neolith.focus.dto.admin.cmmssetting.UserSiteDTO;
import com.neolith.focus.mappers.admin.UserSiteMapper;
import com.neolith.focus.model.admin.UserSite;
import com.neolith.focus.model.admin.UserSiteGroup;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.UserSiteResult;
import com.neolith.focus.security.UserUtil;
import com.neolith.focus.service.admin.api.UserSiteService;
import com.neolith.focus.util.AuthenticationUtil;

@Service
public class UserSiteServiceImpl implements UserSiteService {

	@Autowired
	private UserSiteDao userSiteDao;

	@Autowired
	private UserGroupDao userGroupDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private UserSiteGroupDao userSiteGroupDao;

	@Autowired
	private UserSiteService userSiteService;

	@Override
	public List<UserSiteDTO> getUserSiteList() {
		List<UserSite> userSites = (List<UserSite>) userSiteDao.findAll();
		try {
			return UserSiteMapper.getInstance().domainToDTOList(userSites);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Integer> getUserSiteAssetListIds() {
		return userSiteDao.findUserSiteAssets();
	}

	@Override
	public List<Integer> getUserGroupIdsForUserSite(Integer userSiteId) {
		try {
			return userSiteGroupDao.findUserSiteGroupIds(userSiteId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public UserSiteResult save(UserSiteDTO dto) throws Exception {
		UserSiteResult result = createUserSiteResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("User Site Already updated. Please Reload User Site.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
			ex.printStackTrace();
		}

		return result;
	}

	private String getMessageByAction(UserSiteDTO dto) {
		if (dto.getSiteId() == null) {
			return "User Site Added Successfully.";
		} else {
			return "User Site Updated Successfully.";
		}
	}

	private UserSiteResult createUserSiteResult(UserSiteDTO dto) {
		UserSiteResult result;
		if ((dto.getSiteId() != null) && (dto.getSiteId() > 0)) {
			result = new UserSiteResult(userSiteDao.findOne(dto.getSiteId()), dto);
		} else {
			result = new UserSiteResult(new UserSite(), dto);
		}

		return result;
	}


	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(UserSiteResult result) throws Exception {
		setUserSiteGroups(result.getDomainEntity(), result.getDtoEntity());
		setSite(result);
		setUser(result);
	}

	private void setUser(UserSiteResult result) {
		result.getDomainEntity().setUser(UserUtil.getAuthenticatedLoggedUser());
	}

	private void setSite(UserSiteResult result) {
		if ((result.getDtoEntity().getSiteSiteId() != null) && (result.getDtoEntity().getSiteSiteId() > 0)) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteSiteId()));
		}
	}

	protected void setUserSiteGroups(UserSite userSite, UserSiteDTO dto) {
		Set<UserSiteGroup> userSiteGroups = new HashSet<UserSiteGroup>();
		for (Integer userGroupId : dto.getSiteAssignedUserGroups()) {
			UserSiteGroup userSiteGroup = new UserSiteGroup();
			userSiteGroup.setUserGroup(userGroupDao.findById(userGroupId));
			userSiteGroup.setIsDeleted(Boolean.FALSE);
			userSiteGroup.setUserSite(userSite);
			userSiteGroups.add(userSiteGroup);
		}
		userSite.setUserSiteGroups(userSiteGroups);
	}

	protected void saveOrUpdate(UserSiteDTO dto, UserSite domain) throws Exception {
		UserSiteMapper.getInstance().dtoToDomain(dto, domain);
		setUserSiteGroups(domain, dto);
		domain.setSite(assetDao.findOne(dto.getSiteSiteId()));
		domain.setUser(UserUtil.getAuthenticatedLoggedUser());
		userSiteDao.save(domain);
	}

	@Override
	public UserSiteDTO findById(Integer id) {
		try {
			UserSiteDTO userSite= UserSiteMapper.getInstance().domainToDto(userSiteDao.findOne(id));
			userSite.setSiteAssignedUserGroups(userSiteService.getUserGroupIdsForUserSite(id));
			return userSite;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteUserSiteById(Integer userId) {
		try {
			userSiteDao.delete(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public DataTablesOutput<UserSiteDTO> findAll(FocusDataTablesInput dataTablesInput) {

		DataTablesOutput<UserSite> domainOut = null;
		Specification<UserSite> specification = null;
		try {
			if (AuthenticationUtil.isAuthUserAdminLevel()) {

				specification = (root, query, cb) -> {
					query.groupBy(root.get("site").get("id"));
					return cb.conjunction();
				};

			} else {
				specification = (root, query, cb) -> {
					query.groupBy(root.get("site").get("id"));
					return cb.equal(root.get("user").get("id"), AuthenticationUtil.getCurrentUser().getUserId());
				};
			}

			domainOut = userSiteDao.findAll(dataTablesInput, specification);
			DataTablesOutput<UserSiteDTO> out = UserSiteMapper.getInstance().domainToDTODataTablesOutput(domainOut);
			return out;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
