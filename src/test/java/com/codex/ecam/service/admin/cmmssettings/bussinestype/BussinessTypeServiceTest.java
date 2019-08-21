package com.codex.ecam.service.admin.cmmssettings.bussinestype;

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
public class BussinessTypeServiceTest {

	@Autowired
	private BussinessTypeServiceFindbyIdTestCase bussinessTypeServiceFindbyIdTestCase;
	
	@Autowired
	private BussinessTypeServiceFindTableDataTestCase bussinessTypeServiceFindTableDataTestCase;
	@Autowired
	private BusinessTypeServiceCreateTestCase businessTypeServiceCreateTestCase;
	
	@Autowired
	private BusinessTypeServiceDeleteByIdTestCase businessTypeServiceDeleteByIdTestCase;
	
	@Autowired
	private BussinessTypeServiceUpdateTestCase bussinessTypeServiceUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		businessTypeServiceCreateTestCase.initialize();
		bussinessTypeServiceFindTableDataTestCase.initialize();
		businessTypeServiceDeleteByIdTestCase.initialize();
		bussinessTypeServiceFindbyIdTestCase.initialize();
		bussinessTypeServiceUpdateTestCase.initialize();
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
		businessTypeServiceCreateTestCase.setTestName("Validate Bussiness Type Creation");
		businessTypeServiceCreateTestCase.setTestCondition(businessTypeServiceCreateTestCase.CONDITION_SAVE_ENTITY, BussinessTypeDummyData.getInstance().getDummyDTOBussinessTypeOne());
		businessTypeServiceCreateTestCase.setExpectedResult(businessTypeServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(businessTypeServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		bussinessTypeServiceFindbyIdTestCase.setTestName("Validate Bussiness Type Find by id");
		bussinessTypeServiceFindbyIdTestCase.setTestCondition(bussinessTypeServiceFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Bussiness type 1");
		bussinessTypeServiceFindbyIdTestCase.setExpectedResult(bussinessTypeServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(bussinessTypeServiceFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		bussinessTypeServiceUpdateTestCase.setTestName("Validate Bussiness Type Update");
		bussinessTypeServiceUpdateTestCase.setTestCondition(bussinessTypeServiceUpdateTestCase.UPDATE_ENTITY_NAME,BussinessTypeServiceUpdateTestCase.FieldName.BUSINESS_TYPE_DEFINITION_NAME.name() );
		bussinessTypeServiceUpdateTestCase.setTestCondition(bussinessTypeServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Bussiness Type" );
		bussinessTypeServiceUpdateTestCase.setExpectedResult(bussinessTypeServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(bussinessTypeServiceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		bussinessTypeServiceFindTableDataTestCase.setTestName("Validate Bussiness Type Find Table Data");
		bussinessTypeServiceFindTableDataTestCase.setTestCondition(bussinessTypeServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		bussinessTypeServiceFindTableDataTestCase.setTestCondition(bussinessTypeServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Test Bussiness type 1");
		bussinessTypeServiceFindTableDataTestCase.setTestCondition(bussinessTypeServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Test Bussiness type 2");
		bussinessTypeServiceFindTableDataTestCase.setExpectedResult(bussinessTypeServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(bussinessTypeServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		businessTypeServiceDeleteByIdTestCase.setTestName("Validate  Bussiness Type  Delete by id");
		businessTypeServiceDeleteByIdTestCase.setExpectedResult(businessTypeServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(businessTypeServiceDeleteByIdTestCase.isTestPass());
	}
}
