package com.codex.ecam.service.admin.cmmssettings.asseteventtype;

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
public class AssetEventTypeServiceTest {

	@Autowired
	private AssetEventTypeServiceFindbyIdTestCase assetEventTypeFindbyIdTestCase;

	@Autowired
	private AssetEventTypeServiceFindTableDataTestCase assetEventTypeServiceFindTableDataTestCase;

	@Autowired
	private AssetEventTypeServiceCreateTestCase assetEventTypeServiceCreateTestCase;
	
	@Autowired
	private AssetEventTypeServiceDeleteByIdTestCase assetEventTypeDeleteByIdTestCase;
	
	@Autowired
	private AssetEventTypeServiceUpdateTestCase assetEventTypeUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		assetEventTypeServiceCreateTestCase.initialize();
		assetEventTypeServiceFindTableDataTestCase.initialize();
		assetEventTypeDeleteByIdTestCase.initialize();
		assetEventTypeFindbyIdTestCase.initialize();
		assetEventTypeUpdateTestCase.initialize();
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
		assetEventTypeServiceCreateTestCase.setTestName("Validate Asset Event Type Creation");
		assetEventTypeServiceCreateTestCase.setTestCondition(assetEventTypeServiceCreateTestCase.CONDITION_SAVE_ENTITY, AssetEventTypeDummyData.getInstance().getDummyDTOAssetEventTypeOne());
		assetEventTypeServiceCreateTestCase.setExpectedResult(assetEventTypeServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetEventTypeServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		assetEventTypeFindbyIdTestCase.setTestName("Validate Asset Event Type Find by id");
		assetEventTypeFindbyIdTestCase.setTestCondition(assetEventTypeFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Asset Event type 1");
		assetEventTypeFindbyIdTestCase.setExpectedResult(assetEventTypeFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetEventTypeFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		assetEventTypeUpdateTestCase.setTestName("Validate AAsset Event Type Update");
		assetEventTypeUpdateTestCase.setTestCondition(assetEventTypeUpdateTestCase.UPDATE_ENTITY_NAME,AssetEventTypeServiceUpdateTestCase.FieldName.NAME.name() );
		assetEventTypeUpdateTestCase.setTestCondition(assetEventTypeUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Asset Event Type" );
		assetEventTypeUpdateTestCase.setExpectedResult(assetEventTypeUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetEventTypeUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		assetEventTypeServiceFindTableDataTestCase.setTestName("Validate Asset Event Type Find Table Data");
		assetEventTypeServiceFindTableDataTestCase.setTestCondition(assetEventTypeServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		assetEventTypeServiceFindTableDataTestCase.setTestCondition(assetEventTypeServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Test Asset Event type 1");
		assetEventTypeServiceFindTableDataTestCase.setTestCondition(assetEventTypeServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Test Asset Event type 2");
		assetEventTypeServiceFindTableDataTestCase.setExpectedResult(assetEventTypeServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetEventTypeServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		assetEventTypeDeleteByIdTestCase.setTestName("Validate  Asset Event Type  Delete by id");
		assetEventTypeDeleteByIdTestCase.setExpectedResult(assetEventTypeDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetEventTypeDeleteByIdTestCase.isTestPass());
	}
}
