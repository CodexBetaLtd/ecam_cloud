package com.codex.ecam.service.admin.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;
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
			final Specification<UserGroup> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = userGroupDao.findAll(input, specification);
		}
		final DataTablesOutput<UserGroupDTO> out = UserGroupMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public List<UserGroupDTO> findAll() throws Exception {
		List<UserGroup> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = (List<UserGroup>) userGroupDao.findAll();
		} else {
			final Specification<UserGroup> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = userGroupDao.findAll(specification);
		}
		return UserGroupMapper.getInstance().domainToDTOList(domainOut);
	}

	@Override
	public UserGroupDTO findById(Integer id) throws Exception {
		final UserGroup domain = userGroupDao.findById(id);
		if (domain != null) {
			return UserGroupMapper.getInstance().domainToDtoWithPermission(domain);
		}
		return null;
	}

	@Override
	public UserGroupResult delete(Integer id) {
		final UserGroupResult result = new UserGroupResult(null, null);
		try {
			userGroupDao.delete(id);

			result.setResultStatusSuccess();
			result.addToMessageList("User Group Deleted Successfully.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Error Occured While Deleting.");
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserGroupResult deleteMultiple(Integer[] ids) throws Exception {
		final UserGroupResult result = new UserGroupResult(null, null);
		try {
			for (final Integer id : ids) {
				userGroupDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("User Group(s) Deleted Successfully.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Error Occured While Deleting.");
		}
		return result;
	}

	@Override
	public UserGroupResult save(UserGroupDTO dto) throws Exception {
		final UserGroupResult result = createUserGroupResult(dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("User Group Already updated. Please Reload User Group.");
		} catch (final Exception ex) {
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
		setPagePermissions(result.getDtoEntity(), result.getDomainEntity());
		userGroupDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private void setPagePermissions(UserGroupDTO dto, UserGroup domain) {
		final List<PagePermission> pagePermissionDTOs = dto.getPagePermissions();

		final Set<UserGroupPage> userGroupPages = new HashSet<>();

		if (pagePermissionDTOs != null && pagePermissionDTOs.size() > 0) {

			final Set<UserGroupPage> currentUserGroupPages = domain.getPageList();

			for (final PagePermission pagePermission : pagePermissionDTOs) {
				final UserGroupPage userGroupPage = createOrUpdateUserGroupPage(domain, userGroupPages, currentUserGroupPages,pagePermission);
				final UserGroupPagePermission userGroupPagePermission = createOrUpdateUserGroupPagePermission(pagePermission,userGroupPage);
				userGroupPage.getPermissionList().add(userGroupPagePermission);
			}
		}

		removeUngrantedPagePermissions(pagePermissionDTOs, userGroupPages);

		domain.setPageList(userGroupPages);

	}

	private UserGroupPage createOrUpdateUserGroupPage(UserGroup domain, final Set<UserGroupPage> userGroupPages,
			final Set<UserGroupPage> currentUserGroupPages, final PagePermission pagePermission) {
		UserGroupPage userGroupPage = new UserGroupPage();

		final Optional<UserGroupPage> optionalUserGroupPage = userGroupPages.stream().filter(x->x.getPage().getId().equals(pagePermission.getPage().getId())).findAny();
		if (optionalUserGroupPage.isPresent()) {
			userGroupPage = optionalUserGroupPage.get();
		} else {
			userGroupPage = currentUserGroupPages.stream().filter(x->x.getPage().getId().equals(pagePermission.getPage().getId())).findAny().orElseGet(UserGroupPage::new);
			userGroupPages.add(userGroupPage);
		}
		userGroupPage.setPage(pagePermission.getPage());
		userGroupPage.setUserGroup(domain);
		userGroupPage.setIsDeleted(false);
		return userGroupPage;
	}

	private UserGroupPagePermission createOrUpdateUserGroupPagePermission(final PagePermission pagePermission,
			UserGroupPage userGroupPage) {
		UserGroupPagePermission userGroupPagePermission = new UserGroupPagePermission();
		final Set<UserGroupPagePermission> currentUserGroupPermissions = userGroupPage.getPermissionList();

		if (currentUserGroupPermissions != null && currentUserGroupPermissions.size() > 0) {
			final Optional<UserGroupPagePermission> optionalUserGroupPagePermission = currentUserGroupPermissions.stream()
					.filter(x->x.getPagePermission().getId().equals(pagePermission.getId())).findAny();

			if (optionalUserGroupPagePermission.isPresent()) {
				userGroupPagePermission = optionalUserGroupPagePermission.get();
				currentUserGroupPermissions.remove(optionalUserGroupPagePermission.get());
			} else {
				userGroupPagePermission = new UserGroupPagePermission();
			}

		} else {
			userGroupPage.setPermissionList(new HashSet<>());
		}

		userGroupPagePermission.setPagePermission(pagePermission);
		userGroupPagePermission.setUserGroupPage(userGroupPage);
		userGroupPagePermission.setIsDeleted(false);
		return userGroupPagePermission;
	}

	private void removeUngrantedPagePermissions(List<PagePermission> pagePermissionDTOs, Set<UserGroupPage> userGroupPages) {
		for (final UserGroupPage userGroupPage : userGroupPages) {
			final Set<UserGroupPagePermission> permissions = new HashSet<>();
			for (final UserGroupPagePermission userGroupPagePermission : userGroupPage.getPermissionList()) {
				final Optional<PagePermission> optional = pagePermissionDTOs.stream().filter(x->x.getId().equals(userGroupPagePermission.getPagePermission().getId())).findAny();
				if (optional.isPresent()) {
					permissions.add(userGroupPagePermission);
				}
			}
			userGroupPage.setPermissionList(permissions);
		}
	}

	private void setWigetList(UserGroupResult result) {
		final Set<UserGroupWiget> groupWigets = new HashSet<>();

		if (result.getDtoEntity().getWigets() != null && result.getDtoEntity().getWigets().size() > 0) {

			final Set<UserGroupWiget> currentUserGroupWiget = result.getDomainEntity().getWigetList();

			for (final Widgets widgets : result.getDtoEntity().getWigets()) {
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
		if (result.getDomainEntity().getId() != null && result.getDomainEntity().getId() > 0) {
			result.getDomainEntity().setMenuList(new HashSet<UserGroupMenu>());
		}
	}

	private UserGroupResult createUserGroupResult(UserGroupDTO dto) {
		UserGroupResult result;
		if (dto.getId() != null && dto.getId() > 0) {
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
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public List<GenericCheckBox<Menu, SubMenu>> getMenuPermissions() {
		final List<GenericCheckBox<Menu, SubMenu>> menuPermissionlist = new ArrayList<GenericCheckBox<Menu, SubMenu>>();
		final Menu[] menuList = findMenuByBusiness();
		for (final Menu menu : menuList) {
			final GenericCheckBox<Menu, SubMenu> cBox = new GenericCheckBox<Menu, SubMenu>(menu, null, false);
			final List<SubMenu> subMenuList = SubMenu.getSubMenuByMenu(menu);
			final List<GenericCheckBox<Menu, SubMenu>> sMenulist = new ArrayList<GenericCheckBox<Menu, SubMenu>>();
			for (final SubMenu sMenu : subMenuList) {
				sMenulist.add(new GenericCheckBox<Menu, SubMenu>(null, sMenu, false));
			}
			cBox.setChildList(sMenulist);
			menuPermissionlist.add(cBox);
		}
		return menuPermissionlist;
	}

	public List<PermisonTreeDTO> getMenuAll() {
		final List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		final Menu[] menuList = findMenuByBusiness();
		for (final Menu menu : menuList) {
			final PermisonTreeDTO menudto = new PermisonTreeDTO();
			menudto.setId(menu.getId());
			menudto.setText(menu.getName());
			menudto.setAnyChildren(Boolean.TRUE);
			menudto.setType("MENU");
			menuPermissionlist.add(menudto);
		}
		return menuPermissionlist;
	}

	public List<PermisonTreeDTO> getSubMenuAll(Integer id) {
		final List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		final Menu[] menuList = findMenuByBusiness();
		for (final SubMenu menu : SubMenu.getSubMenuByMenu(Menu.getMenuById(id))) {
			final PermisonTreeDTO menudto = new PermisonTreeDTO();
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
		final List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		for (final Page page : Page.findPageBySubMenu(SubMenu.getSubMenuById(id))) {
			final PermisonTreeDTO menudto = new PermisonTreeDTO();
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
		final List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		for (final PagePermission pagePermission : PagePermission.getPagePermissionsByPage(Page.getPageById(id))) {
			final PermisonTreeDTO menudto = new PermisonTreeDTO();
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
		final List<PermisonTreeDTO> menuPermissionlist = new ArrayList<PermisonTreeDTO>();
		final Menu[] menuList = findMenuByBusiness();

		if (id != null) {
			final UserGroup domain = userGroupDao.findById(id);
			int i = 0;
			for (final Menu menu : menuList) {
				final PermisonTreeDTO menudto = new PermisonTreeDTO();
				menudto.setId(menu.getId());
				menudto.setText(menu.getName());
				menudto.setAnyChildren(Boolean.TRUE);
				menudto.setType("MENU");
				int k = 0;
				for (final SubMenu subMenu : SubMenu.getSubMenuByMenu(menu)) {
					final PermisonTreeDTO submenudto = new PermisonTreeDTO();
					submenudto.setId(subMenu.getId());
					submenudto.setText(subMenu.getName());
					submenudto.setAnyChildren(Boolean.TRUE);
					submenudto.setType("SUBMENU");
					int j = 0;
					for (final Page page : Page.findPageBySubMenu(subMenu)) {
						final PermisonTreeDTO pageDto = new PermisonTreeDTO();
						pageDto.setId(page.getId());
						pageDto.setText(page.getName());
						pageDto.setAnyChildren(Boolean.TRUE);
						pageDto.setType("PAGE");
						int n = 0;
						for (final PagePermission pagePermission : PagePermission.getPagePermissionsByPage(page)) {
							final PermisonTreeDTO pagePermmisionDto = new PermisonTreeDTO();
							pagePermmisionDto.setId(pagePermission.getId());
							pagePermmisionDto.setText(pagePermission.getName());
							for (final UserGroupPage groupPage : domain.getPageList()) {
								for (final UserGroupPagePermission userGroupPagePermission : groupPage.getPermissionList()) {
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
			for (final Menu menu : menuList) {
				final PermisonTreeDTO menudto = new PermisonTreeDTO();
				menudto.setId(menu.getId());
				menudto.setText(menu.getName());
				menudto.setAnyChildren(Boolean.TRUE);
				menudto.setType("MENU");
				int k = 0;
				for (final SubMenu subMenu : SubMenu.getSubMenuByMenu(menu)) {
					final PermisonTreeDTO submenudto = new PermisonTreeDTO();
					submenudto.setId(subMenu.getId());
					submenudto.setText(subMenu.getName());
					submenudto.setAnyChildren(Boolean.TRUE);
					submenudto.setType("SUBMENU");
					int j = 0;
					for (final Page page : Page.findPageBySubMenu(subMenu)) {
						final PermisonTreeDTO pageDto = new PermisonTreeDTO();
						pageDto.setId(page.getId());
						pageDto.setText(page.getName());
						pageDto.setAnyChildren(Boolean.TRUE);
						pageDto.setType("PAGE");
						int n = 0;
						for (final PagePermission pagePermission : PagePermission.getPagePermissionsByPage(page)) {
							final PermisonTreeDTO pagePermmisionDto = new PermisonTreeDTO();
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
			final List<Menu> menus = new ArrayList<>();
			final Business business = businessDao.findById(AuthenticationUtil.getLoginUserBusiness().getId());
			for (final BusinessApp app : business.getBusinessApps()) {
				for (final AppMenu appMenu : app.getApp().getAppMenus()) {
					menus.add(appMenu.getMenu());
				}
			}

			Collections.sort(menus, (m1,m2)->{
				return m1.getId().compareTo(m2.getId());
			});

			return menus.toArray(new Menu[menus.size()]);
		}
	}

	@Override
	public List<Page> findPageListByBusiness() {
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			return Page.getPageList();
		} else {
			final List<Page> pages = new ArrayList<>();

			final Business business = businessDao.findById(AuthenticationUtil.getLoginUserBusiness().getId());
			for (final BusinessApp app : business.getBusinessApps()) {
				for (final AppMenu appMenu : app.getApp().getAppMenus()) {
					final List<SubMenu> submenus = SubMenu.getSubMenuByMenu(appMenu.getMenu());
					for (final SubMenu subMenu : submenus) {
						pages.addAll(Page.findPageBySubMenu(subMenu));
					}
				}
			}

			return pages;
		}
	}

	public List<GenericCheckBox<Page, PagePermission>> findPagePermissions(){

		final List<GenericCheckBox<Page, PagePermission>> menuPermissionlist = new ArrayList<GenericCheckBox<Page, PagePermission>>();

		final Menu[] menuList = findMenuByBusiness();

		for (final Menu menu : menuList) {

			final List<SubMenu> submenus = SubMenu.getSubMenuByMenu(menu);

			for (final SubMenu subMenu : submenus) {

				final List<Page> pages = Page.findPageBySubMenu(subMenu);

				for (final Page page : pages) {
					final GenericCheckBox<Page, PagePermission> pcBox = new GenericCheckBox<Page, PagePermission>(page, null, false);

					final List<PagePermission> pagePermissions = PagePermission.getPagePermissionsByPage(page);

					final List<GenericCheckBox<Page, PagePermission>> ppboxs = new ArrayList<GenericCheckBox<Page, PagePermission>>();

					for (final PagePermission pagePermission : pagePermissions) {
						final GenericCheckBox<Page, PagePermission> ppcBox = new GenericCheckBox<Page, PagePermission>(null, pagePermission, false);
						ppboxs.add(ppcBox);
					}

					pcBox.setChildList(ppboxs);

					menuPermissionlist.add(pcBox);
				}

			}

		}

		Collections.sort(menuPermissionlist, (c1,c2)->{
			return c1.getMain().getId().compareTo(c2.getMain().getId());
		});

		return menuPermissionlist;
	}
}
