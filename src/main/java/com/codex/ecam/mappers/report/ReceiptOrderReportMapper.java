package com.codex.ecam.mappers.report;

import com.codex.ecam.dto.report.data.ReceiptOrderRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;


public class ReceiptOrderReportMapper extends GenericReportMapper<ReceiptOrder, ReceiptOrderRepDTO> {

	private static ReceiptOrderReportMapper instance = null;

	private ReceiptOrderReportMapper() {
	}

	public static ReceiptOrderReportMapper getInstance() {
		if (instance == null) {
			instance = new ReceiptOrderReportMapper();
		}
		return instance;
	}

	@Override
	public ReceiptOrderRepDTO domainToRepDTO(ReceiptOrder domain) throws Exception {
		ReceiptOrderRepDTO repDTO = new ReceiptOrderRepDTO();
		repDTO.setCode(domain.getCode());
		repDTO.setDateOrdered(domain.getDateOrdered());
		repDTO.setDateReceived(domain.getDateReceived());
		if ((domain.getPurchaseOrder() != null) && (domain.getPurchaseOrder().getCode() != null)) {

		repDTO.setOrderNo(domain.getPurchaseOrder().getCode());
		}
		if ((domain.getSupplier() != null) && (domain.getSupplier().getCode() != null)) {

		repDTO.setSupplierName(domain.getSupplier().getCode());
		}
	
		repDTO.setStatus(domain.getReceiptOrderStatus().getName());
		if ((domain.getCreatedUser() != null) && (domain.getCreatedUser().getId() != null)) {
			repDTO.setCreatedUser(domain.getCreatedUser().getFullName());
		}



		repDTO.setStatus(domain.getReceiptOrderStatus().getName());
		return repDTO;
	}



}
