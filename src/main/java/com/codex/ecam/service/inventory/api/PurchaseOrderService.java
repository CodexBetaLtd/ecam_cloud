package com.codex.ecam.service.inventory.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.purchasing.PurchaseOrderResult;

import java.util.List;

public interface PurchaseOrderService {

	DataTablesOutput<PurchaseOrderDTO> findAll(FocusDataTablesInput dataTablesInput) throws Exception;

	PurchaseOrderDTO findById(Integer id) throws Exception;
	
	PurchaseOrderDTO statusChange(Integer id,PurchaseOrderStatus status);
	
	PurchaseOrderDTO createPurchaseOrderFromRFQItems(String rfqItemIds);
	
	PurchaseOrderDTO createPurchaseOrderFromRFQItems(List<Integer> rfqItemIds);

	PurchaseOrderResult update(PurchaseOrderDTO purchaseOrder);

	PurchaseOrderResult save(PurchaseOrderDTO purchaseOrder);

	PurchaseOrderResult delete(Integer id);

}
