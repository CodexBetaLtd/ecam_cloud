package com.neolith.focus.service.admin.notification.testCase;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.biz.notification.NotificationDTO;
import com.neolith.focus.model.biz.notification.Notification;
import com.neolith.focus.result.notification.NotificationResult;
import com.neolith.focus.service.admin.notification.NotificationData;
import com.neolith.focus.service.notification.api.NotificationService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by neo89 on 12/20/16.
 */
@Component
public class NotificationSaveTestCase extends TestCase {

    protected final String RESULT_IS_ERROR = "isError";
    protected NotificationDTO notificationDTO;
    protected NotificationResult resultDetail;
    protected boolean isError;

    @Autowired
    private NotificationService notificationService;

    @Override
    protected void checkActualResults() throws Exception {
        setActualResult(RESULT_IS_ERROR, isError);
    }


    protected void preCondition() {
        notificationDTO = NotificationData.getInstance().getDummyNotificationDTO();
        Assert.assertNotNull("Notification should not be NULL.", notificationDTO);
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
            testSaveOrUpdate(notificationDTO);
        } catch (Exception e) {
            isError = true;
        } finally {
            postCondition();
        }
    }


    protected void testSaveOrUpdate(NotificationDTO dto) throws Exception {
        resultDetail = notificationService.saveNotification(dto);
        Assert.assertEquals("Two Notification should be found", 2, resultDetail.getIntegerList().size());
    }

    protected void testDomainDataEqualsToDTOData(NotificationDTO dto, Notification domain) throws Exception {
        Assert.assertEquals("Subject Should Be Equal", domain.getSubject(), dto.getSubject());
        Assert.assertEquals("Content Should Be Equal", domain.getContent(), dto.getContent());

        Assert.assertEquals("Is Open Should Be Equal", domain.getIsOpen(), dto.getIsOpen());
        Assert.assertEquals("Is Popup Should Be Equal", domain.getIsPopup(), dto.getIsPopup());
        Assert.assertEquals("Is Trashed Should Be Equal", domain.getIsTrashed(), dto.getTrash());
    }




}
