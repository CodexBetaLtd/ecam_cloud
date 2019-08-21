package com.neolith.focus.service.app.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.constants.Menu;
import com.neolith.focus.dao.app.AppDao;
import com.neolith.focus.dao.app.BusinessAppDao;
import com.neolith.focus.dao.biz.BusinessDao;
import com.neolith.focus.dto.app.AppDTO;
import com.neolith.focus.dto.app.AppMenuDTO;
import com.neolith.focus.dto.app.MenuDTO;
import com.neolith.focus.dto.app.RelatedAppDTO;
import com.neolith.focus.mappers.app.AppMapper;
import com.neolith.focus.mappers.app.AppMenuMapper;
import com.neolith.focus.mappers.app.RelatedAppMapper;
import com.neolith.focus.model.app.App;
import com.neolith.focus.model.app.AppMenu;
import com.neolith.focus.model.app.RelatedApp;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.model.biz.business.BusinessApp;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.app.AppResult;
import com.neolith.focus.service.app.api.AppService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.search.app.AppSearchPropertyMapper;

@Service
public class AppServiceImpl implements AppService {

	final static Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

	@Autowired
	private AppDao appDao;

	@Autowired
	private BusinessAppDao businessAppDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<AppDTO> findAll(FocusDataTablesInput input) throws Exception {
		AppSearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<App> domainOut = appDao.findAll(input);
		DataTablesOutput<AppDTO> out = AppMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public AppDTO findById(Integer id) throws Exception {
		App domain = appDao.findOne(id);
		AppDTO dto = AppMapper.getInstance().domainToDto(domain);

		return dto;
	}

	@Override
	public AppResult delete(Integer id) {
		AppResult result = new AppResult(null, null);
		try {
			appDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("App Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("App Already Used. Cannot delete.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Override
	public AppResult save(AppDTO dto) throws Exception {
		AppResult result = createInvoiceResult(dto);
		try {
			executeSave(dto, result);
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("App Already updated. Please Reload App.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void executeSave(AppDTO dto, AppResult result) throws Exception {
		AppMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setAppData(result);
		appDao.save(result.getDomainEntity());
		result.addToMessageList(getMessageByAction(dto));
		result.updateDtoIdAndVersion();
	}

	private AppResult createInvoiceResult(AppDTO dto) {
		AppResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new AppResult(appDao.findOne(dto.getId()), dto);
		} else {
			result = new AppResult(new App(), dto);
		}
		return result;
	}

	private String getMessageByAction(AppDTO dto) {
		if (dto.getId() == null) {
			return "App Added Successfully.";
		} else {
			return "App Updated Successfully.";
		}
	}

	private void setAppData (AppResult result) throws Exception{
		setAppMenus(result);
		setRelatedApps(result);
	}

	private void setRelatedApps(AppResult result) throws Exception {
		Set<RelatedApp> relatedApps = new HashSet<>();

		if ( (result.getDtoEntity().getRelatedApps() != null) && (result.getDtoEntity().getRelatedApps().size() > 0) ) {

			RelatedApp relatedApp;

			for (RelatedAppDTO relatedAppDto : result.getDtoEntity().getRelatedApps()) {

				Set<RelatedApp> currentRelatedApps = result.getDomainEntity().getRelatedApps();

				if ((currentRelatedApps != null) && (currentRelatedApps.size() > 0)) {
					Optional<RelatedApp> optionalRelatedApps = currentRelatedApps.stream().filter((x) -> x.getRelatedApp().getId().equals(relatedAppDto.getRelatedAppId())).findAny();
					if (optionalRelatedApps.isPresent()) {
						relatedApp = optionalRelatedApps.get();
					} else {
						relatedApp = new RelatedApp();
					}
				} else {
					relatedApp = new RelatedApp();
				}
				createRelatedApp(relatedApp, relatedAppDto, result.getDomainEntity());
				relatedApps.add(relatedApp);
			}
		}

		result.getDomainEntity().setRelatedApps(relatedApps);
	}

	private void setAppMenus(AppResult result) throws Exception {
		Set<AppMenu> appMenus = new HashSet<>();

		if ((result.getDtoEntity().getAppMenus() != null) && (result.getDtoEntity().getAppMenus().size() > 0)) {

			AppMenu appMenu;

			for (AppMenuDTO appMenuDto : result.getDtoEntity().getAppMenus()) {

				Set<AppMenu> currentAppMenus = result.getDomainEntity().getAppMenus();

				if ((currentAppMenus != null) && (currentAppMenus.size() > 0)) {
					Optional<AppMenu> optionalMenus = currentAppMenus.stream().filter((x) -> x.getMenu().getId().equals(appMenuDto.getMenuId())).findAny();
					if (optionalMenus.isPresent()) {
						appMenu = optionalMenus.get();
					} else {
						appMenu = new AppMenu();
					}
				} else {
					appMenu = new AppMenu();
				}
				createAppMenu(appMenu, appMenuDto, result.getDomainEntity());
				appMenus.add(appMenu);
			}
		}

		result.getDomainEntity().setAppMenus(appMenus);
	}

	private void createAppMenu(AppMenu appMenu, AppMenuDTO appMenuDto, App domain) throws Exception {
		AppMenuMapper.getInstance().dtoToDomain(appMenuDto, appMenu);
		appMenu.setApp(domain);
	}

	private void createRelatedApp(RelatedApp relatedApp, RelatedAppDTO relatedAppDto, App domain) throws Exception {
		RelatedAppMapper.getInstance().dtoToDomain(relatedAppDto, relatedApp);
		relatedApp.setApp(domain);
		relatedApp.setRelatedApp(appDao.findOne(relatedAppDto.getRelatedAppId()));
	}

	@Override
	public List<MenuDTO> findAllMenus() {
		List<Menu> list = Menu.getMenus();
		return createMenuDTOList(list);
	}

	private List<MenuDTO> createMenuDTOList(List<Menu> list) {
		List<MenuDTO> dtoList = new ArrayList<>();
		MenuDTO dto;
		for (Menu menu : list) {
			dto = new MenuDTO();
			dto.setId(menu.getId());
			dto.setName(menu.getName());
			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	public Boolean isAppInstalled(Integer appId) {
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			return false;
		} else {
			return businessAppDao.isAppInstalled(appId, AuthenticationUtil.getLoginUserBusiness().getId());
		}
	}

	@Override
	public List<AppDTO> findRelatedApps(Integer appId) {
		List<AppDTO> apps = new ArrayList<>();
		App app = appDao.findOne(appId);
		try {
			addRelatedAppsToList(app, apps);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return apps;
	}

	@Override
	public List<AppDTO> findAffectedApps(Integer appId) {
		List<AppDTO> apps = new ArrayList<>();
		try {
			addAffectedAppsToList(appId, apps);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return apps;
	}

	private void addRelatedAppsToList(App app, List<AppDTO> apps) throws Exception {
		if ((app.getRelatedApps() != null) && (app.getRelatedApps().size() > 0)) {
			for (RelatedApp relatedApp : app.getRelatedApps()) {
				AppDTO dto = AppMapper.getInstance().domainToDtoForDataTable(relatedApp.getRelatedApp());
				apps.add(dto);
			}
		}
	}

	private void addAffectedAppsToList(Integer appId, List<AppDTO> apps) throws Exception {
		BusinessApp bApp = businessAppDao.findByAppIdAndBusinessId(appId, AuthenticationUtil.getLoginUserBusiness().getId());
		Business business = bApp.getBusiness();

		Set<RelatedApp> affectedApps = bApp.getAffectedApps();

		if ( (bApp != null) && ((affectedApps != null) && (affectedApps.size() > 0)) ) {
			for ( RelatedApp rApp : affectedApps ) {
				Optional<BusinessApp> optionalBusinessApp = business.getBusinessApps().stream().filter((x) -> x.getApp().getId().equals(rApp.getApp().getId())).findAny();
				if (optionalBusinessApp.isPresent()) {
					AppDTO dto = AppMapper.getInstance().domainToDtoForDataTable(optionalBusinessApp.get().getApp());
					apps.add(dto);
				}
			}
		}
	}

	@Override
	public Boolean installApp(Integer appId) {
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			return false;
		} else {
			Business business = businessDao.findById(AuthenticationUtil.getLoginUserBusiness().getId());
			addApp(appId, business);
			return true;
		}
	}

	private void addApp(Integer appId, Business business) {
		if ( isAppAlreadyNotInstalled(appId, business) ) {
			App app = appDao.findOne(appId);
			installRelatedApps(business, app);
			BusinessApp bApp = createBusinessApp(business, app);
			businessAppDao.save(bApp);
			logger.info("Install App : " + app.getName() + " Successfully");
		}
	}

	private void installRelatedApps(Business business, App app) {
		for ( RelatedApp rApp : app.getRelatedApps() ) {
			addApp(rApp.getRelatedApp().getId(), business);
		}
	}

	private BusinessApp createBusinessApp(Business business, App app) {
		BusinessApp bApp = new BusinessApp();
		bApp.setBusiness(business);
		bApp.setApp(app);
		bApp.setNoExpiry(true);
		bApp.setExpiryDate(null);
		bApp.setIsDeleted(false);

		return bApp;
	}

	private boolean isAppAlreadyNotInstalled(Integer appId, Business business) {
		if ((business.getBusinessApps() != null) && (business.getBusinessApps().size() > 0)) {
			Optional<BusinessApp> optionalBusinessApp = business.getBusinessApps().stream().filter((x) -> x.getApp().getId() == appId).findAny();
			if (optionalBusinessApp.isPresent()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Boolean uninstallApp(Integer appId) {
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			return false;
		} else {
			Business business = businessDao.findById(AuthenticationUtil.getLoginUserBusiness().getId());
			removeApp(appId, business);
			return true;
		}
	}

	private void removeApp(Integer appId, Business business) {
		Optional<BusinessApp> optionalBusinessApp = business.getBusinessApps().stream().filter((x) -> x.getApp().getId() == appId).findAny();
		if (optionalBusinessApp.isPresent()) {
			removeRelatedApps(business, optionalBusinessApp.get());
			businessAppDao.delete(optionalBusinessApp.get());
			logger.info("Uninstall App : " + optionalBusinessApp.get().getApp().getName() + " Successfully");
		}
	}

	private void removeRelatedApps(Business business, BusinessApp businessApp) {
		if ((businessApp.getAffectedApps() != null) && (businessApp.getAffectedApps().size() > 0)) {
			for ( RelatedApp app : businessApp.getAffectedApps()) {
				removeApp(app.getApp().getId(), business);
			}
		}
	}

	@Override
	public List<AppDTO> findAllApps() throws Exception {
		List<App> apps = appDao.findAllApps();
		return AppMapper.getInstance().domainToDTOList(apps);
	}

}
