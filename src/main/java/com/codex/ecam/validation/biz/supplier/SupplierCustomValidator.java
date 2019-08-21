package com.codex.ecam.validation.biz.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.dao.biz.SupplierDao;
import com.codex.ecam.model.biz.business.Business;

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
