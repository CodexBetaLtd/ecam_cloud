package com.neolith.focus.validation.biz.supplier;

import com.neolith.focus.dao.biz.SupplierDao;
import com.neolith.focus.model.biz.business.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SupplierCustomValidator {

    @Autowired
    private SupplierDao supplierDao;

    public Boolean validateDuplicateCode(Integer id, String code) {
        List<Business> businesses = supplierDao.findDuplicateByCodeAndId(id, code);
        if ((businesses != null) && (businesses.size() > 0)) {
            return false;
        }
        return true;
    }

}
