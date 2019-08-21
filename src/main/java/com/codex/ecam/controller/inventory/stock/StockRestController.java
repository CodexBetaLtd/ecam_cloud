package com.codex.ecam.controller.inventory.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.dto.inventory.stock.StockLedgerDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.StockResult;
import com.codex.ecam.service.inventory.api.StockService;
import com.codex.ecam.service.log.api.StockLogService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(StockRestController.REQUEST_MAPPING_URL)
public class StockRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/stock";

    @Autowired
    private StockService stockService;
    
    @Autowired
    private StockLogService stockLogService ;


    /*****************************************************
     * DataTables
     *******************************************************/

    @RequestMapping(value = "/tabledata-by-part", method = RequestMethod.GET)
    public DataTablesOutput<StockDTO> findStockByPartDataTable(@Valid FocusDataTablesInput input, Integer id) {
        try {
            return stockService.findStockByPart(input, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/stockByAssetTemp", method = RequestMethod.GET)
    public DataTablesOutput<StockDTO> findStockByAssetTemp(@Valid FocusDataTablesInput input, Integer partId) {
        try {
            return stockService.findStockByAssetTemp(input, partId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/stockByAsset", method = RequestMethod.GET)
    public DataTablesOutput<StockDTO> findStockByAsset(@Valid FocusDataTablesInput input, Integer assetId) {
        try {
            return stockService.findStockByAsset(input, assetId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/stockByPart", method = RequestMethod.GET)
    public DataTablesOutput<StockDTO> findStockByPart(@Valid FocusDataTablesInput input, Integer partId) {
    	try {
    		return stockService.findStockByPart(input, partId);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    @RequestMapping(value = "/remainStockPartList", method = RequestMethod.GET)
    public DataTablesOutput<StockDTO> findRemainStockPartList(@Valid FocusDataTablesInput input) {
        try {
            return stockService.findRemainStockPartList(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataTablesOutput<>();
    }

    @RequestMapping(value = "/stockPartList", method = RequestMethod.GET)
    public DataTablesOutput<StockDTO> findStockPartList(@Valid FocusDataTablesInput input) {
        try {
            return stockService.findStockPartList(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataTablesOutput<>();
    }


    /*****************************************************
     * Others
     *******************************************************/
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public StockDTO getStockById(Integer id) {
        try {
            return stockService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new StockDTO();
        }
    }

    @RequestMapping(value = "/findRemainItemQty", method = RequestMethod.GET)
    public BigDecimal getRemainQtyByPartAndWarehouse(@Valid Integer partId, @Valid Integer warehouseId) {
        StockResult result;
        try {
            result = stockService.findStockOnHand(partId, warehouseId);
            return result.getDtoEntity().getQtyOnHand();
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/stockByPartList", method = RequestMethod.GET)
    public List<StockDTO> findStockByPart(Integer partId) {
    	try {
    		return stockService.findStockByPart(partId);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return new ArrayList<>();
    }
    
    @RequestMapping(value = "/stockLedger", method = RequestMethod.GET)
    public DataTablesOutput<StockLedgerDTO> getStockLedger(@Valid FocusDataTablesInput input, Integer stockId) {
        try {
            return stockLogService.findAllByStockId(input, stockId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
