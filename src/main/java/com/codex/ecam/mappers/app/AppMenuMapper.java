package com.codex.ecam.mappers.app;

import com.codex.ecam.constants.Menu;
import com.codex.ecam.dto.app.AppMenuDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.app.AppMenu;

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
