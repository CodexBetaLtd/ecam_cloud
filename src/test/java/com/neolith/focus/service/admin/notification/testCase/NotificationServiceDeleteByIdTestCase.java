package com.neolith.focus.service.admin.notification.testCase;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dao.biz.NotificationDao;
import com.neolith.focus.dto.biz.notification.NotificationDTO;
import com.neolith.focus.model.biz.notification.Notification;
import com.neolith.focus.result.notification.NotificationResult;
import com.neolith.focus.service.admin.notification.NotificationData;
import com.neolith.focus.service.notification.api.NotificationService;

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
