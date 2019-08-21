package com.neolith.focus.security;

import com.neolith.focus.config.SpringConfiguration;
import com.neolith.focus.config.SpringSecurityConfiguration;
import com.neolith.focus.config.TestSpringConfiguration;
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
// @Transactional // enable if you're updating database during testing
public class UserDetailServiceTest {

	@Autowired
	private UserDetailServiceLoginTestCase userDetailServiceLoginTestCase;

	@Before
	public void setup() {
		userDetailServiceLoginTestCase.initialize();
	}

	@After
	public void teardown() {
	}

	@Test
	public void testLoginWithValidAccount() throws Exception {
		userDetailServiceLoginTestCase.setTestName("Validate Login with valid credentials");
		userDetailServiceLoginTestCase.setTestCondition(userDetailServiceLoginTestCase.CONDITION_USERNAME, "admin");
		userDetailServiceLoginTestCase.setTestCondition(userDetailServiceLoginTestCase.CONDITION_PASSWORD, "admin123");
		userDetailServiceLoginTestCase.setExpectedResult(userDetailServiceLoginTestCase.RESULT_IS_ERROR, false);
		assertTrue(userDetailServiceLoginTestCase.isTestPass());
	}

	@Test
	public void testLoginWithEmptyCredentials() throws Exception {
		userDetailServiceLoginTestCase.setTestName("Validate Login with empty credentials");
		userDetailServiceLoginTestCase.setTestCondition(userDetailServiceLoginTestCase.CONDITION_USERNAME, "");
		userDetailServiceLoginTestCase.setTestCondition(userDetailServiceLoginTestCase.CONDITION_PASSWORD, "");
		userDetailServiceLoginTestCase.setExpectedResult(userDetailServiceLoginTestCase.RESULT_IS_ERROR, true);
		assertTrue(userDetailServiceLoginTestCase.isTestPass());
	}

	@Test
	public void testLoginWithInvalidCredentials() throws Exception {
		userDetailServiceLoginTestCase.setTestName("Validate Login with invalid credentials");
		userDetailServiceLoginTestCase.setTestCondition(userDetailServiceLoginTestCase.CONDITION_USERNAME, "someuser");
		userDetailServiceLoginTestCase.setTestCondition(userDetailServiceLoginTestCase.CONDITION_PASSWORD, "somepassword");
		userDetailServiceLoginTestCase.setExpectedResult(userDetailServiceLoginTestCase.RESULT_IS_ERROR, true);
		assertTrue(userDetailServiceLoginTestCase.isTestPass());
	}
}
