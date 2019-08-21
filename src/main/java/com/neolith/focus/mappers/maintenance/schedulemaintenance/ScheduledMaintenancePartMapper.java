package com.neolith.focus.mappers.maintenance.schedulemaintenance;

import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenancePartDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenancePart;


public class ScheduledMaintenancePartMapper extends GenericMapper<ScheduledMaintenancePart, ScheduledMaintenancePartDTO>{

	private static ScheduledMaintenancePartMapper instance = null;

	private ScheduledMaintenancePartMapper(){}

	public static ScheduledMaintenancePartMapper getInstance(){
		if(instance == null){
			instance = new ScheduledMaintenancePartMapper();
		}
		return instance;
	}

	@Override
	public ScheduledMaintenancePartDTO domainToDto(ScheduledMaintenancePart domain) throws Exception {
		ScheduledMaintenancePartDTO dto = new ScheduledMaintenancePartDTO();
		dto.setPartId(domain.getId());
		dto.setPartSuggestedQuantity(domain.getSuggestedQuantity());

		if(domain.getScheduledMaintenanceTask() != null){
			dto.setPartTaskId(domain.getScheduledMaintenanceTask().getId());
			dto.setPartTaskDescription(domain.getScheduledMaintenanceTask().getDescription());
			dto.setPartScheduledMaintenanceId(domain.getScheduledMaintenanceTask().getScheduledMaintenanceTrigger().getScheduledMaintenance().getId());
			dto.setPartTaskAssetId(domain.getScheduledMaintenanceTask().getScheduledMaintenanceTrigger().getAsset().getId());
			dto.setPartTaskAssetName(domain.getScheduledMaintenanceTask().getScheduledMaintenanceTrigger().getAsset().getName());
		}

		if(domain.getPart() != null){
			dto.setPartPartId(domain.getPart().getId());
			dto.setPartName(domain.getPart().getName());
		}

		if(domain.getStock() != null){
			dto.setPartStockId(domain.getStock().getId());
			dto.setPartLocation(domain.getStock().getSite().getName());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(ScheduledMaintenancePartDTO dto, ScheduledMaintenancePart domain) throws Exception {
		domain.setId(dto.getPartId());
		domain.setSuggestedQuantity(dto.getPartSuggestedQuantity());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public ScheduledMaintenancePartDTO domainToDtoForDataTable(ScheduledMaintenancePart domain) throws Exception {
		ScheduledMaintenancePartDTO dto = new ScheduledMaintenancePartDTO();
		dto.setPartId(domain.getId());
		dto.setPartSuggestedQuantity(domain.getSuggestedQuantity());

		return dto;
	}

}
