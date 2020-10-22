package com.codex.ecam.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.codex.ecam.dto.biz.notification.server.NotificationServerDTO;
import com.codex.ecam.dto.biz.notification.server.NotificationServerType;
import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.model.biz.notification.NotificationRecipientUser;
import com.codex.ecam.result.notification.NotificationResult;

import lombok.var;
@Component
@Scope(scopeName=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class KafkaUtil {

	static ResponseEntity<String> result;

	private static String notificationUrl = "/notification/sendnotification";
	
	private static String messageUrl = "/notification/sendmessage";
	
	private	Environment environment; 
	

	private static KafkaUtil instance = null;

	private  KafkaUtil(Environment environment) {
		this.environment=environment;
	}

	public static KafkaUtil getInstance(Environment environment) {
		if (instance == null) {
			instance = new KafkaUtil(environment);
		}
		return instance;
	}


	public ResponseEntity<String> sendKafakaMessage(NotificationResult resultIn,NotificationRecipientUser recipientUser) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri;
		try {
			uri = new URI(getMessageRequestUrl());
			Notification notification = resultIn.getDomainEntity();
			NotificationServerDTO notificationServerDTO = new NotificationServerDTO();
			notificationServerDTO.setId(notification.getId().longValue());
			notificationServerDTO.setNotifyTime(new Date());
			notificationServerDTO.setUserId(recipientUser.getRecipient().getId());
			notificationServerDTO.setUserName(recipientUser.getRecipient().getUserCredential().getUserName());
			notificationServerDTO.setContent(notification.getContent());
			notificationServerDTO.setSubject(notification.getSubject());
			notificationServerDTO.setType(NotificationServerType.NONE);
			notificationServerDTO.setSendUser(notification.getSender().getUserCredential().getUserName());
			notificationServerDTO.setIsOpened(Boolean.FALSE);
			notificationServerDTO.setIsPopup(Boolean.TRUE);
			result = restTemplate.postForEntity(uri, notificationServerDTO, String.class);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return result;
	}

	public  ResponseEntity<String> sendKafakaNotification(NotificationResult resultIn,NotificationRecipientUser recipientUser) {
		var requestFactory = new HttpComponentsClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		URI uri;
		try {
			uri = new URI(getNotificationRequestUrl());
			Notification notification = resultIn.getDomainEntity();
			NotificationServerDTO notificationServerDTO = new NotificationServerDTO();
			notificationServerDTO.setId(notification.getId().longValue());
			notificationServerDTO.setNotifyTime(new Date());
			notificationServerDTO.setUserId(recipientUser.getRecipient().getId());
			notificationServerDTO.setUserName(recipientUser.getRecipient().getUserCredential().getUserName());
			notificationServerDTO.setContent(notification.getContent());
			notificationServerDTO.setSubject(notification.getSubject());
			notificationServerDTO.setType(NotificationServerType.NONE);
			notificationServerDTO.setSendUser(notification.getSender().getUserCredential().getUserName());
			notificationServerDTO.setIsOpened(Boolean.FALSE);
			notificationServerDTO.setIsPopup(Boolean.TRUE);

			result = restTemplate.postForEntity(uri, notificationServerDTO, String.class);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return result;
	}

	private  String getNotificationRequestUrl() {
		return environment.getProperty("kafka.server.url").concat(notificationUrl);
	}

	private String getMessageRequestUrl() {
		return environment.getProperty("kafka.server.url").concat(messageUrl);
	}





}
