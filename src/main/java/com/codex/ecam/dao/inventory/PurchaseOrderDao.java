package com.codex.ecam.dao.inventory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderFile;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface PurchaseOrderDao extends FocusDataTableRepository<PurchaseOrder, Integer> {

	@Query("from PurchaseOrderFile where id = :id")
	PurchaseOrderFile findByFileId(@Param("id") Integer id);
	
	@Query("from PurchaseOrderItem where asset.id = :partId and purchaseOrder.receivedDate > :date")
	List<PurchaseOrderItem> findItemOrderBeforeDate(@Param("date") Date purcahseDate,@Param("partId") Integer partId);

}
