package com.codex.ecam.result.inventory;

import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentDTO;
import com.codex.ecam.model.inventory.stockAdjustment.StockAdjustment;
import com.codex.ecam.result.BaseResult;

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
