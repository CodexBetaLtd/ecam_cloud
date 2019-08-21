package com.neolith.focus.service.admin.usergroup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.constants.Menu;
import com.neolith.focus.constants.SubMenu;
import com.neolith.focus.dto.admin.UserGroupDTO;
import com.neolith.focus.result.admin.UserGroupResult;
import com.neolith.focus.service.admin.api.UserGroupService;

@Component
public class UserGroupServiceUpdateTestCase  extends TestCase {

	public static enum FieldName { NONE, NAME, DESCRIPTION, MENUS, SUB_MENUS };

	@Autowired
	private UserGroupService userGroupService;

	protected final String UPDATE_ENTITY_NAME = "name";

	protected final String UPDATE_ENTITY_VALUE = "value";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		UserGroupResult result = null;
		try {

			UserGroupDTO userGroup = UserGroupDummyData.getInstance().getDummyDTOUserGroup();
			result = userGroupService.save(userGroup);

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), userGroup);

			userGroupService.save(result.getDtoEntity());
			UserGroupDTO updatedGroup = userGroupService.findById(result.getDomainEntity().getId());

			Assert.assertNotNull("User Group is null.", updatedGroup);
			Assert.assertTrue("User Group not updated.", isFieldUpdated(updatedGroup, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			userGroupService.delete(result.getDomainEntity().getId());
		}
	}

	@SuppressWarnings("unchecked")
	private boolean isFieldUpdated(UserGroupDTO userGroup, String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return userGroup.getName().equals(value);

		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			return userGroup.getDescription().equals(value);

		} else if (name.equals(FieldName.MENUS.name())) {
			return isMenuUpdated(userGroup.getTopMenus(), (List<Menu>) value);

		} else if (name.equals(FieldName.SUB_MENUS.name())) {
			return isSubMenuUpdated(userGroup.getSubMenus(), (List<SubMenu>) value);
		}
		return false;
	}

	private boolean isSubMenuUpdated(List<SubMenu> subMenus, List<SubMenu> value) {
		List<SubMenu> different = new ArrayList<SubMenu>();

		different.addAll(subMenus);
		different.addAll(value);

		different.removeAll(value);
		return different.size() > 0 ? false : true;
	}

	private boolean isMenuUpdated(List<Menu> topMenus, List<Menu> value) {
		List<Menu> different = new ArrayList<Menu>();

		different.addAll(topMenus);
		different.addAll(value);

		different.removeAll(value);
		return different.size() > 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	private void setGivenValue(String name, Object value, UserGroupDTO userGroup) {

		if (name.equals(FieldName.NAME.name())) {
			userGroup.setName((String) value);

		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			userGroup.setDescription((String) value);

		} else if (name.equals(FieldName.MENUS.name())) {
			userGroup.setTopMenus((List<Menu>) value);

		} else if (name.equals(FieldName.SUB_MENUS.name())) {
			userGroup.setSubMenus((List<SubMenu>) value);
		}

	}

}
