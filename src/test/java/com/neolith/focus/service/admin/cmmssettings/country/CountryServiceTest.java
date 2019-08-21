package com.neolith.focus.service.admin.cmmssettings.country;

import com.neolith.focus.config.SpringConfiguration;
import com.neolith.focus.config.SpringSecurityConfiguration;
import com.neolith.focus.config.TestSpringConfiguration;
import com.neolith.focus.dao.admin.UserDao;
import com.neolith.focus.dto.admin.UserCredentialDTO;
import com.neolith.focus.security.CurrentUser;
import com.neolith.focus.service.admin.api.UserCredentialService;
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
public class CountryServiceTest {

	@Autowired
	private CountryServiceFindbyIdTestCase countryServiceFindbyIdTestCase;
	
	@Autowired
	private CountryServiceFindTableDataTestCase countryServiceFindTableDataTestCase;
	@Autowired
	private CountryServiceCreateTestCase countryServiceCreateTestCase;
	
	@Autowired
	private CountryServiceDeleteByIdTestCase countryServiceDeleteByIdTestCase;
	
	@Autowired
	private CountryServiceUpdateTestCase countryServiceUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		countryServiceCreateTestCase.initialize();
		countryServiceFindTableDataTestCase.initialize();
		countryServiceDeleteByIdTestCase.initialize();
		countryServiceFindbyIdTestCase.initialize();
		countryServiceUpdateTestCase.initialize();
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
		countryServiceCreateTestCase.setTestName("Validate Country Creation");
		countryServiceCreateTestCase.setTestCondition(countryServiceCreateTestCase.CONDITION_SAVE_ENTITY, CountryDummyData.getInstance().getDummyDTOCountryOne());
		countryServiceCreateTestCase.setExpectedResult(countryServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(countryServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		countryServiceFindbyIdTestCase.setTestName("Validate Country Find by id");
		countryServiceFindbyIdTestCase.setTestCondition(countryServiceFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Country 1");
		countryServiceFindbyIdTestCase.setExpectedResult(countryServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(countryServiceFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		countryServiceUpdateTestCase.setTestName("Validate AAsset Event Type Update");
		countryServiceUpdateTestCase.setTestCondition(countryServiceUpdateTestCase.UPDATE_ENTITY_NAME,CountryServiceUpdateTestCase.FieldName.NAME.name() );
		countryServiceUpdateTestCase.setTestCondition(countryServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Country" );
		countryServiceUpdateTestCase.setExpectedResult(countryServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(countryServiceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		countryServiceFindTableDataTestCase.setTestName("Validate Country Find Table Data");
		countryServiceFindTableDataTestCase.setTestCondition(countryServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		countryServiceFindTableDataTestCase.setTestCondition(countryServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Test Country 1");
		countryServiceFindTableDataTestCase.setTestCondition(countryServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Test Country 2");
		countryServiceFindTableDataTestCase.setExpectedResult(countryServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(countryServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		countryServiceDeleteByIdTestCase.setTestName("Validate  Country  Delete by id");
		countryServiceDeleteByIdTestCase.setExpectedResult(countryServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(countryServiceDeleteByIdTestCase.isTestPass());
	}
}
