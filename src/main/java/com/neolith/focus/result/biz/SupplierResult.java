package com.neolith.focus.result.biz;

import com.neolith.focus.dto.biz.supplier.SupplierDTO;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.result.BaseResult;

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
