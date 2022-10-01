package com.codex.ecam.mappers.admin;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.admin.cmmssetting.tax.TaxDTO;
import com.codex.ecam.dto.admin.cmmssetting.tax.TaxValueDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.admin.Tax;
import com.codex.ecam.model.admin.TaxValue;

public class TaxMapper extends GenericMapper<Tax, TaxDTO> {

	private static TaxMapper instance = null;

	private  TaxMapper() {
	}

	public static TaxMapper getInstance() {
		if (instance == null) {
			instance = new TaxMapper();
		}
		return instance;
	}

	@Override
	public TaxDTO domainToDto(Tax  domain) throws Exception {
		TaxDTO dto = new TaxDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setPriorty(domain.getPriorty());

		if ( domain.getBusiness() != null ) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		dto.setTaxType(domain.getTaxType());
		setCommanDTOFields(dto, domain);
		setTaxValues(dto, domain);

		return dto;
	}
	
	private void setTaxValues(TaxDTO dto, Tax domain){
		if(domain.getTaxValues().size()>0){
			List<TaxValueDTO> list=new ArrayList<>();
			for(TaxValue taxValue:domain.getTaxValues()){
				TaxValueDTO valuedto=new TaxValueDTO();
				valuedto.setId(taxValue.getId());
				valuedto.setVersion(taxValue.getVersion());
				valuedto.setStartDate(taxValue.getFromDate());
				valuedto.setEndDate(taxValue.getToDate());
				valuedto.setIsCurrentRate(taxValue.getIsCurrentRate());
				list.add(valuedto);
				
			}
			dto.setTaxValueDTOs(list);
		}

	}

	@Override
	public void dtoToDomain(TaxDTO dto, Tax domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());
		domain.setPriorty(dto.getPriorty());
		domain.setTaxType(dto.getTaxType());


	}

	@Override
	public TaxDTO domainToDtoForDataTable(Tax domain) throws Exception {
		TaxDTO dto = new TaxDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setPriorty(domain.getPriorty());
		
		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		setCurrentTaxValues(dto, domain);
		dto.setTaxType(domain.getTaxType());


		return dto;
	}
	
	private void setCurrentTaxValues(TaxDTO dto, Tax domain){
		if(domain.getTaxValues().size()>0){
			for(TaxValue taxValue:domain.getTaxValues()){
				if(taxValue.getIsCurrentRate()){
					dto.setId(taxValue.getId());
					dto.setVersion(taxValue.getVersion());
					dto.setCurrentValue(taxValue.getValue());
					dto.setCurrentValueid(taxValue.getId());
					dto.setCurrentValueStartDate(taxValue.getFromDate());
					dto.setCurrentValueEndDate(taxValue.getToDate());
				}

				
			}
		}

	}

}
