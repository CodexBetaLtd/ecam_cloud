package com.codex.ecam.mappers.maintenance.workorder;

import com.codex.ecam.dto.maintenance.workOrder.MiscellaneousExpenseDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.miscellaneous.WorkOrderMiscellaneousExpense;

public class WorkOrderMiscellaneousExpenseMapper extends GenericMapper<WorkOrderMiscellaneousExpense, MiscellaneousExpenseDTO> {

	private static WorkOrderMiscellaneousExpenseMapper instance = null;

	private WorkOrderMiscellaneousExpenseMapper() {
	}

	public static WorkOrderMiscellaneousExpenseMapper getInstance() {
		if (instance == null) {
			instance = new WorkOrderMiscellaneousExpenseMapper();
		}
		return instance;
	}


	@Override
	public MiscellaneousExpenseDTO domainToDto(WorkOrderMiscellaneousExpense domain) throws Exception {
		MiscellaneousExpenseDTO dto = new MiscellaneousExpenseDTO();
		dto.setId(domain.getId());
		dto.setDescription(domain.getDescription());
		dto.setEstimatedUnitCost(domain.getEstimatedUnitCost());
		dto.setEstimatedQuantity(domain.getEstimatedQuantity());
		dto.setEstimatedTotalCost(domain.getEstimatedTotalCost());
		dto.setActualUnitCost(domain.getActualUnitCost());
		dto.setActualQuantity(domain.getEstimatedQuantity());
		dto.setActualTotalCost(domain.getActualTotalCost());

		if ((domain.getMiscellaneousExpenseType() != null) && (domain.getMiscellaneousExpenseType().getId() != null)) {
			dto.setMiscellaneousExpenseTypeId(domain.getMiscellaneousExpenseType().getId());
			dto.setMiscellaneousExpenseTypeDescription(domain.getMiscellaneousExpenseType().getDescription());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public MiscellaneousExpenseDTO domainToDtoForDataTable(WorkOrderMiscellaneousExpense domain) throws Exception {
		MiscellaneousExpenseDTO dto = new MiscellaneousExpenseDTO();
		dto.setId(domain.getId());
		dto.setDescription(domain.getDescription());
		dto.setEstimatedUnitCost(domain.getEstimatedUnitCost());
		dto.setEstimatedQuantity(domain.getEstimatedQuantity());
		dto.setEstimatedTotalCost(domain.getEstimatedTotalCost());
		dto.setActualUnitCost(domain.getActualUnitCost());
		dto.setActualQuantity(domain.getEstimatedQuantity());
		dto.setActualTotalCost(domain.getActualTotalCost());

		return dto;
	}

	@Override
	public void dtoToDomain(MiscellaneousExpenseDTO dto, WorkOrderMiscellaneousExpense domain) throws Exception {
		domain.setId(dto.getId());
		domain.setDescription(dto.getDescription());
		domain.setEstimatedUnitCost(dto.getEstimatedUnitCost());
		domain.setEstimatedQuantity(dto.getEstimatedQuantity());
		domain.setEstimatedTotalCost(dto.getEstimatedTotalCost());
		domain.setActualUnitCost(dto.getActualUnitCost());
		domain.setActualTotalCost(dto.getActualTotalCost());

		setCommanDomainFields(dto, domain);
	}
}
