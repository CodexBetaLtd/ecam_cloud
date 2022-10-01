package com.codex.ecam.dto.admin.cmmssetting;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;

public class UserSiteDTO extends BaseDTO {

    private Integer siteId;
    private Integer siteUserId;
    private Integer siteSiteId;
    private String siteSiteName;
    private List<Integer> siteAssignedUserGroups = new ArrayList<>();
    private List<UserGroupDTO> siteUserGroupDTOList = new ArrayList<>();

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer id) {
        this.siteId = id;
    }

    public Integer getSiteUserId() {
        return siteUserId;
    }

    public void setSiteUserId(Integer userId) {
        this.siteUserId = userId;
    }

    public Integer getSiteSiteId() {
        return siteSiteId;
    }

    public void setSiteSiteId(Integer siteId) {
        this.siteSiteId = siteId;
    }

    public List<Integer> getSiteAssignedUserGroups() {
        return siteAssignedUserGroups;
    }

    public String getSiteSiteName() {
        return siteSiteName;
    }

    public void setSiteSiteName(String siteName) {
        this.siteSiteName = siteName;
    }

    public List<UserGroupDTO> getSiteUserGroupDTOList() {
        return siteUserGroupDTOList;
    }

    public void setSiteUserGroupDTOList(List<UserGroupDTO> userGroupDTOList) {
        this.siteUserGroupDTOList = userGroupDTOList;
    }

    public void setSiteAssignedUserGroups(List<Integer> list) {
        this.siteAssignedUserGroups = list;
    }

}
