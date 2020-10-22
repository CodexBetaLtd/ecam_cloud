package com.codex.ecam.service.notification.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.NotificationType;
import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.dto.biz.notification.NotificationViewDTO;
import com.codex.ecam.dto.biz.notification.server.NotificationServerDTO;
import com.codex.ecam.exception.setting.notification.NotificationException;
import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.notification.NotificationResult;

import java.util.List;

public interface NotificationService {

	NotificationResult newNotification();
	
	NotificationResult openMessage(Integer notificationId) throws NotificationException;

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
    DataTablesOutput<NotificationDTO> findAllInboxNotificationByUser(FocusDataTablesInput dataTablesInput) throws Exception;
	DataTablesOutput<NotificationDTO> findAllSystemInboxNotificationByUser(FocusDataTablesInput dataTablesInput) throws Exception;
	List<NotificationServerDTO> findAllListInboxNotificationByUser() throws Exception;
	List<NotificationServerDTO> findAllListSystemNotificationByUser() throws Exception;


}
