package com.codex.ecam.result.inventory;

import com.codex.ecam.dto.inventory.inventoryGroup.InventoryGroupDTO;
import com.codex.ecam.model.inventory.inventoryGroup.InventoryGroup;
import com.codex.ecam.result.BaseResult;

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
