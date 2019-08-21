package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.admin.UserGroupDTO;
import com.neolith.focus.dto.admin.cmmssetting.UserSiteDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.admin.UserSite;
import com.neolith.focus.model.admin.UserSiteGroup;

import java.util.ArrayList;
import java.util.List;

public class UserSiteMapper extends GenericMapper<UserSite, UserSiteDTO> {

    private static UserSiteMapper instance = null;

    private UserSiteMapper(){
    }

    public static UserSiteMapper getInstance() {
        if (instance == null) {
            instance = new UserSiteMapper();
        }
        return instance;
    }


    @Override
    public UserSiteDTO domainToDto(UserSite domain) throws Exception {
        UserSiteDTO dto = new UserSiteDTO();
        if(domain!= null){
            dto.setSiteId(domain.getId());
            dto.setVersion(domain.getVersion());
            dto.setSiteSiteId(domain.getSite().getId());
            dto.setSiteSiteName(domain.getSite().getName());
            dto.setSiteUserGroupDTOList(setUserSiteGroupBySiteGroup(domain));
        }
        return dto;
    }


    protected List<UserGroupDTO> setUserSiteGroupBySiteGroup(UserSite domain){
        List<UserGroupDTO> userGroupDTOs = new ArrayList<>();
        for(UserSiteGroup userSiteGroup: domain.getUserSiteGroups()){
            UserGroupDTO userGroupDTO = new UserGroupDTO();
            userGroupDTO.setId(userSiteGroup.getUserGroup().getId());
            userGroupDTOs.add(userGroupDTO);
        }
        return userGroupDTOs;
    }



    @Override
    public void dtoToDomain(UserSiteDTO dto,UserSite domain) throws Exception {
        if(domain!= null && dto!= null){
            domain.setId(dto.getSiteId() != null ? dto.getSiteId() : null);
            domain.setVersion(dto.getVersion()!= null ? dto.getVersion():null );
        }
    }



    public List<UserSiteDTO> domainListToDTO(List<UserSite> userSites) throws Exception{
        List<UserSiteDTO> userSiteDTOList = new ArrayList<>();
        for(UserSite userSite: userSites){
            userSiteDTOList.add(domainToDto(userSite));

        }
        return userSiteDTOList;
    }

    @Override
    public UserSiteDTO domainToDtoForDataTable(UserSite domain) throws Exception {
        	UserSiteDTO dto = new UserSiteDTO();
            dto.setSiteSiteId(domain.getSite().getId());
            dto.setSiteSiteName(domain.getSite().getName());
            return dto;
    }



}
