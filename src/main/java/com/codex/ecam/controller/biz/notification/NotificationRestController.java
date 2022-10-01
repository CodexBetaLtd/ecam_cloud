package com.codex.ecam.controller.biz.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.constants.NotificationType;
import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.notification.api.NotificationService;

import javax.validation.Valid;

@RestController
@RequestMapping(NotificationRestController.REQUEST_MAPPING_URL)
public class NotificationRestController {

	public static final String REQUEST_MAPPING_URL = "/restapi/notification";

	@Autowired
	private NotificationService notificationService;


	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public DataTablesOutput<NotificationDTO> inbox(@Valid FocusDataTablesInput dataTablesInput) {
		try {
			return notificationService.findAllNotification(dataTablesInput, NotificationType.INBOX_NOTIFICATION);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<NotificationDTO>();
	}

	@RequestMapping(value = "/outbox", method = RequestMethod.GET)
	public DataTablesOutput<NotificationDTO> outbox(@Valid FocusDataTablesInput dataTablesInput) {
		try {
			return notificationService.findAllNotification(dataTablesInput, NotificationType.OUTBOX_NOTIFICATION);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<NotificationDTO>();
	}

	@RequestMapping(value = "/trash", method = RequestMethod.GET)
	public DataTablesOutput<NotificationDTO> trash(@Valid FocusDataTablesInput dataTablesInput) {
		try {
			return notificationService.findAllNotification(dataTablesInput, NotificationType.TRASH_NOTIFICATION);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<NotificationDTO>();
	}

	@RequestMapping(value = "/inbox-unread-count", method = RequestMethod.GET)
	public Integer inboxCount() {
		try {
			return notificationService.getInboxUnreadCount();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


}
