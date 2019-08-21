package com.neolith.focus.service.inventory.api;

import com.neolith.focus.constants.inventory.ReceiptOrderStatus;
import com.neolith.focus.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.purchasing.ReceiptOrderResult; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface ReceiptOrderService {

	ReceiptOrderResult findById(Integer id) throws Exception;

	ReceiptOrderResult update(ReceiptOrderDTO receiptOrder);

	ReceiptOrderResult save(ReceiptOrderDTO receiptOrder);

	ReceiptOrderResult delete(Integer id);

	ReceiptOrderResult statusChange(Integer id, ReceiptOrderStatus receiptOrderStatus);

	DataTablesOutput<ReceiptOrderDTO> findAll(FocusDataTablesInput input) throws Exception;

}
