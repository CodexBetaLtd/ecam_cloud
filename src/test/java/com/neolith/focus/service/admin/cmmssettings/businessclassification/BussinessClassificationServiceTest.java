package com.neolith.focus.service.admin.cmmssettings.businessclassification;

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
public class BussinessClassificationServiceTest {

	@Autowired
	private BussinessClassificationServiceFindbyIdTestCase bussinessClassificationServiceFindbyIdTestCase;
	
	@Autowired
	private BussinessClassificatioServiceFindTableDataTestCase bussinessClassificatioServiceFindTableDataTestCase;
	@Autowired
	private BussinessClassificationServiceCreateTestCase bussinessClassificationServiceCreateTestCase;
	
	@Autowired
	private BussinessClassificatioServiceDeleteByIdTestCase bussinessClassificatioServiceDeleteByIdTestCase;
	
	@Autowired
	private BussinessClassificationServiceUpdateTestCase bussinessClassificationServiceUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		bussinessClassificationServiceCreateTestCase.initialize();
		bussinessClassificatioServiceFindTableDataTestCase.initialize();
		bussinessClassificatioServiceDeleteByIdTestCase.initialize();
		bussinessClassificationServiceFindbyIdTestCase.initialize();
		bussinessClassificationServiceUpdateTestCase.initialize();
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
		bussinessClassificationServiceCreateTestCase.setTestName("Validate Bussiness Classification Creation");
		bussinessClassificationServiceCreateTestCase.setTestCondition(bussinessClassificationServiceCreateTestCase.CONDITION_SAVE_ENTITY, BussinessClassificationDummyData.getInstance().getDummyDTOBussinessClassificationOne());
		bussinessClassificationServiceCreateTestCase.setExpectedResult(bussinessClassificationServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(bussinessClassificationServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		bussinessClassificationServiceFindbyIdTestCase.setTestName("Validate Bussiness Classification Find by id");
		bussinessClassificationServiceFindbyIdTestCase.setTestCondition(bussinessClassificationServiceFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Bussiness Classification 1");
		bussinessClassificationServiceFindbyIdTestCase.setExpectedResult(bussinessClassificationServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(bussinessClassificationServiceFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		bussinessClassificationServiceUpdateTestCase.setTestName("Validate Bussiness Classification Update");
		bussinessClassificationServiceUpdateTestCase.setTestCondition(bussinessClassificationServiceUpdateTestCase.UPDATE_ENTITY_NAME,BussinessClassificationServiceUpdateTestCase.FieldName.NAME.name() );
		bussinessClassificationServiceUpdateTestCase.setTestCondition(bussinessClassificationServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Bussiness Classification" );
		bussinessClassificationServiceUpdateTestCase.setExpectedResult(bussinessClassificationServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(bussinessClassificationServiceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		bussinessClassificatioServiceFindTableDataTestCase.setTestName("Validate Bussiness Classification Find Table Data");
		bussinessClassificatioServiceFindTableDataTestCase.setTestCondition(bussinessClassificatioServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		bussinessClassificatioServiceFindTableDataTestCase.setTestCondition(bussinessClassificatioServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Test Bussiness Classification 1");
		bussinessClassificatioServiceFindTableDataTestCase.setTestCondition(bussinessClassificatioServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Test Bussiness Classification 2");
		bussinessClassificatioServiceFindTableDataTestCase.setExpectedResult(bussinessClassificatioServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(bussinessClassificatioServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		bussinessClassificatioServiceDeleteByIdTestCase.setTestName("Validate Bussiness Classification Delete by id");
		bussinessClassificatioServiceDeleteByIdTestCase.setExpectedResult(bussinessClassificatioServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(bussinessClassificatioServiceDeleteByIdTestCase.isTestPass());
	}
}
