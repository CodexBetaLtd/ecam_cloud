package com.codex.ecam.service.admin.cmmssettings.maintainacetype;

import com.codex.ecam.config.SpringConfiguration;
import com.codex.ecam.config.SpringSecurityConfiguration;
import com.codex.ecam.config.TestSpringConfiguration;
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

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSpringConfiguration.class, SpringConfiguration.class, SpringSecurityConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class MaintainaceTypeServiceTest {

	@Autowired
	private MaintainaceTypeServiceFindbyIdTestCase maintainaceTypeServiceFindbyIdTestCase;
	
	@Autowired
	private MaintainaceTypeServiceFindTableDataTestCase maintainaceTypeServiceFindTableDataTestCase;
	@Autowired
	private MaintainanceTypeServiceCreateTestCase maintainanceTypeServiceCreateTestCase;
	
	@Autowired
	private MaintainanceTypeServiceDeleteByIdTestCase maintainanceTypeServiceDeleteByIdTestCase;
	
	@Autowired
	private MaintainanceTypeServiceUpdateTestCase maintainanceTypeServiceUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		maintainanceTypeServiceCreateTestCase.initialize();
		maintainaceTypeServiceFindTableDataTestCase.initialize();
		maintainanceTypeServiceDeleteByIdTestCase.initialize();
		maintainaceTypeServiceFindbyIdTestCase.initialize();
		maintainanceTypeServiceUpdateTestCase.initialize();
	}
	@After
	public void teardown() {
	}
	private void addMockAunthentication() {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER_ROLE");
		UserCredentialDTO userDto = userCredentialService.findByUserName("admin");
		CurrentUser usr = new CurrentUser(userDto, authorities, userDao.findById(userDto.getUserId()));
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usr, "", authorities);
		authentication.setDetails(usr);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Test
	public void testCreateDefault() throws Exception {
		maintainanceTypeServiceCreateTestCase.setTestName("Validate  Maintainance Type Creation");
		maintainanceTypeServiceCreateTestCase.setTestCondition(maintainanceTypeServiceCreateTestCase.CONDITION_SAVE_ENTITY, MaintainanceTypeDummyData.getInstance().getDummyDTOMaintainanceTwo());
		maintainanceTypeServiceCreateTestCase.setExpectedResult(maintainanceTypeServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(maintainanceTypeServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		maintainaceTypeServiceFindbyIdTestCase.setTestName("Validate  Maintainance Type Find by id");
		maintainaceTypeServiceFindbyIdTestCase.setTestCondition(maintainaceTypeServiceFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Maintainance Type 1");
		maintainaceTypeServiceFindbyIdTestCase.setExpectedResult(maintainaceTypeServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(maintainaceTypeServiceFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		maintainanceTypeServiceUpdateTestCase.setTestName("Validate Maintainance Type Update");
		maintainanceTypeServiceUpdateTestCase.setTestCondition(maintainanceTypeServiceUpdateTestCase.UPDATE_ENTITY_NAME,MaintainanceTypeServiceUpdateTestCase.FieldName.NAME.name() );
		maintainanceTypeServiceUpdateTestCase.setTestCondition(maintainanceTypeServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated  Maintainance Type" );
		maintainanceTypeServiceUpdateTestCase.setExpectedResult(maintainanceTypeServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(maintainanceTypeServiceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		maintainaceTypeServiceFindTableDataTestCase.setTestName("Validate  Maintainance Type Find Table Data");
		maintainaceTypeServiceFindTableDataTestCase.setTestCondition(maintainaceTypeServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		maintainaceTypeServiceFindTableDataTestCase.setTestCondition(maintainaceTypeServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Test Maintainance Type 1");
		maintainaceTypeServiceFindTableDataTestCase.setTestCondition(maintainaceTypeServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Test Maintainance Type 2");
		maintainaceTypeServiceFindTableDataTestCase.setExpectedResult(maintainaceTypeServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(maintainaceTypeServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		maintainanceTypeServiceDeleteByIdTestCase.setTestName("Validate  Maintainance Type  Delete by id");
		maintainanceTypeServiceDeleteByIdTestCase.setExpectedResult(maintainanceTypeServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(maintainanceTypeServiceDeleteByIdTestCase.isTestPass());
	}
}
