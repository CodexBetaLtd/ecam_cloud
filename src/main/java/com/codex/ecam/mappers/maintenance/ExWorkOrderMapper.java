package com.codex.ecam.mappers.maintenance;

import com.codex.ecam.dto.maintenance.exworkorder.ExWorkOrderDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.ExWorkOrder;

public class ExWorkOrderMapper extends GenericMapper<ExWorkOrder, ExWorkOrderDTO> {

	private static ExWorkOrderMapper instance = null;

	private ExWorkOrderMapper() {
	}

	public static ExWorkOrderMapper getInstance() {
		if (instance == null) {
			instance = new ExWorkOrderMapper();
		}
		return instance;
	}

	@Override
	public ExWorkOrderDTO domainToDto(ExWorkOrder domain) throws Exception {
		ExWorkOrderDTO dto = new ExWorkOrderDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setMaintenanceSummary(domain.getMaintenanceSummary());
		dto.setRequestedDate(domain.getDateRequested());
		dto.setCompletedDate(domain.getDateCompleted());
		dto.setWorkOrderStatus(domain.getWoStatus());
		dto.setNote(domain.getNote());
		if (domain.getRequestedByUser() != null) {
			dto.setServiceRequestId(domain.getRequestedByUser().getId());
			dto.setServiceRequestName(domain.getRequestedByUser().getFullName());
		}
		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}
		if (domain.getAsset() != null) {
			dto.setAssetId(domain.getAsset().getId());
			dto.setAssetName(domain.getAsset().getName());
		}
		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
			dto.setSiteName(domain.getSite().getName());
		}
		if (domain.getSupplier() != null) {
			dto.setServiceProviderName(domain.getSupplier().getName());
			dto.setServiceProviderId(domain.getSupplier().getId());
		}
		setCommanDTOFields(dto, domain);

		return dto;
	}


	@Override
	public void dtoToDomain(ExWorkOrderDTO dto, ExWorkOrder domain) throws Exception {
		domain.setId(dto.getId());
		domain.setCode(dto.getCode());
		domain.setActualCost(dto.getActualCost());
		domain.setEstimatedCost(dto.getEstimatedCost());
		domain.setMaintenanceSummary(dto.getMaintenanceSummary());
		domain.setNote(dto.getNote());
		domain.setDateRequested(dto.getRequestedDate());
		domain.setDateCompleted(dto.getCompletedDate());
		domain.setWoStatus(dto.getWorkOrderStatus());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public ExWorkOrderDTO domainToDtoForDataTable(ExWorkOrder domain) throws Exception {
		ExWorkOrderDTO dto = new ExWorkOrderDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setMaintenanceSummary(domain.getMaintenanceSummary());
		dto.setRequestedDate(domain.getDateRequested());
		dto.setCompletedDate(domain.getDateCompleted());
		dto.setWorkOrderStatus(domain.getWoStatus());
		if (domain.getRequestedByUser() != null) {
			dto.setServiceRequestName(domain.getRequestedByUser().getFullName());
		}
		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
		}
		if (domain.getAsset() != null) {
			dto.setAssetId(domain.getAsset().getId());
		}
		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
		}
		if (domain.getSupplier() != null) {
			dto.setServiceProviderName(domain.getSupplier().getName());
		}
		return dto;
	}

}
