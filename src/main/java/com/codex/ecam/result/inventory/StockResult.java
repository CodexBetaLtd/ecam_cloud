package com.codex.ecam.result.inventory;

import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.result.BaseResult;

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
