package com.neolith.focus.mappers.app;

import com.neolith.focus.constants.Menu;
import com.neolith.focus.dto.app.AppMenuDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.app.AppMenu;

public class AppMenuMapper extends GenericMapper<AppMenu, AppMenuDTO> {

    private static AppMenuMapper instance = null;

    private AppMenuMapper() {
    }

    public static AppMenuMapper getInstance() {
        if (instance == null) {
            instance = new AppMenuMapper();
        }
        return instance;
    }

    @Override
    public AppMenuDTO domainToDto(AppMenu domain) throws Exception {
        AppMenuDTO dto = new AppMenuDTO();
        dto.setId(domain.getId());
        dto.setAppId(domain.getApp().getId());
        dto.setMenuId(domain.getMenu().getId());
        dto.setMenuName(domain.getMenu().getName());

        setCommanDTOFields(dto, domain);
        return dto;
    }

    @Override
    public AppMenuDTO domainToDtoForDataTable(AppMenu domain) throws Exception {
        AppMenuDTO dto = new AppMenuDTO();
        dto.setId(domain.getId());
        dto.setAppId(domain.getApp().getId());
        dto.setMenuId(domain.getMenu().getId());
        dto.setMenuName(domain.getMenu().getName());

        return dto;
    }

    @Override
    public void dtoToDomain(AppMenuDTO dto, AppMenu domain) throws Exception {
        domain.setId(dto.getId());
        domain.setMenu(Menu.getMenuById(dto.getMenuId()));

        setCommanDomainFields(dto, domain);
    }

}
