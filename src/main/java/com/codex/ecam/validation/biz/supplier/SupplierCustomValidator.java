package com.codex.ecam.validation.biz.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.dao.biz.SupplierDao;
import com.codex.ecam.model.biz.supplier.Supplier;

@Component
public class SupplierCustomValidator {

    @Autowired
    private SupplierDao supplierDao;

    public Boolean validateDuplicateCode(Integer id, String code) {
        List<Supplier> businesses = supplierDao.findDuplicateByCodeAndId(id, code);
        if ((businesses != null) && (businesses.size() > 0)) {
            return false;
        }
        return true;
    }

}
