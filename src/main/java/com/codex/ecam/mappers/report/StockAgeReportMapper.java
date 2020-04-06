package com.codex.ecam.mappers.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.codex.ecam.dto.report.data.StockAgeRepDTO;
import com.codex.ecam.dto.report.filter.StockAgeFilterDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.inventory.stock.Stock;


public class StockAgeReportMapper extends GenericReportMapper<Stock, StockAgeRepDTO> {

	private static StockAgeReportMapper instance = null;

	private StockAgeReportMapper() {
	}

	public static StockAgeReportMapper getInstance() {
		if (instance == null) {
			instance = new StockAgeReportMapper();
		}
		return instance;
	}

	@Override
	public StockAgeRepDTO domainToRepDTO(Stock domain) throws Exception {
		StockAgeRepDTO repDTO = new StockAgeRepDTO();
		repDTO.setStockAgeStockNo(domain.getStockNo());
		repDTO.setStockAgeCreatedDate(domain.getDate());
		if(domain.getPart() != null){
			repDTO.setStockAgePartCode(domain.getPart().getCode());
			repDTO.setStockAgePartDescription(domain.getPart().getDescription());
		}
		repDTO.setStockAgePartUnitPrice(domain.getSellingPrice());
		repDTO.setStockAgeRemainQty(domain.getCurrentQuantity());
		repDTO.setStockAgeStockQty(domain.getLastQuantity());
		repDTO.setStockAgeTotalAmount(BigDecimal.ZERO);
		setStockAgeDays(repDTO);
		return repDTO;
	}
	
	 private void setStockAgeDays(StockAgeRepDTO repDTO){
	
			 if(repDTO.getStockAgeCreatedDate()!=null){
				 int days=setNoOfDays(repDTO.getStockAgeCreatedDate(),new Date());
				 repDTO.setStockAgeDaysAge(days);
				 repDTO.setStockAgeYears(BigDecimal.valueOf(days/365));
				 repDTO.setStockAgeMonths(BigDecimal.valueOf(days%12));
				 repDTO.setStockAgeDays(BigDecimal.valueOf(days%30));
			 }

		 
	 }
		
	 private Integer setNoOfDays(Date reportDate,Date stockDate){
		        long difference = Math.abs(reportDate.getTime() - stockDate.getTime());
		        long differenceDates = difference / (24 * 60 * 60 * 1000);

		        //Convert long to String
	return (int) differenceDates;

	 }



}
