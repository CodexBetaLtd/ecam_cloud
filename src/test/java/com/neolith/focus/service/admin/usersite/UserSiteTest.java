package com.neolith.focus.service.admin.usersite;

import com.neolith.focus.config.SpringConfiguration;
import com.neolith.focus.config.SpringSecurityConfiguration;
import com.neolith.focus.config.TestSpringConfiguration;
import com.neolith.focus.service.admin.usersite.testCase.UserSiteServiceDeleteTestCase;
import com.neolith.focus.service.admin.usersite.testCase.UserSiteServiceFindByIdTestCase;
import com.neolith.focus.service.admin.usersite.testCase.UserSiteServiceSaveTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSpringConfiguration.class, SpringConfiguration.class, SpringSecurityConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class UserSiteTest {

	@Autowired
	private UserSiteServiceSaveTestCase userSiteServiceSaveTestCase;
	@Autowired
	private UserSiteServiceFindByIdTestCase userSiteServiceFindByIdTestCase;
	@Autowired
	private UserSiteServiceDeleteTestCase userSiteServiceDeleteTestCase;


	@Before
	public void setup() {
		userSiteServiceSaveTestCase.initialize();
		userSiteServiceFindByIdTestCase.initialize();
		userSiteServiceDeleteTestCase.initialize();
	}

	@After
	public void teardown() {

	}

	/*============================*/
	// Insert User Site
	/*============================*/
	@Test
	public void testSaveUserSite() throws Exception {
		userSiteServiceSaveTestCase.setTestName("Validate User Site, Save Operation");
		assertTrue(userSiteServiceSaveTestCase.isTestPass());
	}


	/*============================*/
	// Find By User Site Id
	/*============================*/
	@Test
	public void testFindWithId() throws Exception {
		userSiteServiceFindByIdTestCase.setTestName("Validate User Site, Find by id with valid id");
		assertTrue(userSiteServiceFindByIdTestCase.isTestPass());
	}

	/*============================*/
	// Delete User Site
	/*============================*/
	@Test
	public void testDeleteSavedUserSite() throws Exception {
		userSiteServiceDeleteTestCase.setTestName("Validate User Site, Delete Operation");
		assertTrue(userSiteServiceDeleteTestCase.isTestPass());
	}





}
