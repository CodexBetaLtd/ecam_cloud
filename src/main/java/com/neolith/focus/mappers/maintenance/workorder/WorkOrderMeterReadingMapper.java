package com.neolith.focus.mappers.maintenance.workorder;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderMeterReadingDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.AssetMeterReading;
import com.neolith.focus.model.maintenance.workorder.WorkOrderMeterReading;

public class WorkOrderMeterReadingMapper extends GenericMapper<WorkOrderMeterReading, WorkOrderMeterReadingDTO> {
	private static WorkOrderMeterReadingMapper instance = null;

	private WorkOrderMeterReadingMapper() {
	}

	public static WorkOrderMeterReadingMapper getInstance() {
		if (instance == null) {
			instance = new WorkOrderMeterReadingMapper();
		}
		return instance;
	}

	@Override
	public WorkOrderMeterReadingDTO domainToDto(WorkOrderMeterReading domain) throws Exception {
		WorkOrderMeterReadingDTO dto = new WorkOrderMeterReadingDTO();
		dto.setId(domain.getId());
		dto.setMeterReadingDescription(domain.getDescription());

		if ((domain.getAssetMeterReading() != null) && (domain.getAssetMeterReading().getId() != null)) {
			dto.setMeterReadingId(domain.getAssetMeterReading().getId());
			dto.setMeterReadingName(domain.getAssetMeterReading().getMeterReadingName());
		}

		if ((domain.getAssetMeterReading() != null) && (domain.getAssetMeterReading().getAsset() != null) && (domain.getAssetMeterReading().getAsset().getId() != null)) {
			dto.setMeterReadingAssetId(domain.getAssetMeterReading().getAsset().getId());
			dto.setMeterReadingAssetName(domain.getAssetMeterReading().getAsset().getName());
		}

		if((domain.getWorkOrder() != null) && (domain.getWorkOrder().getId() != null)){
			dto.setWorkOrderId(domain.getWorkOrder().getId());
		}

		setMeterReadingValues(domain.getAssetMeterReading(), dto);

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setMeterReadingValues(AssetMeterReading domain, WorkOrderMeterReadingDTO dto) throws Exception {
		if (domain.getAssetMeterReadingValues().size() > 0) {
			dto.setMeterReadingCurrentValue(domain.getCurrentAssetMeterReadingValue().getMeterReadingValue());
			dto.setMeterReadingCurrentValueId(domain.getCurrentAssetMeterReadingValue().getId());
		}
	}

	@Override
	public void dtoToDomain(WorkOrderMeterReadingDTO dto, WorkOrderMeterReading domain) throws Exception {
		domain.setId(dto.getId());
		domain.setDescription(dto.getMeterReadingDescription());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public WorkOrderMeterReadingDTO domainToDtoForDataTable(WorkOrderMeterReading domain) throws Exception {
		WorkOrderMeterReadingDTO dto = new WorkOrderMeterReadingDTO();
		dto.setId(domain.getId());
		dto.setMeterReadingDescription(domain.getDescription());

		return dto;
	}

}
