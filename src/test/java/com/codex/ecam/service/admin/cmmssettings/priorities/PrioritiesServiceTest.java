package com.codex.ecam.service.admin.cmmssettings.priorities;

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
public class PrioritiesServiceTest {

	@Autowired
	private PrioritiesServiceFindbyIdTestCase prioritiesServiceFindbyIdTestCase;
	
	@Autowired
	private PrioritiesServiceFindTableDataTestCase prioritiesServiceFindTableDataTestCase;
	@Autowired
	private PrioritiesServiceCreateTestCase prioritiesServiceCreateTestCase;
	
	@Autowired
	private PrioritiesServiceDeleteByIdTestCase prioritiesServiceDeleteByIdTestCase;
	
	@Autowired
	private PrioritiesServiceUpdateTestCase prioritiesServiceUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		prioritiesServiceCreateTestCase.initialize();
		prioritiesServiceFindTableDataTestCase.initialize();
		prioritiesServiceDeleteByIdTestCase.initialize();
		prioritiesServiceFindbyIdTestCase.initialize();
		prioritiesServiceUpdateTestCase.initialize();
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
		prioritiesServiceCreateTestCase.setTestName("Validate Priority Creation");
		prioritiesServiceCreateTestCase.setTestCondition(prioritiesServiceCreateTestCase.CONDITION_SAVE_ENTITY, PrioritiesDummyData.getInstance().getDummyDTOPrioritiesOne());
		prioritiesServiceCreateTestCase.setExpectedResult(prioritiesServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(prioritiesServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		prioritiesServiceFindbyIdTestCase.setTestName("Validate Priority Find by id");
		prioritiesServiceFindbyIdTestCase.setTestCondition(prioritiesServiceFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Priority 1");
		prioritiesServiceFindbyIdTestCase.setExpectedResult(prioritiesServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(prioritiesServiceFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		prioritiesServiceUpdateTestCase.setTestName("Validate Priority Update");
		prioritiesServiceUpdateTestCase.setTestCondition(prioritiesServiceUpdateTestCase.UPDATE_ENTITY_NAME,PrioritiesServiceUpdateTestCase.FieldName.NAME.name() );
		prioritiesServiceUpdateTestCase.setTestCondition(prioritiesServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Priority" );
		prioritiesServiceUpdateTestCase.setExpectedResult(prioritiesServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(prioritiesServiceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		prioritiesServiceFindTableDataTestCase.setTestName("Validate Priority Find Table Data");
		prioritiesServiceFindTableDataTestCase.setTestCondition(prioritiesServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		prioritiesServiceFindTableDataTestCase.setTestCondition(prioritiesServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Test Priority 1");
		prioritiesServiceFindTableDataTestCase.setTestCondition(prioritiesServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Test Priority 2");
		prioritiesServiceFindTableDataTestCase.setExpectedResult(prioritiesServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(prioritiesServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		prioritiesServiceDeleteByIdTestCase.setTestName("Validate  Priority Delete by id");
		prioritiesServiceDeleteByIdTestCase.setExpectedResult(prioritiesServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(prioritiesServiceDeleteByIdTestCase.isTestPass());
	}
}
