package com.codex.ecam.service.admin.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.Menu;
import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.PagePermission;
import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.constants.Widgets;
import com.codex.ecam.dao.admin.UserGroupDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.PermisonTreeDTO;
import com.codex.ecam.dto.admin.UserGroupDTO;
import com.codex.ecam.mappers.admin.UserGroupMapper;
import com.codex.ecam.model.admin.UserGroup;
import com.codex.ecam.model.admin.UserGroupMenu;
import com.codex.ecam.model.admin.UserGroupPage;
import com.codex.ecam.model.admin.UserGroupPagePermission;
import com.codex.ecam.model.admin.UserGroupWiget;
import com.codex.ecam.model.app.AppMenu;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.biz.business.BusinessApp;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.UserGroupResult;
import com.codex.ecam.service.admin.api.UserGroupService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.admin.UserGroupSearchPropertyMapper;
import com.codex.ecam.util.type.GenericCheckBox;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupDao userGroupDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<UserGroupDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<UserGroup> domainOut;

		UserGroupSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = userGroupDao.findAll(input);
		} else {
			Specification<UserGroup> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = userGroupDao.findAll(input, specification);
		}
		DataTablesOutput<UserGroupDTO> out = UserGroupMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public List<UserGroupDTO> findAll() throws Exception {
		List<UserGroup> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = (List<UserGroup>) userGroupDao.findAll();
		} else {
			Specification<UserGroup> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = (List<UserGroup>) userGroupDao.findAll(specification);
		}
		return UserGroupMapper.getInstance().domainToDTOList(domainOut);
	}

	@Override
	public UserGroupDTO findById(Integer id) throws Exception {
		UserGroup domain = userGroupDao.findById(id);
		if (domain != null) {
			return UserGroupMapper.getInstance().domainToDtoWithPermission(domain);
		}
		return null;
	}

	@Override
	public UserGroupResult delete(Integer id) {
		UserGroupResult result = new UserGroupResult(null, null);
		try {
			userGroupDao.delete(id);

			result.setResultStatusSuccess();
			result.addToMessageList("User Group Deleted Successfully.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Error Occured While Deleting.");
		}
		return result;
	}

	@Override
	public UserGroupResult save(UserGroupDTO dto) throws Exception {
		UserGroupResult result = createUserGroupResult(dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("User Group Already updated. Please Reload User Group.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(UserGroupResult result) throws Exception {
		removeMenuAndPagePermissions(result);
		UserGroupMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		setWigetList(result);
		userGroupDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private void setWigetList(UserGroupResult result) {
		Set<UserGroupWiget> groupWigets = new HashSet<>();

		if ((result.getDtoEntity().getWigets() != null) && (result.getDtoEntity().getWigets().size() > 0)) {

			Set<UserGroupWiget> currentUserGroupWiget = result.getDomainEntity().getWigetList();

			for (Widgets widgets : result.getDtoEntity().getWigets()) {
				UserGroupWiget groupWiget;

				if (widgets.getId() != null) {
					groupWiget = currentUserGroupWiget.stream().filter((x) -> x.getId().equals(widgets.getId()))
							.findAny().orElseGet(UserGroupWiget::new);
				} else {
					groupWiget = new UserGroupWiget();
				}

				// AssetFileMapper.getInstance().dtoToDomain(assetFileDTO,
				// assetFile);
				groupWiget.setUserGroup(result.getDomainEntity());
				groupWiget.setWidgets(widgets);

				groupWigets.add(groupWiget);
			}
		}
		result.getDomainEntity().setWigetList(groupWigets);
	}

	private void removeMenuAndPagePermissions(UserGroupResult result) {
		if ((result.getDomainEntity().getId() != null) && (result.getDomainEntity().getId() > 0)) {
			result.getDomainEntity().setMenuList(new HashSet<UserGroupMenu>());
			if (result.getDtoEntity().getPage() != null) {
				if ((result.getDomainEntity().getPageList() != null)
						&& (result.getDomainEntity().getPageList().size() > 0)) {
					Optional<UserGroupPage> optionalUserGroupPage = result.getDomainEntity().getPageList().stream()
							.filter((x) -> x.getPage().equals(result.getDtoEntity().getPage())).findAny();
					if (optionalUserGroupPage.isPresent()) {
						result.getDomainEntity().getPageList().remove(optionalUserGroupPage.get());
					}
				}
			}
		}
	}

	private UserGroupResult createUserGroupResult(UserGroupDTO dto) {
		UserGroupResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new UserGroupResult(userGroupDao.findOne(dto.getId()), dto);
		} else {
			result = new UserGroupResult(new UserGroup(), dto);
		}

		return result;
	}

	private String getMessageByAction(UserGroupDTO dto) {
		if (dto.getId() == null) {
			return "User Group Added Successfully.";
		} else {
			return "User Group Updated Successfully.";
		}
	}

	private void setBusiness(UserGroupResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public List<GenericCheckBox<Menu, SubMenu>> getMenuPermissions() {
		List<GenericCheckBox<Menu, SubMenu>> menuPermissionlist = new ArrayList<GenericCheckBox<Menu, SubMenu>>();
		Menu[] menuList = findMenuByBusiness();
		for (Menu menu : menuList) {
			GenericCheckBox<Menu, SubMenu> cBox = new GenericCheckBox<Menu, SubMenu>(menu, null, false);
			List<SubMenu> subMenuList = SubMenu.getSubMenuByMenu(menu);
			List<GenericCheckBox<Menu, SubMenu>> sMenulist = new ArrayList<GenericCheckBox<Menu, SubMenu>>();
			for (SubMenu sMenu : subMenuList) {
				sMenulist.add(new GenericCheckBox<Menu, SubMenu>(null, sMenu, false));
			}
			cBox.setChildList(sMenulist);
			menuPermissionlist.add(cBox);
		}
		return menuPermissionlist;
	}

	public List<PermisonTreeDTO> getMenuAll() {
		List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		Menu[] menuList = findMenuByBusiness();
		for (Menu menu : menuList) {
			PermisonTreeDTO menudto = new PermisonTreeDTO();
			menudto.setId(menu.getId());
			menudto.setText(menu.getName());
			menudto.setAnyChildren(Boolean.TRUE);
			menudto.setType("MENU");
			menuPermissionlist.add(menudto);
		}
		return menuPermissionlist;
	}

	public List<PermisonTreeDTO> getSubMenuAll(Integer id) {
		List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		Menu[] menuList = findMenuByBusiness();
		for (SubMenu menu : SubMenu.getSubMenuByMenu(Menu.getMenuById(id))) {
			PermisonTreeDTO menudto = new PermisonTreeDTO();
			menudto.setId(menu.getId());
			menudto.setText(menu.getName());
			menudto.setAnyChildren(Boolean.TRUE);
			menudto.setType("SUBMENU");
			menudto.setContinent(Menu.getMenuById(id).getName());
			menuPermissionlist.add(menudto);
		}
		return menuPermissionlist;
	}

	@Override
	public List<PermisonTreeDTO> getPageAll(Integer id) {
		List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		for (Page page : Page.findPageBySubMenu(SubMenu.getSubMenuById(id))) {
			PermisonTreeDTO menudto = new PermisonTreeDTO();
			menudto.setId(page.getId());
			menudto.setText(page.getName());
			menudto.setAnyChildren(Boolean.TRUE);
			menudto.setType("PAGE");
			menudto.setContinent(SubMenu.getSubMenuById(id).getName());
			menuPermissionlist.add(menudto);
		}
		return menuPermissionlist;
	}

	@Override
	public List<PermisonTreeDTO> getPagePermistionAll(Integer id) {
		List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		for (PagePermission pagePermission : PagePermission.getPagePermissionsByPage(Page.getPageById(id))) {
			PermisonTreeDTO menudto = new PermisonTreeDTO();
			menudto.setId(pagePermission.getId());
			menudto.setText(pagePermission.getName());
			menudto.setAnyChildren(Boolean.FALSE);
			menudto.setType("PAGEPERMISSION");
			menudto.setContinent(Page.getPageById(id).getName());
			menuPermissionlist.add(menudto);
		}
		return menuPermissionlist;
	}

	public List<PermisonTreeDTO> getMenuPermissionsAll(Integer id) {
		List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		Menu[] menuList = findMenuByBusiness();

		if (id != null) {
			UserGroup domain = userGroupDao.findById(id);
			int i = 0;
			for (Menu menu : menuList) {
				PermisonTreeDTO menudto = new PermisonTreeDTO();
				menudto.setId(menu.getId());
				menudto.setText(menu.getName());
				menudto.setAnyChildren(Boolean.TRUE);
				menudto.setType("MENU");
				int k = 0;
				for (SubMenu subMenu : SubMenu.getSubMenuByMenu(menu)) {
					PermisonTreeDTO submenudto = new PermisonTreeDTO();
					submenudto.setId(subMenu.getId());
					submenudto.setText(subMenu.getName());
					submenudto.setAnyChildren(Boolean.TRUE);
					submenudto.setType("SUBMENU");
					int j = 0;
					for (Page page : Page.findPageBySubMenu(subMenu)) {
						PermisonTreeDTO pageDto = new PermisonTreeDTO();
						pageDto.setId(page.getId());
						pageDto.setText(page.getName());
						pageDto.setAnyChildren(Boolean.TRUE);
						pageDto.setType("PAGE");
						int n = 0;
						for (PagePermission pagePermission : PagePermission.getPagePermissionsByPage(page)) {
							PermisonTreeDTO pagePermmisionDto = new PermisonTreeDTO();
							pagePermmisionDto.setId(pagePermission.getId());
							pagePermmisionDto.setText(pagePermission.getName());
							for (UserGroupPage groupPage : domain.getPageList()) {
								for (UserGroupPagePermission userGroupPagePermission : groupPage.getPermissionList()) {
									if (userGroupPagePermission.getPagePermission().equals(pagePermission)) {
										pagePermmisionDto.setCheckedFieldName(Boolean.TRUE);

									} else {
										pagePermmisionDto.setCheckedFieldName(Boolean.FALSE);

									}
								}
							//pagePermmisionDto.setCheckedFieldName(Boolean.TRUE);

							pagePermmisionDto.setAnyChildren(Boolean.FALSE);
							pagePermmisionDto.setType("PAGEPERMISSION");
							pageDto.getChildren().add(pagePermmisionDto);
							n++;
						}
						submenudto.getChildren().add(pageDto);
						j++;
					}
					menudto.getChildren().add(submenudto);
					k++;
				}
				menuPermissionlist.add(menudto);
				i++;
			}
			}

		} else {
			int i = 0;
			for (Menu menu : menuList) {
				PermisonTreeDTO menudto = new PermisonTreeDTO();
				menudto.setId(menu.getId());
				menudto.setText(menu.getName());
				menudto.setAnyChildren(Boolean.TRUE);
				menudto.setType("MENU");
				int k = 0;
				for (SubMenu subMenu : SubMenu.getSubMenuByMenu(menu)) {
					PermisonTreeDTO submenudto = new PermisonTreeDTO();
					submenudto.setId(subMenu.getId());
					submenudto.setText(subMenu.getName());
					submenudto.setAnyChildren(Boolean.TRUE);
					submenudto.setType("SUBMENU");
					int j = 0;
					for (Page page : Page.findPageBySubMenu(subMenu)) {
						PermisonTreeDTO pageDto = new PermisonTreeDTO();
						pageDto.setId(page.getId());
						pageDto.setText(page.getName());
						pageDto.setAnyChildren(Boolean.TRUE);
						pageDto.setType("PAGE");
						int n = 0;
						for (PagePermission pagePermission : PagePermission.getPagePermissionsByPage(page)) {
							PermisonTreeDTO pagePermmisionDto = new PermisonTreeDTO();
							pagePermmisionDto.setId(pagePermission.getId());
							pagePermmisionDto.setText(pagePermission.getName());
							// if(pagePermission.equals(other)){
							pagePermmisionDto.setCheckedFieldName(Boolean.FALSE);
							// }

							pagePermmisionDto.setAnyChildren(Boolean.FALSE);
							pagePermmisionDto.setType("PAGEPERMISSION");
							pagePermmisionDto.getChildren().add(pagePermmisionDto);
							n++;
						}
						submenudto.getChildren().add(pageDto);
						j++;
					}
					menudto.getChildren().add(submenudto);
					k++;
				}
				menuPermissionlist.add(menudto);
				i++;
			}
		}
		return menuPermissionlist;
	}

	private Menu[] findMenuByBusiness() {
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			return Menu.values();
		} else {
			List<Menu> menus = new ArrayList<>();
			Business business = businessDao.findById(AuthenticationUtil.getLoginUserBusiness().getId());
			for (BusinessApp app : business.getBusinessApps()) {
				for (AppMenu appMenu : app.getApp().getAppMenus()) {
					menus.add(appMenu.getMenu());
				}
			}
			return menus.toArray(new Menu[menus.size()]);
		}
	}

	@Override
	public List<Page> findPageListByBusiness() {
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			return Page.getPageList();
		} else {
			List<Page> pages = new ArrayList<>();

			Business business = businessDao.findById(AuthenticationUtil.getLoginUserBusiness().getId());
			for (BusinessApp app : business.getBusinessApps()) {
				for (AppMenu appMenu : app.getApp().getAppMenus()) {
					List<SubMenu> submenus = SubMenu.getSubMenuByMenu(appMenu.getMenu());
					for (SubMenu subMenu : submenus) {
						pages.addAll(Page.findPageBySubMenu(subMenu));
					}
				}
			}

			return pages;
		}
	}

}
