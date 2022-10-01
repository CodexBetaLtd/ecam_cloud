package com.codex.ecam.mappers.maintenance.workorder;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderPartDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.workorder.WorkOrderPart;


public class WorkOrderPartMapper extends GenericMapper<WorkOrderPart, WorkOrderPartDTO>{

	private static WorkOrderPartMapper instance = null;

	private WorkOrderPartMapper(){}

	public static WorkOrderPartMapper getInstance(){
		if(instance == null){
			instance = new WorkOrderPartMapper();
		}
		return instance;
	}

	@Override
	public WorkOrderPartDTO domainToDto(WorkOrderPart domain) throws Exception {
		WorkOrderPartDTO dto = new WorkOrderPartDTO();
		dto.setWoPartId(domain.getId());
		dto.setWoPartEstimatedQuantity(domain.getEstimatedQuantity());
		dto.setWoPartActualQuantity(domain.getActualQuantity());
		dto.setWoPartRemark(domain.getRemark());

		if(domain.getWorkOrderTask() != null){
			dto.setWoPartTaskId(domain.getWorkOrderTask().getId());
			dto.setWoPartTaskDescription(domain.getWorkOrderTask().getDescription());
			dto.setWoPartWorkOrderId(domain.getWorkOrderTask().getWorkOrder().getId());
			dto.setWoPartAssetId(domain.getWorkOrderTask().getAsset().getId());
			dto.setWoPartAssetName(domain.getWorkOrderTask().getAsset().getName());
		}

		if(domain.getWorkOrder() != null){
			dto.setWoPartWorkOrderId(domain.getWorkOrder().getId());
		}

		if(domain.getPart() != null){
			dto.setWoPartPartId(domain.getPart().getId());
			dto.setWoPartPartName(domain.getPart().getName());
		}

		if(domain.getStock() != null){
			dto.setWoPartStockId(domain.getStock().getId());
			dto.setWoPartLocation(domain.getStock().getSite().getName());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(WorkOrderPartDTO dto, WorkOrderPart domain) throws Exception {
		domain.setId(dto.getWoPartId());
		domain.setEstimatedQuantity(dto.getWoPartEstimatedQuantity());
		domain.setActualQuantity(dto.getWoPartActualQuantity());
		domain.setRemark(dto.getWoPartRemark());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public WorkOrderPartDTO domainToDtoForDataTable(WorkOrderPart domain) throws Exception {
		WorkOrderPartDTO dto = new WorkOrderPartDTO();
		dto.setWoPartId(domain.getId());
		dto.setWoPartEstimatedQuantity(domain.getEstimatedQuantity());
		dto.setWoPartActualQuantity(domain.getActualQuantity());
		dto.setWoPartRemark(domain.getRemark());

		return dto;
	}

}
