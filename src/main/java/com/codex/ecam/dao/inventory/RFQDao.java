package com.codex.ecam.dao.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.model.inventory.rfq.RFQ;
import com.codex.ecam.model.inventory.rfq.RFQFile;
import com.codex.ecam.model.inventory.rfq.RFQNotification;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface RFQDao extends FocusDataTableRepository<RFQ, Integer> {

	@Query("Update RFQ set rfqStatus=:status where  id = :rfqId")
	RFQ updateStatus(@Param("status") RFQStatus rfqStatus,@Param("rfqId") Integer id);
	
	@Query("from RFQFile where id = :id")
	RFQFile findByFileId(@Param("id") Integer id);
	

    @Query("select rfq from RFQ rfq where rfq.id=(select max(id) from RFQ where business.id=:businessId and year(createdDate)=year(sysdate()))")
	RFQ findLastDomainByBusiness(@Param("businessId") Integer businessId);
	
    @Query("from RFQNotification where rfq.id = :rfqId")
    List<RFQNotification> findByNotificationById(@Param("rfqId") Integer id);

}
