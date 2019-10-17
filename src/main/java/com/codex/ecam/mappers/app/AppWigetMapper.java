package com.codex.ecam.mappers.app;

import com.codex.ecam.constants.Widgets;
import com.codex.ecam.dto.app.AppWigetDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.app.AppWiget;

public class AppWigetMapper extends GenericMapper<AppWiget, AppWigetDTO> {

    private static AppWigetMapper instance = null;

    private AppWigetMapper() {
    }

    public static AppWigetMapper getInstance() {
        if (instance == null) {
            instance = new AppWigetMapper();
        }
        return instance;
    }

    @Override
    public AppWigetDTO domainToDto(AppWiget domain) throws Exception {
    	AppWigetDTO dto = new AppWigetDTO();
        dto.setId(domain.getId());
        dto.setAppId(domain.getApp().getId());
        dto.setWigetId(domain.getWidgets().getId());
        dto.setWigetName(domain.getWidgets().getName());

        setCommanDTOFields(dto, domain);
        return dto;
    }

    @Override
    public AppWigetDTO domainToDtoForDataTable(AppWiget domain) throws Exception {
    	AppWigetDTO dto = new AppWigetDTO();
        dto.setId(domain.getId());
        dto.setAppId(domain.getApp().getId());
        dto.setWigetId(domain.getWidgets().getId());
        dto.setWigetName(domain.getWidgets().getName());

        return dto;
    }

    @Override
    public void dtoToDomain(AppWigetDTO dto, AppWiget domain) throws Exception {
        domain.setId(dto.getId());
        domain.setWidgets(Widgets.getWigetById(dto.getWigetId()));

        setCommanDomainFields(dto, domain);
    }

}
