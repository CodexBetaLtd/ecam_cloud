package com.neolith.focus.service.inventory.api;

import com.neolith.focus.constants.PurchaseOrderStatus;
import com.neolith.focus.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.purchasing.PurchaseOrderResult; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

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
