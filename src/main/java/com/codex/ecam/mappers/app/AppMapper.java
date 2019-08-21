package com.codex.ecam.mappers.app;

import java.util.Set;

import com.codex.ecam.dto.app.AppDTO;
import com.codex.ecam.dto.app.AppMenuDTO;
import com.codex.ecam.dto.app.RelatedAppDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.app.App;
import com.codex.ecam.model.app.AppMenu;
import com.codex.ecam.model.app.RelatedApp;

public class AppMapper extends GenericMapper<App, AppDTO> {

	private static AppMapper instance = null;

	private AppMapper() {
	}

	public static AppMapper getInstance() {
		if (instance == null) {
			instance = new AppMapper();
		}
		return instance;
	}

	@Override
	public AppDTO domainToDto(App domain) throws Exception {
		AppDTO dto = new AppDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setRate(domain.getRate());

		setAppMenus(dto, domain);
		setRelatedApps(dto, domain);

		setCommanDTOFields(dto, domain);
		return dto;
	}

	private void setRelatedApps(AppDTO dto, App domain) throws Exception {
		Set<RelatedApp> relatedApps = domain.getRelatedApps();
		if ((relatedApps != null) && (relatedApps.size() > 0)) {

			for (RelatedApp relatedApp : domain.getRelatedApps()) {

				RelatedAppDTO relatedAppDto = RelatedAppMapper.getInstance().domainToDto(relatedApp);
				dto.getRelatedApps().add(relatedAppDto);

				if ((relatedApp.getRelatedApp().getRelatedApps() != null) && (relatedApp.getRelatedApp().getRelatedApps().size() > 0)) {
					setRelatedApps(dto, relatedApp.getRelatedApp());
				}
			}
		}
	}

	private void setAppMenus(AppDTO dto, App domain) throws Exception {
		Set<AppMenu> appMenus = domain.getAppMenus();
		if ((appMenus != null) && (appMenus.size() > 0)) {
			for (AppMenu appMenu : domain.getAppMenus()) {
				AppMenuDTO appMenuDto = AppMenuMapper.getInstance().domainToDto(appMenu);
				dto.getAppMenus().add(appMenuDto);
			}
		}
	}

	@Override
	public AppDTO domainToDtoForDataTable(App domain) throws Exception {
		AppDTO dto = new AppDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setRate(domain.getRate());
		return dto;
	}

	@Override
	public void dtoToDomain(AppDTO dto, App domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setRate(dto.getRate());

		setCommanDomainFields(dto, domain);
	}

}
