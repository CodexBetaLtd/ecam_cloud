package com.codex.ecam.service.asset.assetCategory;

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
public class AssetCategoryServiceTest {
	@Autowired
	private AssetCategoryServiceFindbyIdTestCase  assetCategoryFindbyIdTestCase;
	
	@Autowired
	private AssetCategoryServiceFindTableDataTestCase assetCategoryServiceFindTableDataTestCase;
	@Autowired
	private AssetCategoryServiceCreateTestCase  assetCategoryServiceCreateTestCase;
	
	@Autowired
	private AssetCategoryServiceDeleteByIdTestCase  assetCategoryDeleteByIdTestCase;
	
	@Autowired
	private AssetCategoryServiceUpdateTestCase assetCategoryUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		assetCategoryServiceCreateTestCase.initialize();
		assetCategoryServiceFindTableDataTestCase.initialize();
		assetCategoryDeleteByIdTestCase.initialize();
		assetCategoryFindbyIdTestCase.initialize();
		assetCategoryUpdateTestCase.initialize();
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
		assetCategoryServiceCreateTestCase.setTestName("Validate Asset Category Creation");
		assetCategoryServiceCreateTestCase.setTestCondition(assetCategoryServiceCreateTestCase.CONDITION_SAVE_ENTITY, AssetCategoryDummyData.getInstance().getDummyDTOAssetCategoryOne());
		assetCategoryServiceCreateTestCase.setExpectedResult(assetCategoryServiceCreateTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetCategoryServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		assetCategoryFindbyIdTestCase.setTestName("Validate Asset Category Find by id");
		assetCategoryFindbyIdTestCase.setTestCondition(assetCategoryFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Asset Category 1");
		assetCategoryFindbyIdTestCase.setExpectedResult(assetCategoryFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetCategoryFindbyIdTestCase.isTestPass());
	}
	
	@Test
	public void testUpdateDefault() throws Exception {
		assetCategoryUpdateTestCase.setTestName("Validate Asset Category Update");
		assetCategoryUpdateTestCase.setTestCondition(assetCategoryUpdateTestCase.UPDATE_ENTITY_NAME, AssetCategoryServiceUpdateTestCase.FieldName.NAME.name() );
		assetCategoryUpdateTestCase.setTestCondition(assetCategoryUpdateTestCase.UPDATE_ENTITY_VALUE, "Updated Asset category" );
		assetCategoryUpdateTestCase.setExpectedResult(assetCategoryUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetCategoryUpdateTestCase.isTestPass());
	}
	
	@Test
	public void testFindTableData() throws Exception {
		assetCategoryServiceFindTableDataTestCase.setTestName("Validate Asset Category Find Table Data");
		assetCategoryServiceFindTableDataTestCase.setTestCondition(assetCategoryServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		assetCategoryServiceFindTableDataTestCase.setTestCondition(assetCategoryServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Test Asset Category 1");
		assetCategoryServiceFindTableDataTestCase.setTestCondition(assetCategoryServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "Test Asset Category 2");
		assetCategoryServiceFindTableDataTestCase.setExpectedResult(assetCategoryServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetCategoryServiceFindTableDataTestCase.isTestPass());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		assetCategoryDeleteByIdTestCase.setTestName("Validate  Asset Category  Delete by id");
		assetCategoryDeleteByIdTestCase.setExpectedResult(assetCategoryDeleteByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(assetCategoryDeleteByIdTestCase.isTestPass());
	}
}
