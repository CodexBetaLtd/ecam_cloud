package com.codex.ecam.controller.inventory.stockAdjustment;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.StockAdjustmentService;

import javax.validation.Valid;

@RestController
@RequestMapping(StockAdjustmentRestController.REQUEST_MAPPING_URL)
public class StockAdjustmentRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/stockAdjustment";

    @Autowired
    private StockAdjustmentService stockAdjustmentService;

    @RequestMapping(value = "/dtStockAdjustment", method = RequestMethod.GET)
    public DataTablesOutput<StockAdjustmentDTO> findStockAdjustmentDataTable(@Valid FocusDataTablesInput input, Integer partId) {
        try {
            return stockAdjustmentService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/dtStockByWarehouse", method = RequestMethod.GET)
    public DataTablesOutput<StockDTO> findStockByWarehouseDataTable(@Valid FocusDataTablesInput input, Integer partId) {
        try {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
