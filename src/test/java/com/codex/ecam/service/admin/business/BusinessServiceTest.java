package com.codex.ecam.service.admin.business;

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
public class BusinessServiceTest {
	
	@Autowired
	private BusinessServiceCreateTestCase businessServiceCreateTestCase;
	
	@Autowired
	private BusinessServiceFindTableDataTestCase businessServiceFindTableDataTestCase;
	
	@Autowired
	private BusinessServiceDeleteByIdTestCase businessServiceDeleteByIdTestCase;
	
	@Autowired
	private BusinessServiceUpdateTestCase businessServiceUpdateTestCase;
	
	@Autowired
	private BusinessServiceFindByIdTestCase businessServiceFindByIdTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		businessServiceCreateTestCase.initialize();
		businessServiceFindTableDataTestCase.initialize();
		businessServiceDeleteByIdTestCase.initialize();
		businessServiceUpdateTestCase.initialize();
		businessServiceFindByIdTestCase.initialize();
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
		businessServiceCreateTestCase.setTestName("Validate Business Creation");
		businessServiceCreateTestCase.setTestCondition(businessServiceCreateTestCase.CONDITION_SAVE_ENTITY, BusinessDummyData.getInstance().getDummyDTOBusinessOne());
		businessServiceCreateTestCase.setExpectedResult(businessServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(businessServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		businessServiceFindByIdTestCase.setTestName("Validate Business Find by id");
		businessServiceFindByIdTestCase.setTestCondition(businessServiceFindByIdTestCase.CONDITION_EXPECTED_NAME, "Business Test_1");
		businessServiceFindByIdTestCase.setExpectedResult(businessServiceFindByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(businessServiceFindByIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		businessServiceUpdateTestCase.setTestName("Validate Business Update");
		businessServiceUpdateTestCase.setTestCondition(businessServiceUpdateTestCase.UPDATE_ENTITY_NAME, BusinessServiceUpdateTestCase.FieldName.NAME.name() );
		businessServiceUpdateTestCase.setTestCondition(businessServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Business" );
		businessServiceUpdateTestCase.setExpectedResult(businessServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(businessServiceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		businessServiceFindTableDataTestCase.setTestName("Validate Business Find Table Data");
		businessServiceFindTableDataTestCase.setTestCondition(businessServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		businessServiceFindTableDataTestCase.setTestCondition(businessServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Business Test_1");
		businessServiceFindTableDataTestCase.setTestCondition(businessServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Business Test_2");
		businessServiceFindTableDataTestCase.setExpectedResult(businessServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(businessServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		businessServiceDeleteByIdTestCase.setTestName("Validate Business Delete by id");
		businessServiceDeleteByIdTestCase.setExpectedResult(businessServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(businessServiceDeleteByIdTestCase.isTestPass());
	}

}
