package com.codex.ecam.mappers.purchasing;

import com.codex.ecam.dto.inventory.rfq.RFQDTO;
import com.codex.ecam.dto.inventory.rfq.RFQRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.inventory.rfq.RFQ;
import com.codex.ecam.model.inventory.rfq.RFQItem;

public class RFQReportMapper extends GenericReportMapper<RFQ, RFQRepDTO> {

	private static RFQReportMapper instance = null;

	private RFQReportMapper() {
	}

	public static RFQReportMapper getInstance() {
		if (instance == null) {
			instance = new RFQReportMapper();
		}
		return instance;
	}

	public RFQRepDTO domainToDto(RFQ domain) throws Exception {
		RFQRepDTO dto = new RFQRepDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setRfqStatus(domain.getRfqStatus());
		dto.setStatusName(domain.getRfqStatus().getName());
		
		dto.setExpectedDeliveryDate(domain.getDateExpectedDelivery());
		dto.setRequiredResponseDate(domain.getDateRequiredResponse());
		dto.setSentDate(domain.getDateSent());
		dto.setMessageSubject(domain.getMessageSubject());
		dto.setMessageContent(domain.getMessageContent());
		dto.setMessageSubject(domain.getMessageSubject());
		dto.setQuoteReferenceNumber(domain.getQuoteReferenceNumber());				
		
		dto.setSupplierAddress(domain.getSupplierAddress());		
		dto.setSupplierCity(domain.getSupplierCity());
		dto.setSupplierPostalCode(domain.getSupplierPostalCode());
		dto.setSupplierProvince(domain.getSupplierProvince());		
		
		dto.setShipToAddress(domain.getShippingAddress());
		dto.setShippingCity(domain.getSupplierCity());
		dto.setShippingPostalCode(domain.getShippingPostalCode());
		dto.setShippingProvince(domain.getSupplierProvince());
		
		if ( domain.getSupplier() != null) {
			dto.setSupplierId(domain.getSupplier().getId());
			dto.setSupplierName(domain.getSupplier().getName());
		}
		if ( domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
		}
		
		if (domain.getShipTo() != null) {
			dto.setShipToId(domain.getShipTo().getId());
		}
		
		if ( domain.getShippingCountry() != null ) {
			dto.setShipToCountry(domain.getShippingCountry().getId());
		}	
		
		if ( domain.getSupplierCountry() != null ) {
			dto.setSupplierCountry(domain.getSupplierCountry().getId());
		}
		
		setRFQItems(domain, dto);
		
		return dto;
	}
	public RFQRepDTO repDTOToDto(RFQDTO dto) throws Exception {
		RFQRepDTO repDto = new RFQRepDTO();
		repDto.setId(dto.getId());
		repDto.setCode(dto.getCode());
		repDto.setRfqStatus(dto.getRfqStatus());
		repDto.setStatusName(dto.getRfqStatus().getName());

		repDto.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
		repDto.setRequiredResponseDate(dto.getRequiredResponseDate());
		repDto.setSentDate(dto.getSentDate());
		repDto.setMessageSubject(dto.getMessageSubject());
		repDto.setMessageContent(dto.getMessageContent());
		repDto.setMessageSubject(dto.getMessageSubject());
		repDto.setQuoteReferenceNumber(dto.getQuoteReferenceNumber());				
		
		repDto.setSupplierAddress(dto.getSupplierAddress());		
		repDto.setSupplierCity(dto.getSupplierCity());
		repDto.setSupplierPostalCode(dto.getSupplierPostalCode());
		repDto.setSupplierProvince(dto.getSupplierProvince());		
		
		repDto.setShipToAddress(dto.getShipToAddress());
		repDto.setShippingCity(dto.getSupplierCity());
		repDto.setShippingPostalCode(dto.getShippingPostalCode());
		repDto.setShippingProvince(dto.getSupplierProvince());
		
		if ( dto.getSupplierId() != null) {
			repDto.setSupplierId(dto.getSupplierId());
			repDto.setSupplierName(dto.getSupplierName());
		}
		if ( dto.getBusinessId() != null) {
			repDto.setBusinessId(dto.getBusinessId());
		}
		
		if (dto.getShipToId() != null) {
			repDto.setShipToId(dto.getShipToId());
		}
		
		if ( dto.getShipToCountry() != null ) {
			repDto.setShipToCountry(dto.getShipToCountry());
		}	
		
		if ( dto.getSupplierCountry() != null ) {
			repDto.setSupplierCountry(dto.getSupplierCountry());
		}

	//	setRFQItems(RFQDTO, repDto);

		return repDto;
	}

