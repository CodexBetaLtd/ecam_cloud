package com.neolith.focus.service.notification.api;

import com.neolith.focus.constants.NotificationType;
import com.neolith.focus.dto.biz.notification.NotificationDTO;
import com.neolith.focus.dto.biz.notification.NotificationViewDTO;
import com.neolith.focus.model.biz.notification.Notification;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.notification.NotificationResult;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface NotificationService {

	NotificationResult newNotification();

	NotificationResult saveNotification(NotificationDTO dto);

	NotificationResult saveSystemNotification(NotificationDTO dto);

	NotificationResult toggleTrashedNotification(Boolean trashed, Integer notificationId) throws Exception;

	NotificationResult deleteNotification(Integer id);

    NotificationResult replyNotification(Integer id);

    NotificationResult forwardNotification(Integer id);

    NotificationResult findNotificationById(Integer id);

	NotificationViewDTO notificationUtility();

	NotificationResult fireInboxNotification(Notification notification);

    List<NotificationDTO> findAllNotification(NotificationType notificationType) throws Exception;

	DataTablesOutput<NotificationDTO> findAllNotification(FocusDataTablesInput dataTablesInput, NotificationType notificationType) throws Exception;

}
