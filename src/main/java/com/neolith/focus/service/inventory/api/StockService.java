package com.neolith.focus.service.inventory.api;
 
import com.neolith.focus.dto.inventory.stock.StockDTO;
import com.neolith.focus.dto.inventory.stock.StockHistoryDTO;
import com.neolith.focus.dto.inventory.stock.StockViewFilterDTO;
import com.neolith.focus.dto.inventory.stockAdjuestment.StockAdjustmentDTO;
import com.neolith.focus.model.inventory.aod.AOD;
import com.neolith.focus.model.inventory.receiptOrder.ReceiptOrderItem;
import com.neolith.focus.model.inventory.receiptOrder.ReceiptOrderTax;
import com.neolith.focus.model.inventory.stock.Stock;
import com.neolith.focus.repository.FocusDataTablesInput; 
import com.neolith.focus.result.inventory.StockResult; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.math.BigDecimal;
import java.util.List;

public interface StockService { 
	
	void adjustStock(StockAdjustmentDTO dto) throws Exception;
	
	void updateItemAVGPrice(Integer partId) throws Exception; 
	
	void saveUpdatePartStock(List<StockDTO> stockDTOs);

    StockResult stockReceived(ReceiptOrderItem receiptOrderItem, BigDecimal subTotal, List<ReceiptOrderTax> receiptOrderTaxes);

    StockResult dispatchStock(AOD aod) throws Exception;
    
    StockResult save(StockDTO dto);

    StockResult findByIdWithOpenPOs(Integer id);

    StockResult delete(Integer id); 
    
    StockResult findStockOnHand(Integer partId, Integer warehouse) throws Exception;

    Stock dispatchedReturn(Integer stockId, BigDecimal returnQty) throws Exception; 
    
    Stock findOne(Integer stockId) throws Exception; 

    StockDTO findById(Integer id) throws Exception;

    List<StockDTO> getStockSummary(StockViewFilterDTO stockViewFilterDTO);

    List<StockDTO> getStockDetailList(StockViewFilterDTO stockViewFilterDTO);

    List<StockDTO> findStockByPart(Integer assetId); 

    List<StockHistoryDTO> findStockHistoryByStock(Integer stockId); 

    DataTablesOutput<StockDTO> findStockByPart(FocusDataTablesInput input, Integer partId) throws Exception;

    DataTablesOutput<StockDTO> findStockByAsset(FocusDataTablesInput input, Integer assetId) throws Exception;

    DataTablesOutput<StockDTO> findStockByAssetTemp(FocusDataTablesInput input, Integer assetId) throws Exception;

    DataTablesOutput<StockDTO> findStockByUserLevel(FocusDataTablesInput input, Integer assetId) throws Exception;

    DataTablesOutput<StockDTO> findStockByPartAndWarehouse(FocusDataTablesInput input, Integer partId, Integer warehouseId) throws Exception;

    DataTablesOutput<StockDTO> findRemainStockPartList(FocusDataTablesInput input);

    DataTablesOutput<StockDTO> findStockPartList(FocusDataTablesInput input) throws Exception;  
    
    
}
