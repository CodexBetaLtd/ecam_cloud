package com.neolith.focus.result.inventory;

import com.neolith.focus.dto.inventory.stockAdjuestment.StockAdjustmentDTO;
import com.neolith.focus.model.inventory.stockAdjustment.StockAdjustment;
import com.neolith.focus.result.BaseResult;

public class StockAdjustmentResult extends BaseResult<StockAdjustment, StockAdjustmentDTO> {

    public StockAdjustmentResult(StockAdjustment domain, StockAdjustmentDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
