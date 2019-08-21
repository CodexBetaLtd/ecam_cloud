package com.neolith.focus.validation.inventory.part;

import com.neolith.focus.dao.asset.AssetDao;
import com.neolith.focus.model.asset.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
