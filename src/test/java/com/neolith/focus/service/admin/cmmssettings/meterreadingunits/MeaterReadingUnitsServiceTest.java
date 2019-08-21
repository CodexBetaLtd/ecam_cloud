package com.neolith.focus.service.admin.cmmssettings.meterreadingunits;

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
public class MeaterReadingUnitsServiceTest {

	@Autowired
	private MeaterReadingUnitsServiceFindbyIdTestCase meaterReadingUnitsServiceFindbyIdTestCase;
	
	@Autowired
	private MeterReadingUnitsServiceFindTableDataTestCase meterReadingUnitsServiceFindTableDataTestCase;
	@Autowired
	private MeaterReadingUnitsServiceCreateTestCase meaterReadingUnitsServiceCreateTestCase;
	
	@Autowired
	private MeaterReadingUnitsServiceDeleteByIdTestCase meaterReadingUnitsServiceDeleteByIdTestCase;
	
	@Autowired
	private MeterReadingUnitsServiceUpdateTestCase meterReadingUnitsServiceUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		meaterReadingUnitsServiceCreateTestCase.initialize();
		meterReadingUnitsServiceFindTableDataTestCase.initialize();
		meaterReadingUnitsServiceDeleteByIdTestCase.initialize();
		meaterReadingUnitsServiceFindbyIdTestCase.initialize();
		meterReadingUnitsServiceUpdateTestCase.initialize();
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
		meaterReadingUnitsServiceCreateTestCase.setTestName("Validate Meter Reading Units Creation");
		meaterReadingUnitsServiceCreateTestCase.setTestCondition(meaterReadingUnitsServiceCreateTestCase.CONDITION_SAVE_ENTITY, MeterReadingUnitsDummyData.getInstance().getDummyDTOMeterReadingUnitseOne());
		meaterReadingUnitsServiceCreateTestCase.setExpectedResult(meaterReadingUnitsServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(meaterReadingUnitsServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		meaterReadingUnitsServiceFindbyIdTestCase.setTestName("Validate Meter Reading Units Find by id");
		meaterReadingUnitsServiceFindbyIdTestCase.setTestCondition(meaterReadingUnitsServiceFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Meter Reading Units 1");
		meaterReadingUnitsServiceFindbyIdTestCase.setExpectedResult(meaterReadingUnitsServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(meaterReadingUnitsServiceFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		meterReadingUnitsServiceUpdateTestCase.setTestName("Validate  Meter Reading Units Update");
		meterReadingUnitsServiceUpdateTestCase.setTestCondition(meterReadingUnitsServiceUpdateTestCase.UPDATE_ENTITY_NAME,MeterReadingUnitsServiceUpdateTestCase.FieldName.NAME.name() );
		meterReadingUnitsServiceUpdateTestCase.setTestCondition(meterReadingUnitsServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated  Meter Reading Units" );
		meterReadingUnitsServiceUpdateTestCase.setExpectedResult(meterReadingUnitsServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(meterReadingUnitsServiceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		meterReadingUnitsServiceFindTableDataTestCase.setTestName("Validate Meter Reading Units Find Table Data");
		meterReadingUnitsServiceFindTableDataTestCase.setTestCondition(meterReadingUnitsServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		meterReadingUnitsServiceFindTableDataTestCase.setTestCondition(meterReadingUnitsServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Test Meter Reading Units 1");
		meterReadingUnitsServiceFindTableDataTestCase.setTestCondition(meterReadingUnitsServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Test Meter Reading Units 2");
		meterReadingUnitsServiceFindTableDataTestCase.setExpectedResult(meterReadingUnitsServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(meterReadingUnitsServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		meaterReadingUnitsServiceDeleteByIdTestCase.setTestName("Validate  Meter Reading Units Delete by id");
		meaterReadingUnitsServiceDeleteByIdTestCase.setExpectedResult(meaterReadingUnitsServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(meaterReadingUnitsServiceDeleteByIdTestCase.isTestPass());
	}
}
