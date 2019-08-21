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

/**
 * Created by neo89 on 12/20/16.
 */
@Component
public class NotificationUndoTrashTestCase extends TestCase {

    protected final String RESULT_IS_ERROR = "isError";
    protected NotificationDTO notificationDTO;
    protected NotificationResult resultDetail;
    protected boolean isError;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationDao notificationDao;

    @Override
    protected void checkActualResults() throws Exception {
        setActualResult(RESULT_IS_ERROR, isError);
    }


    protected void preCondition() throws Exception {
        notificationDTO = NotificationData.getInstance().getDummyNotificationDTO();
        notificationDTO.setTrash(Boolean.TRUE);
        Assert.assertNotNull("Notification DTO should not be NULL.", notificationDTO);
        Assert.assertTrue("Notification DTO Trash should not be True.", notificationDTO.getTrash());
        resultDetail = notificationService.saveNotification(notificationDTO);
    }

    protected void postCondition() throws Exception {
        List<Integer> integers = resultDetail.getIntegerList();
        for (Integer id : integers) {
            notificationService.deleteNotification(id);
        }
    }


    @Override
    protected void executeTest() throws Exception {
        try {
            preCondition();
            testNotificationUndoTrash(resultDetail.getIntegerList().get(0));
            postCondition();
        } catch (Exception e) {
            isError = true;
        }
    }

    protected void testNotificationUndoTrash(Integer id) throws Exception {
        Notification notification = notificationDao.findOne(id);
        Assert.assertEquals("Notification should not be NULL.", Boolean.TRUE, notification.getIsTrashed());
        notificationService.toggleTrashedNotification(Boolean.FALSE, id);
        testNotificationUndoTrashItem(id);
    }

    protected void testNotificationUndoTrashItem(Integer id) throws Exception {
        Notification notification = notificationDao.findOne(id);
        Assert.assertNotNull("Notification should not be NULL.", notification);
        Assert.assertFalse("Notification Trash should be False.", notification.getIsTrashed());
        Assert.assertEquals("Notification Trash should NOT be False.", Boolean.FALSE, notification.getIsTrashed());
    }


}
