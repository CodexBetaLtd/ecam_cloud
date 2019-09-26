package com.codex.ecam.result.biz;

import com.codex.ecam.dto.biz.supplier.SupplierDTO;
import com.codex.ecam.model.biz.supplier.Supplier;
import com.codex.ecam.result.BaseResult;

public class SupplierResult extends BaseResult<Supplier, SupplierDTO> {

    public SupplierResult(Supplier domain, SupplierDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
