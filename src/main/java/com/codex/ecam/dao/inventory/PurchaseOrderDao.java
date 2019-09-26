package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderFile;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface PurchaseOrderDao extends FocusDataTableRepository<PurchaseOrder, Integer> {

	@Query("from PurchaseOrderFile where id = :id")
	PurchaseOrderFile findByFileId(@Param("id") Integer id);

}
