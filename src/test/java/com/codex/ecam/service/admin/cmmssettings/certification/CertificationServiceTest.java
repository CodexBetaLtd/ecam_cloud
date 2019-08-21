package com.codex.ecam.service.admin.cmmssettings.certification;

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
public class CertificationServiceTest {

	@Autowired
	private CertificationServiceFindbyIdTestCase certificationServiceFindbyIdTestCase;
	
	@Autowired
	private CertificationServiceFindTableDataTestCase certificationServiceFindTableDataTestCase;
	@Autowired
	private CertificationServiceCreateTestCase certificationServiceCreateTestCase;
	
	@Autowired
	private CertificationServiceDeleteByIdTestCase certificationServiceDeleteByIdTestCase;
	
	@Autowired
	private CertificationServiceUpdateTestCase certificationServiceUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		certificationServiceCreateTestCase.initialize();
		certificationServiceFindTableDataTestCase.initialize();
		certificationServiceDeleteByIdTestCase.initialize();
		certificationServiceFindbyIdTestCase.initialize();
		certificationServiceUpdateTestCase.initialize();
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
		certificationServiceCreateTestCase.setTestName("Validate Certification type  Creation");
		certificationServiceCreateTestCase.setTestCondition(certificationServiceCreateTestCase.CONDITION_SAVE_ENTITY, CertificationDummyData.getInstance().getDummyDTOCertificationOne());
		certificationServiceCreateTestCase.setExpectedResult(certificationServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(certificationServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		certificationServiceFindbyIdTestCase.setTestName("Validate Certification type  Find by id");
		certificationServiceFindbyIdTestCase.setTestCondition(certificationServiceFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Certification type 1");
		certificationServiceFindbyIdTestCase.setExpectedResult(certificationServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(certificationServiceFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		certificationServiceUpdateTestCase.setTestName("Validate Certification type  Update");
		certificationServiceUpdateTestCase.setTestCondition(certificationServiceUpdateTestCase.UPDATE_ENTITY_NAME,CertificationServiceUpdateTestCase.FieldName.CERTIFICATION_TYPE.name() );
		certificationServiceUpdateTestCase.setTestCondition(certificationServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Asset Event Type" );
		certificationServiceUpdateTestCase.setExpectedResult(certificationServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(certificationServiceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		certificationServiceFindTableDataTestCase.setTestName("Validate Certification type  Find Table Data");
		certificationServiceFindTableDataTestCase.setTestCondition(certificationServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		certificationServiceFindTableDataTestCase.setTestCondition(certificationServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Certification type 1");
		certificationServiceFindTableDataTestCase.setTestCondition(certificationServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Certification type 2");
		certificationServiceFindTableDataTestCase.setExpectedResult(certificationServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(certificationServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		certificationServiceDeleteByIdTestCase.setTestName("Validate  Certification type   Delete by id");
		certificationServiceDeleteByIdTestCase.setExpectedResult(certificationServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(certificationServiceDeleteByIdTestCase.isTestPass());
	}
}
