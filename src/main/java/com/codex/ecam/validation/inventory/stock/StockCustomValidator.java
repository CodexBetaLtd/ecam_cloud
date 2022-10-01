package com.codex.ecam.validation.inventory.stock;

import java.math.BigDecimal; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.model.inventory.stock.Stock; 

@Component
public class StockCustomValidator {

	@Autowired
	private StockDao stockDao; 

	public Boolean findStockOnHand(Integer partId, BigDecimal val) throws Exception {
		try { 
			Stock stock  = stockDao.findOne(partId);
			BigDecimal currentQty = stock.getCurrentQuantity();
			if (val.compareTo(currentQty) == 1) {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

}
