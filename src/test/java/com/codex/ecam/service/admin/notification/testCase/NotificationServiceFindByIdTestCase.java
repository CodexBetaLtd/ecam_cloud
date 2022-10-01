package com.codex.ecam.service.admin.notification.testCase;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dao.biz.NotificationDao;
import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.result.notification.NotificationResult;
import com.codex.ecam.service.admin.notification.NotificationData;
import com.codex.ecam.service.notification.api.NotificationService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationServiceFindByIdTestCase extends TestCase {

	protected final String RESULT_IS_ERROR = "isError";
	protected Notification notification = new Notification();
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private NotificationDao notificationDao;
	private boolean isError;

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	protected void preCondition(){
		/*notification = notificationDao.save(NotificationData.getInstance().getDummyNotification());
		Assert.assertNotNull("Pre Condition(Saved Notification) Should Be Satisfied.", notification);
		Assert.assertNotNull("Pre Condition(Saved Notification Id) Should Be Satisfied.", notification.getId());*/
	}
	protected void postCondition(){
		notificationDao.delete(notification.getId());
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			//Depend On the Save Operation
			testFindNotificationWithValidId();
			//			testFindNotificationWithInvalidId();
			//			testFindNotificationWithNullValue();
		} catch (Exception e) {
			isError = true;
		}
	}


	protected void testFetchNotificationIsValid(Notification notification,NotificationDTO notificationDTO) throws Exception{
		Assert.assertNotNull("Notification Should Be Null or Empty for Invalid ID", notification);
		//Test Other Fields
		Assert.assertEquals("Notification Subject Should Be Equal", notification.getSubject(),notificationDTO.getSubject());
		Assert.assertEquals("Notification Content Should Be Equal", notification.getContent(),notificationDTO.getContent());
	}




	protected void testFindNotificationWithValidId() throws Exception{
		NotificationDTO notificationDTO = NotificationData.getInstance().getDummyNotificationDTO();
		NotificationResult resultDetail = notificationService.saveNotification(notificationDTO);
		List<Integer> idList = resultDetail.getIntegerList();
		for(Integer id: idList){
			Notification notification = notificationDao.findOne(id);
			testFetchNotificationIsValid(notification,notificationDTO);
			notificationDao.delete(id);
		}
	}

	protected void testFindNotificationWithInvalidId() throws Exception {
		NotificationResult result = notificationService.findNotificationById(Integer.parseInt("invalidId"));
		Assert.assertNull("Notification Should Be Null or Empty for Invalid ID", result.getDtoEntity());
	}
	protected void testFindNotificationWithNullValue() throws Exception{
		NotificationResult result = notificationService.findNotificationById(null);
		Assert.assertNull("Notification Should Be Null or Empty", result.getDtoEntity());
	}


}
