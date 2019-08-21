package com.neolith.focus.service.admin.notification;

import com.neolith.focus.config.SpringConfiguration;
import com.neolith.focus.config.SpringSecurityConfiguration;
import com.neolith.focus.config.TestSpringConfiguration;
import com.neolith.focus.service.admin.notification.testCase.*;
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
public class NotificationServiceTest {

	@Autowired
	private NotificationSaveTestCase notificationSaveTestCase;
	@Autowired
	private NotificationServiceFindByIdTestCase notificationServiceFindByIdTestCase;
	@Autowired
	private NotificationServiceDeleteByIdTestCase notificationServiceDeleteByIdTestCase;
	@Autowired
	private NotificationTrashTestCase notificationTrashTestCase;
	@Autowired
	private NotificationUndoTrashTestCase notificationUndoTrashTestCase;
	@Autowired
	private NotificationTypeChangeTestCase notificationTypeChangeTestCase;



	@Before
	public void setup() {
		notificationSaveTestCase.initialize();
		notificationServiceFindByIdTestCase.initialize();
		notificationServiceDeleteByIdTestCase.initialize();
		notificationTrashTestCase.initialize();
		notificationUndoTrashTestCase.initialize();
		notificationTypeChangeTestCase.initialize();
	}


	@After
	public void teardown() {

	}




	/*============================*/
	// Insert
	/*============================*/
	@Test
	public void testSaveNotification() throws Exception {
		notificationSaveTestCase.setTestName("Validate Notification Save Operation");
		assertTrue(notificationSaveTestCase.isTestPass());
	}
	@Test
	public void testSaveNotificationChangeType() throws Exception {
		notificationTypeChangeTestCase.setTestName("Validate Notification, Save Inbox, Outbox Operation");
		assertTrue(notificationTypeChangeTestCase.isTestPass());
	}



	/*============================*/
	// Find By Id
	/*============================*/
	@Test
	public void testFindWithValidId() throws Exception {
		notificationServiceFindByIdTestCase.setTestName("Validate Notification Find by id with valid id");
		assertTrue(notificationServiceFindByIdTestCase.isTestPass());
	}


	/*============================*/
	// Delete Notification
	/*============================*/
	@Test
	public void testDeleteSavedNotification() throws Exception {
		notificationServiceDeleteByIdTestCase.setTestName("Validate Notification Delete Operation");
		assertTrue(notificationServiceDeleteByIdTestCase.isTestPass());
	}


	/*============================*/
	// Move To Trash
	/*============================*/
	@Test
	public void testNotificationTrash() throws Exception {
		notificationTrashTestCase.setTestName("Validate Notification Trash Operation");
		assertTrue(notificationTrashTestCase.isTestPass());
	}

	/*============================*/
	// Undo Trash
	/*============================*/
	@Test
	public void testNotificationUndoTrash() throws Exception {
		notificationUndoTrashTestCase.setTestName("Validate Notification Trash Operation");
		assertTrue(notificationUndoTrashTestCase.isTestPass());
	}





}
