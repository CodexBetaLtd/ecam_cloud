package com.codex.ecam.controller.inventory.stock;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.ResponseBody;

import com.codex.ecam.validation.inventory.stock.StockCustomValidator;

@Controller
@RequestMapping(StockValidationController.REQUEST_MAPPING_URL)
public class StockValidationController {
	
	 public static final String REQUEST_MAPPING_URL = "/validate/stock";
	 
	 @Autowired
	 private StockCustomValidator validator;
	 
	 @RequestMapping(value = "/onhandqty", method = RequestMethod.GET)
	 public @ResponseBody Boolean stockOnHandQtyValidate(BigDecimal itemQuantity, Integer itemStockId) throws Exception {
		 return validator.findStockOnHand(itemStockId, itemQuantity);
	 }
	 
}
