package com.neolith.focus.service.asset;

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
public class AssetServiceTest {
	
	@Autowired
	private AssetServiceCreateTestCase assetServiceCreateTestCase;
	
	@Autowired
	private AssetServiceFindTableDataTestCase assetServiceFindTableDataTestCase;
	
	@Autowired
	private AssetServiceDeleteByIdTestCase assetServiceDeleteByIdTestCase;
	
	@Autowired
	private AssetServiceUpdateTestCase asseterviceUpdateTestCase;
	
	@Autowired
	private AssetServiceFindByIdTestCase assetServiceFindByIdTestCase;
	
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
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
	public void testCreateMachine() throws Exception {
		assetServiceCreateTestCase.setTestName("Validate Machine Creation");
		assetServiceCreateTestCase.setTestCondition(assetServiceCreateTestCase.CONDITION_SAVE_ENTITY, AssetDummyData.getInstance().getDummyDTOMachineAsset());
		assetServiceCreateTestCase.setExpectedResult(assetServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testCreateTool() throws Exception {
		assetServiceCreateTestCase.setTestName("Validate Tool Creation");
		assetServiceCreateTestCase.setTestCondition(assetServiceCreateTestCase.CONDITION_SAVE_ENTITY, AssetDummyData.getInstance().getDummyDTOToolsAsset());
		assetServiceCreateTestCase.setExpectedResult(assetServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testCreateFacility() throws Exception {
		assetServiceCreateTestCase.setTestName("Validate Facility Creation");
		assetServiceCreateTestCase.setTestCondition(assetServiceCreateTestCase.CONDITION_SAVE_ENTITY, AssetDummyData.getInstance().getDummyDTOFaciltyAsset());
		assetServiceCreateTestCase.setExpectedResult(assetServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		assetServiceFindByIdTestCase.setTestName("Validate Asset Find by id");
		assetServiceFindByIdTestCase.setTestCondition(assetServiceFindByIdTestCase.CONDITION_EXPECTED_NAME, "Asset Test_1");
		assetServiceFindByIdTestCase.setExpectedResult(assetServiceFindByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetServiceFindByIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		asseterviceUpdateTestCase.setTestName("Validate Asset Update");
		asseterviceUpdateTestCase.setTestCondition(asseterviceUpdateTestCase.UPDATE_ENTITY_NAME, AssetServiceUpdateTestCase.FieldName.NAME.name() );
		asseterviceUpdateTestCase.setTestCondition(asseterviceUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Business" );
		asseterviceUpdateTestCase.setExpectedResult(asseterviceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(asseterviceUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		assetServiceFindTableDataTestCase.setTestName("Validate Asset Find Table Data");
		assetServiceFindTableDataTestCase.setTestCondition(assetServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		assetServiceFindTableDataTestCase.setTestCondition(assetServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Business Test_1");
		assetServiceFindTableDataTestCase.setTestCondition(assetServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Business Test_2");
		assetServiceFindTableDataTestCase.setExpectedResult(assetServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		assetServiceDeleteByIdTestCase.setTestName("Validate Asset Delete by id");
		assetServiceDeleteByIdTestCase.setExpectedResult(assetServiceDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetServiceDeleteByIdTestCase.isTestPass());
	}

}
