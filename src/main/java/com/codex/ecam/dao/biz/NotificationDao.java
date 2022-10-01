package com.codex.ecam.dao.biz;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface NotificationDao  extends FocusDataTableRepository<Notification, Integer>  {

	final static String FIND_INBOX_UNREAD_ITEM_COUNT="SELECT count(id) FROM tbl_notification WHERE recipient_id=:userId";

	@Query(value=FIND_INBOX_UNREAD_ITEM_COUNT,nativeQuery=true)
	Integer findInboxUnreadItemCount(@Param("userId") Integer userId);

}
