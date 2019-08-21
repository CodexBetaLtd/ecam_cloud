package com.codex.ecam.validation.inventory.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.model.asset.Asset;

import java.util.List;

@Component
public class PartCustomValidator {

    @Autowired
    private AssetDao partDao;

    public Boolean validateDuplicateCode(Integer id, String code) {
        List<Asset> businesses = partDao.findDuplicateByCodeAndId(id, code);
        if ((businesses != null) && (businesses.size() > 0)) {
            return false;
        }
        return true;
    }

}
