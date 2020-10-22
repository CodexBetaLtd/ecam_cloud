package com.codex.ecam.dao.biz;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface NotificationDao extends FocusDataTableRepository<Notification, Integer> {

	final static String FIND_INBOX_UNREAD_ITEM_COUNT = "SELECT count(id) FROM tbl_notification WHERE recipient_id=:userId";

	@Query(value = FIND_INBOX_UNREAD_ITEM_COUNT, nativeQuery = true)
	Integer findInboxUnreadItemCount(@Param("userId") Integer userId);

	@Query("SELECT nof FROM Notification nof JOIN nof.recipients nofr where nofr.recipient.id=:userId and nofr.isSystemMessage!=True")
	List<Notification> findAllInboxByUser(@Param("userId") Integer userId);
	
	@Query("SELECT nof FROM Notification nof JOIN nof.recipients nofr where nofr.recipient.id=:userId and nofr.isOpen=False and nofr.isSystemMessage!=True")
	List<Notification> findAllNotOpenInboxByUser(@Param("userId") Integer userId);
	

	@Query("SELECT nof FROM Notification nof JOIN nof.recipients nofr where nofr.recipient.id=:userId and nofr.isSystemMessage=True")
	List<Notification> findAllInboxSystemByUser(@Param("userId") Integer userId);
	
	@Query("SELECT nof FROM Notification nof JOIN nof.recipients nofr where nofr.recipient.id=:userId and nofr.isOpen=False and nofr.isSystemMessage=True")
	List<Notification> findAllNotInboxSystemByUser(@Param("userId") Integer userId);

	@Transactional
	@Modifying
	@Query("UPDATE NotificationRecipientUser SET isOpen=TRUE WHERE recipient.id=:userId and notification.id=:id")
	void updateOpenStatusNotification(@Param("userId") Integer userId, @Param("id") Integer id);

}
