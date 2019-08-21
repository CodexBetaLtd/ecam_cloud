package com.neolith.focus.service.admin.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.cmmssetting.UserSiteDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.UserSiteResult;

public interface UserSiteService{

	List<UserSiteDTO> getUserSiteList();

	List<Integer> getUserSiteAssetListIds();

	List<Integer> getUserGroupIdsForUserSite(Integer userSiteId);

	UserSiteDTO findById(Integer id);

	void deleteUserSiteById(Integer userId);

	DataTablesOutput<UserSiteDTO> findAll(FocusDataTablesInput dataTablesInput);

	UserSiteResult save(UserSiteDTO dto) throws Exception;

}
