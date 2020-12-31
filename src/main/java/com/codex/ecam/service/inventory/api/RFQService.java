package com.codex.ecam.service.inventory.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.dto.inventory.rfq.RFQDTO;
import com.codex.ecam.dto.inventory.rfq.RFQRepDTO;
import com.codex.ecam.model.inventory.rfq.RFQ;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.MRNResult;
import com.codex.ecam.result.purchasing.RFQResult;

public interface RFQService {

	RFQDTO findById(Integer id) throws Exception;
	
	RFQRepDTO findRFQRepDTOById(Integer id) throws Exception;
	
	RFQResult statusChange(Integer id,RFQStatus rfqStatus);
	
	RFQResult createNewRFQ();
	
	RFQDTO createRFQFromPoItems(String poItemIds); 

	RFQResult save(RFQDTO rfq);

	RFQResult delete(Integer id);

	DataTablesOutput<RFQDTO> findAll(FocusDataTablesInput dataTablesInput) throws Exception;

    PurchaseOrderDTO saveWithPurchaseOrder(RFQDTO rfq);
    
	String rfqFileUpload(MultipartFile file,String refId) throws Exception;

	void rfqFileDownload(Integer id,HttpServletResponse response) throws Exception;
	void rfqFileDelete(Integer id) throws Exception;
	
    MRNResult generateRFQFromMrn(String ids, Integer mrnId);
    
    String getNextCode(Integer businessId);


}
