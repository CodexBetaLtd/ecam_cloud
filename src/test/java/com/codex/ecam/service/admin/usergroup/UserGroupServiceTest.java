package com.codex.ecam.service.admin.usergroup;

import com.codex.ecam.config.SpringConfiguration;
import com.codex.ecam.config.SpringSecurityConfiguration;
import com.codex.ecam.config.TestSpringConfiguration;
import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dto.admin.UserCredentialDTO;
import com.codex.ecam.security.CurrentUser;
import com.codex.ecam.service.admin.api.UserCredentialService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSpringConfiguration.class, SpringConfiguration.class, SpringSecurityConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class UserGroupServiceTest {
	
	@Autowired
	private UserGroupServiceFindAllTestCase userGroupServiceFindAllTestCase;
	
	@Autowired
	private UserGroupServiceFindTableDataTestCase userGroupServiceFindTableDataTestCase;
	
	@Autowired
	private UserGroupServiceFindByIdTestCase userGroupServiceFindByIdTestCase;
	
	@Autowired
	private UserGroupServiceDeleteByIdTestCase userGroupServiceDeleteByIdTestCase;
	
	@Autowired
	private UserGroupServiceCreateTestCase userGroupServiceCreateTestCase;
	
	@Autowired
	private UserGroupServiceUpdateTestCase userGroupServiceUpdateTestCase;

	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		userGroupServiceCreateTestCase.initialize();
		userGroupServiceUpdateTestCase.initialize();
		userGroupServiceFindByIdTestCase.initialize();
		userGroupServiceFindAllTestCase.initialize();
		userGroupServiceFindTableDataTestCase.initialize();		
		userGroupServiceDeleteByIdTestCase.initialize();
	}
	
	private void addMockAunthentication() {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER_ROLE");
		UserCredentialDTO userDto = userCredentialService.findByUserName("admin");
		CurrentUser usr = new CurrentUser(userDto, authorities, userDao.findById(userDto.getUserId()));
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usr, "", authorities);
		authentication.setDetails(usr);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@After
	public void teardown() {
		
	}
	
	@Test
	public void testCreateDefault() throws Exception {
		userGroupServiceCreateTestCase.setTestName("Validate User Group Creation");
		userGroupServiceCreateTestCase.setTestCondition(userGroupServiceCreateTestCase.CONDITION_SAVE_ENTITY, UserGroupDummyData.getInstance().getDummyDTOUserGroup());
		userGroupServiceCreateTestCase.setExpectedResult(userGroupServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(userGroupServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		userGroupServiceUpdateTestCase.setTestName("Validate User Group Update");
		userGroupServiceUpdateTestCase.setTestCondition(userGroupServiceUpdateTestCase.UPDATE_ENTITY_NAME, UserGroupServiceUpdateTestCase.FieldName.NAME.name() );
		userGroupServiceUpdateTestCase.setTestCondition(userGroupServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Test" );
		userGroupServiceUpdateTestCase.setExpectedResult(userGroupServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(userGroupServiceUpdateTestCase.isTestPass());
	}

	@Test
	public void testUpdateWithSubMenuChange() throws Exception {
		userGroupServiceUpdateTestCase.setTestName("Validate User Group Update");
		userGroupServiceUpdateTestCase.setTestCondition(userGroupServiceUpdateTestCase.UPDATE_ENTITY_NAME, UserGroupServiceUpdateTestCase.FieldName.SUB_MENUS.name() );
		List<SubMenu> list = new ArrayList<>(1);
		list.add(SubMenu.USERS);
		userGroupServiceUpdateTestCase.setTestCondition(userGroupServiceUpdateTestCase.UPDATE_ENTITY_VALUE, list );
		userGroupServiceUpdateTestCase.setExpectedResult(userGroupServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(userGroupServiceUpdateTestCase.isTestPass());
	}

	@Test
	public void testFindByIdWithValidId() throws Exception {
		userGroupServiceFindByIdTestCase.setTestName("Validate User Group Find by id with valid id");
		userGroupServiceFindByIdTestCase.setTestCondition(userGroupServiceFindByIdTestCase.CONDITION_EXPECTED_ID, 1);
		userGroupServiceFindByIdTestCase.setTestCondition(userGroupServiceFindByIdTestCase.CONDITION_EXPECTED_NAME, "Admin");
		userGroupServiceFindByIdTestCase.setExpectedResult(userGroupServiceFindByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(userGroupServiceFindByIdTestCase.isTestPass());
	}
	
	@Test
	public void testFindByIdWithInValidId() throws Exception {
		userGroupServiceFindByIdTestCase.setTestName("Validate User Group Find by id with invalid id");
		userGroupServiceFindByIdTestCase.setTestCondition(userGroupServiceFindByIdTestCase.CONDITION_EXPECTED_ID, 10);
		userGroupServiceFindByIdTestCase.setTestCondition(userGroupServiceFindByIdTestCase.CONDITION_EXPECTED_NAME, "testerror");
		userGroupServiceFindByIdTestCase.setExpectedResult(userGroupServiceFindByIdTestCase.RESULT_IS_ERROR, true);
		assertTrue(userGroupServiceFindByIdTestCase.isTestPass());
	}
	
	@Test
	public void testFindByIdWithNullId() throws Exception {
		userGroupServiceFindByIdTestCase.setTestName("Validate User Group Find by id with null id");
		userGroupServiceFindByIdTestCase.setTestCondition(userGroupServiceFindByIdTestCase.CONDITION_EXPECTED_ID, null);
		userGroupServiceFindByIdTestCase.setTestCondition(userGroupServiceFindByIdTestCase.CONDITION_EXPECTED_NAME, "testerror");
		userGroupServiceFindByIdTestCase.setExpectedResult(userGroupServiceFindByIdTestCase.RESULT_IS_ERROR, true);
		assertTrue(userGroupServiceFindByIdTestCase.isTestPass());
	}
	
	@Test
	public void testFindAll() throws Exception {
		userGroupServiceFindAllTestCase.setTestName("Validate User Group Find All");
		userGroupServiceFindAllTestCase.setTestCondition(userGroupServiceFindAllTestCase.CONDITION_EXPECTED_COUNT, 2);
		userGroupServiceFindAllTestCase.setTestCondition(userGroupServiceFindAllTestCase.CONDITION_EXPECTED_FIRST_ID, 1);
		userGroupServiceFindAllTestCase.setTestCondition(userGroupServiceFindAllTestCase.CONDITION_EXPECTED_FIRST_NAME, "Admin");
		userGroupServiceFindAllTestCase.setTestCondition(userGroupServiceFindAllTestCase.CONDITION_EXPECTED_SECOND_ID, 2);
		userGroupServiceFindAllTestCase.setTestCondition(userGroupServiceFindAllTestCase.CONDITION_EXPECTED_SECOND_NAME, "HR Manager");
		userGroupServiceFindAllTestCase.setExpectedResult(userGroupServiceFindAllTestCase.RESULT_IS_ERROR, false);
		assertTrue(userGroupServiceFindAllTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		userGroupServiceFindTableDataTestCase.setTestName("Validate User Group Find Table Data");
		userGroupServiceFindTableDataTestCase.setTestCondition(userGroupServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		userGroupServiceFindTableDataTestCase.setTestCondition(userGroupServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_ID, 1);
		userGroupServiceFindTableDataTestCase.setTestCondition(userGroupServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Admin");
		userGroupServiceFindTableDataTestCase.setTestCondition(userGroupServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_ID, 2);
		userGroupServiceFindTableDataTestCase.setTestCondition(userGroupServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "HR Manager");
		userGroupServiceFindTableDataTestCase.setExpectedResult(userGroupServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(userGroupServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		userGroupServiceDeleteByIdTestCase.setTestName("Validate User Group Delete by id with valid id");
		userGroupServiceDeleteByIdTestCase.setExpectedResult(userGroupServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(userGroupServiceDeleteByIdTestCase.isTestPass());
	}	

}
