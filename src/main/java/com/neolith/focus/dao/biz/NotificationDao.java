package com.neolith.focus.dao.biz;

import com.neolith.focus.model.biz.notification.Notification;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDao  extends FocusDataTableRepository<Notification, Integer>  {

	final static String FIND_INBOX_UNREAD_ITEM_COUNT="SELECT count(id) FROM tbl_notification WHERE recipient_id=:userId";

	@Query(value=FIND_INBOX_UNREAD_ITEM_COUNT,nativeQuery=true)
	Integer findInboxUnreadItemCount(@Param("userId") Integer userId);

}
