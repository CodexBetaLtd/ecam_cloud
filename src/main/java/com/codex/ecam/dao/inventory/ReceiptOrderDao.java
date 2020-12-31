package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface ReceiptOrderDao extends FocusDataTableRepository<ReceiptOrder, Integer> {
	
    @Query("select ro from ReceiptOrder ro where ro.id=(select max(id) from ReceiptOrder where business.id=:businessId and year(createdDate)=year(sysdate()))")
    ReceiptOrder findLastDomainByBusiness(@Param("businessId") Integer businessId);

}
