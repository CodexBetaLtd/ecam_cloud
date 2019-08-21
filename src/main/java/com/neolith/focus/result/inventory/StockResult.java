package com.neolith.focus.result.inventory;

import com.neolith.focus.dto.inventory.stock.StockDTO;
import com.neolith.focus.model.inventory.stock.Stock;
import com.neolith.focus.result.BaseResult;

public class StockResult extends BaseResult<Stock, StockDTO> {

    public StockResult(Stock domain, StockDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
