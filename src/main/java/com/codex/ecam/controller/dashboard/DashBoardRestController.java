package com.codex.ecam.controller.dashboard;
 
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.StockService;
import com.codex.ecam.service.maintenance.api.WorkOrderService; 

@RestController
@RequestMapping(DashBoardRestController.REQUEST_MAPPING_URL)
public class DashBoardRestController {
	
	public static final String REQUEST_MAPPING_URL = "/restapi/dashboard";
	
    @Autowired
    private WorkOrderService workOrderService;
    
    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/openworkorder", method = RequestMethod.GET)
    public DataTablesOutput<WorkOrderDTO> getOpenWorkOrderDataTable(@Valid FocusDataTablesInput input) {
    	try {
    		return workOrderService.findAllOpenWorkOder(input);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @RequestMapping(value = "/highpriorityworkorder", method = RequestMethod.GET)
    public DataTablesOutput<WorkOrderDTO> getHighPriorityWorkOrderDataTable(@Valid FocusDataTablesInput input) {
    	try {
    		return workOrderService.findAllHighPriorityWorkOder(input);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @RequestMapping(value = "/lowstockitem", method = RequestMethod.GET)
    public DataTablesOutput<StockDTO> getLowStockItemDataTable(@Valid FocusDataTablesInput input) {
        try {
            return stockService.findLowStockPartItem(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	
}
