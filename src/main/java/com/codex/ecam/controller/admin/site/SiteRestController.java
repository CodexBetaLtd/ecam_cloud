package com.codex.ecam.controller.admin.site;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.UserGroupService;
import com.codex.ecam.service.admin.api.UserSiteService;
import com.codex.ecam.service.asset.api.AssetService;


@RestController
@RequestMapping(SiteRestController.REQUEST_MAPPING_URL)
public class SiteRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/sites";

	@Autowired
	public UserGroupService userGroupService;

	@Autowired
	public AssetService assetService;

	@Autowired
	public UserSiteService userSiteService;


	@RequestMapping(value = {"/deleteUserSiteById"}, method = RequestMethod.GET)
	public String deleteUserSite(Integer siteId, Model model) {
		try {
			userSiteService.deleteUserSiteById(siteId);
			return "success";
		} catch (final Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
	}

	@RequestMapping(value = "/tabledata", method = {RequestMethod.GET})
	public DataTablesOutput<AssetDTO> getAllByLevel(@Valid FocusDataTablesInput input) {
		try {
			return assetService.findAllFacilitiesByLevel(input);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<AssetDTO>();
	}

}
