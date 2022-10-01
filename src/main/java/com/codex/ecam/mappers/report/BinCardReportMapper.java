package com.codex.ecam.mappers.report;

import java.math.BigDecimal;

import com.codex.ecam.dto.report.data.BinCardRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.inventory.stock.StockHistory;


public class BinCardReportMapper extends GenericReportMapper<StockHistory, BinCardRepDTO> {

	private static BinCardReportMapper instance = null;

	private BinCardReportMapper() {
	}

	public static BinCardReportMapper getInstance() {
		if (instance == null) {
			instance = new BinCardReportMapper();
		}
		return instance;
	}

	@Override
	public BinCardRepDTO domainToRepDTO(StockHistory domain) throws Exception {
		BinCardRepDTO repDTO = new BinCardRepDTO();
		if(domain.getStock()!=null){
			if(domain.getStock().getPart()!=null){
				repDTO.setBinCardPartCode(domain.getStock().getPart().getCode());
			}
			repDTO.setBinCardStockNo(domain.getStock().getBatchNo());
		}

		repDTO.setBinCardStockDescription(domain.getDescription());
        repDTO.setBinCardDate(domain.getDate());
        repDTO.setBinCardStockQty(domain.getQuantity());
        repDTO.setBinCardAfterQty(domain.getAfterQuantity());
        if(domain.getBeforeQuantity()!=null){
            repDTO.setBinCardBeforeQty(domain.getBeforeQuantity());

        }else{
            repDTO.setBinCardBeforeQty(BigDecimal.ZERO);
        }
        if(domain.getAfterQuantity()!=null && domain.getBeforeQuantity()!=null){
            repDTO.setBinCardBalance(domain.getAfterQuantity().subtract(domain.getBeforeQuantity()));

        }
		return repDTO;
	}



}