	private void setRFQItems(RFQ domain, RFQRepDTO dto) throws Exception {

		for (RFQItem item : domain.getRfqItems()) {
			dto.getItems().add(RFQItemMapper.getInstance().domainToDto(item));
		}

	}

	public void dtoToDomain(RFQRepDTO dto, RFQ domain) throws Exception {
		domain.setId(dto.getId());
		domain.setCode(dto.getCode());

		domain.setDateExpectedDelivery(dto.getExpectedDeliveryDate());
		domain.setDateRequiredResponse(dto.getRequiredResponseDate());
		domain.setDateSent(dto.getSentDate());
		domain.setMessageSubject(dto.getMessageSubject());
		domain.setMessageContent(dto.getMessageContent());
		domain.setMessageSubject(dto.getMessageSubject());
		domain.setQuoteReferenceNumber(dto.getQuoteReferenceNumber());
		domain.setRfqStatus(dto.getRfqStatus());

		domain.setSupplierAddress(dto.getSupplierAddress());
		domain.setSupplierCity(dto.getSupplierCity());
		domain.setSupplierPostalCode(dto.getSupplierPostalCode());
		domain.setSupplierProvince(dto.getSupplierProvince());

		domain.setShippingAddress(dto.getShipToAddress());
		domain.setShippingCity(dto.getShippingCity());
		domain.setShippingPostalCode(dto.getShippingPostalCode());
		domain.setShippingProvince(dto.getShippingProvince());

		domain.setBillingAddress(dto.getBillToAddress());
		domain.setBillingCity(dto.getBillingCity());
		domain.setBillingPostalCode(dto.getBillingPostalCode());
		domain.setBillingProvince(dto.getBillingProvince());
	}

    public RFQRepDTO domainToDtoForDataTable(RFQ domain) throws Exception {
    	RFQRepDTO dto = new RFQRepDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());		
		dto.setStatusName(domain.getRfqStatus().getName());
		if ( domain.getSupplier() != null) {
			dto.setSupplierId(domain.getSupplier().getId());
			dto.setSupplierName(domain.getSupplier().getName());
		}
        return dto;
    }

	@Override
	public RFQRepDTO domainToRepDTO(RFQ domain) throws Exception {
		RFQRepDTO dto = new RFQRepDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setRfqStatus(domain.getRfqStatus());
		dto.setStatusName(domain.getRfqStatus().getName());

		dto.setExpectedDeliveryDate(domain.getDateExpectedDelivery());
		dto.setRequiredResponseDate(domain.getDateRequiredResponse());
		dto.setSentDate(domain.getDateSent());
		dto.setMessageSubject(domain.getMessageSubject());
		dto.setMessageContent(domain.getMessageContent());
		dto.setMessageSubject(domain.getMessageSubject());
		dto.setQuoteReferenceNumber(domain.getQuoteReferenceNumber());				
		
		dto.setSupplierAddress(domain.getSupplierAddress());		
		dto.setSupplierCity(domain.getSupplierCity());
		dto.setSupplierPostalCode(domain.getSupplierPostalCode());
		dto.setSupplierProvince(domain.getSupplierProvince());		
		
		dto.setShipToAddress(domain.getShippingAddress());
		dto.setShippingCity(domain.getSupplierCity());
		dto.setShippingPostalCode(domain.getShippingPostalCode());
		dto.setShippingProvince(domain.getSupplierProvince());
		
		if ( domain.getSupplier() != null) {
			dto.setSupplierId(domain.getSupplier().getId());
			dto.setSupplierName(domain.getSupplier().getName());
			dto.setSupplierEmail(domain.getSupplier().getPrimaryEmail());
		}
		if ( domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
		}
		
		if (domain.getShipTo() != null) {
			dto.setShipToId(domain.getShipTo().getId());
		}
		
		if ( domain.getShippingCountry() != null ) {
			dto.setShipToCountry(domain.getShippingCountry().getId());
		}	
		
		if ( domain.getSupplierCountry() != null ) {
			dto.setSupplierCountry(domain.getSupplierCountry().getId());
		}


		setRFQItems(domain, dto);

		return dto;
	}

}
