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
public class NotificationServiceDeleteByIdTestCase extends TestCase {

	protected final String RESULT_IS_ERROR = "isError";
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private NotificationDao notificationDao;
	private boolean isError;

    @Override
    protected void checkActualResults() throws Exception {
        setActualResult(RESULT_IS_ERROR, isError);
    }


    @Override
    protected void executeTest() throws Exception {
        try {
            //Depend On the Save Operation
            NotificationDTO notificationDTO = NotificationData.getInstance().getDummyNotificationDTO();
            NotificationResult resultDetail = notificationService.saveNotification(notificationDTO);

            List<Integer> idList = resultDetail.getIntegerList();
            for (Integer id : idList) {
                notificationService.deleteNotification(id);
            }
            for (Integer id : idList) {
                Notification notification = notificationDao.findOne(id);
                testIsDeleteSuccess(notification);
            }

        } catch (Exception e) {
            isError = true;
        }
    }


	protected void testIsDeleteSuccess(Notification notification){
		Assert.assertNull("Notification Should Be Null or Empty", notification);
	}



}
