package com.neolith.focus.service.inventory.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.multipart.MultipartFile;

import com.neolith.focus.constants.inventory.RFQStatus;
import com.neolith.focus.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.neolith.focus.dto.inventory.rfq.RFQDTO;
import com.neolith.focus.dto.inventory.rfq.RFQRepDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.purchasing.RFQResult;

public interface RFQService {

	RFQDTO findById(Integer id) throws Exception;
	
	RFQRepDTO findRFQRepDTOById(Integer id) throws Exception;
	
	RFQResult statusChange(Integer id,RFQStatus rfqStatus);
	
	RFQDTO createRFQFromPoItems(String poItemIds); 

	RFQResult save(RFQDTO rfq);

	RFQResult delete(Integer id);

	DataTablesOutput<RFQDTO> findAll(FocusDataTablesInput dataTablesInput) throws Exception;

    PurchaseOrderDTO saveWithPurchaseOrder(RFQDTO rfq);
    
	String rfqFileUpload(MultipartFile file,String refId) throws Exception;

	void rfqFileDownload(Integer id,HttpServletResponse response) throws Exception;

}
