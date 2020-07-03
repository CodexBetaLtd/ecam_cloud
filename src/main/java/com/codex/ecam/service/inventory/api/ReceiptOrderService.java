package com.codex.ecam.service.inventory.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.MRNResult;
import com.codex.ecam.result.purchasing.RFQResult;
import com.codex.ecam.result.purchasing.ReceiptOrderResult;

public interface ReceiptOrderService {

	ReceiptOrderResult findById(Integer id) throws Exception;

	ReceiptOrderResult update(ReceiptOrderDTO receiptOrder);

	ReceiptOrderResult save(ReceiptOrderDTO receiptOrder);

	ReceiptOrderResult delete(Integer id);

	ReceiptOrderResult statusChange(Integer id, ReceiptOrderStatus receiptOrderStatus);

	DataTablesOutput<ReceiptOrderDTO> findAll(FocusDataTablesInput input) throws Exception;
	
	ReceiptOrderResult generateGrnFromPo(String ids, Integer poId);
	
    String getNextCode(Integer businessId);

    ReceiptOrderResult createNewReceiptOrder();

}
