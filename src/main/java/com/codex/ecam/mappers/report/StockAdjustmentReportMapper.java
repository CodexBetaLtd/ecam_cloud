package com.codex.ecam.mappers.report;

import com.codex.ecam.dto.report.data.StockAdjustmentRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.inventory.stockAdjustment.StockAdjustment;


public class StockAdjustmentReportMapper extends GenericReportMapper<StockAdjustment, StockAdjustmentRepDTO> {

	private static StockAdjustmentReportMapper instance = null;

	private StockAdjustmentReportMapper() {
	}

	public static StockAdjustmentReportMapper getInstance() {
		if (instance == null) {
			instance = new StockAdjustmentReportMapper();
		}
		return instance;
	}

	@Override
	public StockAdjustmentRepDTO domainToRepDTO(StockAdjustment domain) throws Exception {
		StockAdjustmentRepDTO repDTO = new StockAdjustmentRepDTO();
		repDTO.setAdjustmentCode(domain.getAdjustmentCode());
		repDTO.setDate(domain.getStockAdjustmentDate());
		repDTO.setDescription(domain.getDescription());
		repDTO.setLastQuantity(domain.getLastQuantity());
		if(domain.getPart()!=null){
			repDTO.setPartName(domain.getPart().getName());
		}
		repDTO.setNewQuantity(domain.getNewQuantity());

		repDTO.setStatusName(domain.getStockAdjustmentStatus().getName());
		if(domain.getStock()!=null){
			repDTO.setStockNo(domain.getStock().getBatchNo());

		}
		return repDTO;
	}



}
