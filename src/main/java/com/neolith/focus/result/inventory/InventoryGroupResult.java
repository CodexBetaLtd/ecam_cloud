package com.neolith.focus.result.inventory;

import com.neolith.focus.dto.inventory.inventoryGroup.InventoryGroupDTO;
import com.neolith.focus.model.inventory.inventoryGroup.InventoryGroup;
import com.neolith.focus.result.BaseResult;

public class InventoryGroupResult extends BaseResult<InventoryGroup, InventoryGroupDTO> {

    public InventoryGroupResult(InventoryGroup domain, InventoryGroupDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
