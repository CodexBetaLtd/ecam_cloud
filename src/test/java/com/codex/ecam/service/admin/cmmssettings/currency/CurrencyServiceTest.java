package com.codex.ecam.service.admin.cmmssettings.currency;

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
public class CurrencyServiceTest {

	@Autowired
	private CurrencyServiceFindbyIdTestCase currencyServiceFindbyIdTestCase;
	
	@Autowired
	private CurrencyServiceFindTableDataTestCase currencyServiceFindTableDataTestCase;
	@Autowired
	private CurrencyServiceCreateTestCase currencyServiceCreateTestCase;
	
	@Autowired
	private CurrencyServiceDeleteByIdTestCase currencyServiceDeleteByIdTestCase;
	
	@Autowired
	private CurrencyServiceUpdateTestCase currencyServiceUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		currencyServiceCreateTestCase.initialize();
		currencyServiceFindTableDataTestCase.initialize();
		currencyServiceDeleteByIdTestCase.initialize();
		currencyServiceFindbyIdTestCase.initialize();
		currencyServiceUpdateTestCase.initialize();
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
		currencyServiceCreateTestCase.setTestName("Validate Currency Creation");
		currencyServiceCreateTestCase.setTestCondition(currencyServiceCreateTestCase.CONDITION_SAVE_ENTITY, CurrencyDummyData.getInstance().getDummyDTOCurrencyOne());
		currencyServiceCreateTestCase.setExpectedResult(currencyServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(currencyServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		currencyServiceFindbyIdTestCase.setTestName("Validate Currency Find by id");
		currencyServiceFindbyIdTestCase.setTestCondition(currencyServiceFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Currency 1");
		currencyServiceFindbyIdTestCase.setExpectedResult(currencyServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(currencyServiceFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		currencyServiceUpdateTestCase.setTestName("Validate Currency Update");
		currencyServiceUpdateTestCase.setTestCondition(currencyServiceUpdateTestCase.UPDATE_ENTITY_NAME,CurrencyServiceUpdateTestCase.FieldName.NAME.name() );
		currencyServiceUpdateTestCase.setTestCondition(currencyServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Currency" );
		currencyServiceUpdateTestCase.setExpectedResult(currencyServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(currencyServiceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		currencyServiceFindTableDataTestCase.setTestName("Validate Currency Find Table Data");
		currencyServiceFindTableDataTestCase.setTestCondition(currencyServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		currencyServiceFindTableDataTestCase.setTestCondition(currencyServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Test Currency 1");
		currencyServiceFindTableDataTestCase.setTestCondition(currencyServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Test Currency2");
		currencyServiceFindTableDataTestCase.setExpectedResult(currencyServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(currencyServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		currencyServiceDeleteByIdTestCase.setTestName("Validate  Currency  Delete by id");
		currencyServiceDeleteByIdTestCase.setExpectedResult(currencyServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(currencyServiceDeleteByIdTestCase.isTestPass());
	}
}
