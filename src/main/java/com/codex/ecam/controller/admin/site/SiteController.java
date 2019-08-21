package com.codex.ecam.controller.admin.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.dto.admin.UserGroupDTO;
import com.codex.ecam.dto.admin.cmmssetting.UserSiteDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.service.admin.api.UserGroupService;
import com.codex.ecam.service.admin.api.UserSiteService;
import com.codex.ecam.service.asset.api.AssetService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(SiteController.REQUEST_MAPPING_URL)
public class SiteController {

    public static final String REQUEST_MAPPING_URL = "/site";

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserSiteService userSiteService;

    @RequestMapping(value = {"/getSiteUsers"}, method = RequestMethod.GET)
    public String getSiteUsers(Model model) {
        return "admin/cmmssetting/site/modal/site-users-modal";
    }

    @RequestMapping(value = {"/getSitesTable"}, method = RequestMethod.GET)
    public String getSiteTable(Model model) {
        try {
            List<AssetDTO> assetDTOs = assetService.findByExcludingAssetList(AssetCategoryType.LOCATIONS_OR_FACILITIES, userSiteService.getUserSiteAssetListIds());
            model.addAttribute("siteList", assetDTOs);
            return "general/table/siteTable :: siteTableFragment";
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @RequestMapping(value = {"/getSiteByBusiness"}, method = RequestMethod.GET)
    public String getSiteList(Integer businessId, Model model) {
        try {
            List<AssetDTO> userSiteDTOList = assetService.findAllSiteByBusiness(businessId);
            model.addAttribute("userSiteDTOList", userSiteDTOList);
            model.addAttribute("userDTO", new UserDTO());
            model.addAttribute("userGroupList", userGroupService.findAll());
            return "general/dropdown/userSiteSelection :: userSiteFragment";
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @RequestMapping(value = {"/getSiteByBusinessId/{businessId}"}, method = RequestMethod.GET)
    public String getUserSiteList(Integer businessId, Model model) {
        try {
            List<AssetDTO> userSiteDTOList = assetService.findAllSiteByBusiness(businessId);
            model.addAttribute("userSiteDTOList", userSiteDTOList);
            model.addAttribute("userDTO", new UserDTO());
            model.addAttribute("userGroupList", userGroupService.findAll());
            return "general/dropdown/userSiteSelection :: userSiteFragment";
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @RequestMapping(value = {"/getSiteWithUserGroups"}, method = RequestMethod.GET)
    public String getUserSite(Model model) {
        try {
            List<AssetDTO> assetDTOs = assetService.findByAssetCategoryType(AssetCategoryType.LOCATIONS_OR_FACILITIES);
            model.addAttribute("userSiteDTO", new UserSiteDTO());
            model.addAttribute("assignedUserGroups", new ArrayList<Integer>());
            model.addAttribute("facilityList", assetDTOs.size() > 0 ? assetDTOs : new ArrayList<AssetDTO>());
            model.addAttribute("userGroupDTOList", assetDTOs.size() > 0 ? userGroupService.findAll() : new ArrayList<UserGroupDTO>());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "admin/cmmsSetting/site/userSiteModalBody";
    }

    @RequestMapping(value = {"/getUserGroupsBySite"}, method = RequestMethod.GET)
    public String getUserSiteGroup(Integer siteId, Model model) {
        try {
            AssetDTO assetDTO;
            UserSiteDTO userSiteDTO = userSiteService.findById(siteId);
            assetDTO = assetService.findById(userSiteDTO.getSiteSiteId());
//            userSiteDTO.setAssignedUserGroups(userSiteService.getUserGroupIdsForUserSite(siteId));
            userSiteDTO.setSiteUserGroupDTOList(userGroupService.findAll());
            model.addAttribute("assetDTO", assetDTO);
            model.addAttribute("userSite", userSiteDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
        return "admin/cmmsSetting/site/userSiteGroupBody";
    }

}
