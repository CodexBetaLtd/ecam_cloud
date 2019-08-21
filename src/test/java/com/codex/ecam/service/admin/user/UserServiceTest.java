package com.codex.ecam.service.admin.user;

import com.codex.ecam.config.SpringConfiguration;
import com.codex.ecam.config.SpringSecurityConfiguration;
import com.codex.ecam.config.TestSpringConfiguration;

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
public class UserServiceTest {



	@Autowired
	private UserServiceSaveTestCase userServiceSaveTestCase;
	@Autowired
	private UserServiceFindByIdTestCase userServiceFindByIdTestCase;
	@Autowired
	private UserServiceFindAllTestCase userServiceFindAllTestCase;
	@Autowired
	private UserServiceDeleteTestCase userServiceDeleteTestCase;
	@Autowired
	private UserServiceUpdateTestCase userServiceUpdateTestCase;






	@Before
	public void setup() {
		userServiceSaveTestCase.initialize();
		userServiceFindByIdTestCase.initialize();
		//userServiceFindTableDataTestCase.initialize();
		userServiceFindAllTestCase.initialize();
		userServiceDeleteTestCase.initialize();
		userServiceUpdateTestCase.initialize();
	}

	@After
	public void teardown() {

	}

	/*============================*/
	// Find All
	/*============================*/
/*	@Test
	public void testFindAll() throws Exception {
		userServiceFindAllTestCase.setTestName("Validate User Find All");
		userServiceFindAllTestCase.setTestCondition(userServiceFindAllTestCase.CONDITION_EXPECTED_COUNT, 2);
		userServiceFindAllTestCase.setTestCondition(userServiceFindAllTestCase.CONDITION_EXPECTED_FIRST_ID, 1);
		userServiceFindAllTestCase.setTestCondition(userServiceFindAllTestCase.CONDITION_EXPECTED_FIRST_NAME, "Admin");
		userServiceFindAllTestCase.setTestCondition(userServiceFindAllTestCase.CONDITION_EXPECTED_SECOND_ID, 2);
		userServiceFindAllTestCase.setTestCondition(userServiceFindAllTestCase.CONDITION_EXPECTED_SECOND_NAME, "HR Manager");
		userServiceFindAllTestCase.setExpectedResult(userServiceFindAllTestCase.RESULT_IS_ERROR, false);
		assertTrue(userServiceFindAllTestCase.isTestPass());
	}*/

/*	@Test
	public void testFindTableDatal() throws Exception {
		userServiceFindTableDataTestCase.setTestName("Validate User Find Table Data");
		userServiceFindTableDataTestCase.setTestCondition(userServiceFindTableDataTestCase.CONDITION_EXPECTED_COUNT, 2);
		userServiceFindTableDataTestCase.setTestCondition(userServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_ID, 1);
		userServiceFindTableDataTestCase.setTestCondition(userServiceFindTableDataTestCase.CONDITION_EXPECTED_FIRST_NAME, "Admin");
		userServiceFindTableDataTestCase.setTestCondition(userServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_ID, 2);
		userServiceFindTableDataTestCase.setTestCondition(userServiceFindTableDataTestCase.CONDITION_EXPECTED_SECOND_NAME, "HR Manager");
		userServiceFindTableDataTestCase.setExpectedResult(userServiceFindTableDataTestCase.RESULT_IS_ERROR, false);
		assertTrue(userServiceFindTableDataTestCase.isTestPass());
	}*/





	/*============================*/
	// Insert User
	/*============================*/
	@Test
	public void testSaveUser() throws Exception {
		userServiceSaveTestCase.setTestName("Validate User Save Operation");
		userServiceSaveTestCase.setExpectedResult(userServiceSaveTestCase.RESULT_IS_ERROR, false);
		assertTrue(userServiceSaveTestCase.isTestPass());
	}







	/*============================*/
	// Find By Id
	/*============================*/

	@Test
	public void testFindWithValidId() throws Exception {
		userServiceFindByIdTestCase.setTestName("Validate User Find by id with valid id");
		userServiceFindByIdTestCase.setExpectedResult(userServiceFindByIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(userServiceFindByIdTestCase.isTestPass());
	}
	@Test
	public void testFindWithInValidId() throws Exception {
		userServiceFindByIdTestCase.setTestName("Validate User, Find With Invalid Id");
		userServiceFindByIdTestCase.setTestCondition(userServiceFindByIdTestCase.CONDITION_ID, 9999999);
		userServiceFindByIdTestCase.setExpectedResult(userServiceFindByIdTestCase.RESULT_IS_ERROR, true);
		assertTrue(userServiceFindByIdTestCase.isTestPass());
	}




	/*============================*/
	// Update User
	/*============================*/
	@Test
	public void testUpdateSavedUser() throws Exception {
		userServiceUpdateTestCase.setTestName("Validate User Update Operation");
		userServiceUpdateTestCase.setExpectedResult(userServiceSaveTestCase.RESULT_IS_ERROR, false);
		assertTrue(userServiceUpdateTestCase.isTestPass());
	}









	/*============================*/
	// Delete User
	/*============================*/
	@Test
	public void testDeleteSavedUser() throws Exception {
		userServiceDeleteTestCase.setTestName("Validate User Delete Operation");
		userServiceDeleteTestCase.setExpectedResult(userServiceSaveTestCase.RESULT_IS_ERROR, true);
		assertTrue(userServiceDeleteTestCase.isTestPass());
	}

















}
