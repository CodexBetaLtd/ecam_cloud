package com.neolith.focus.dao.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neolith.focus.constants.inventory.RFQStatus;
import com.neolith.focus.model.inventory.rfq.RFQ;
import com.neolith.focus.model.inventory.rfq.RFQFile;
import com.neolith.focus.model.inventory.rfq.RFQNotification;
import com.neolith.focus.repository.FocusDataTableRepository;

@Repository
public interface RFQDao extends FocusDataTableRepository<RFQ, Integer> {

	@Query("Update RFQ set rfqStatus=:status where  id = :rfqId")
	RFQ updateStatus(@Param("status") RFQStatus rfqStatus,@Param("rfqId") Integer id);
	
	@Query("from RFQFile where id = :id")
	RFQFile findByFileId(@Param("id") Integer id);
	
    @Query("from RFQNotification where rfq.id = :rfqId")
    List<RFQNotification> findByNotificationById(@Param("rfqId") Integer id);

}
