package com.codex.ecam.result.biz;

import com.codex.ecam.dto.biz.supplier.SupplierDTO;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.result.BaseResult;

public class SupplierResult extends BaseResult<Business, SupplierDTO> {

    public SupplierResult(Business domain, SupplierDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
