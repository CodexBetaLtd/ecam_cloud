package com.neolith.focus.validation.asset;

import com.neolith.focus.dao.asset.AssetDao;
import com.neolith.focus.model.asset.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssetCustomValidator {

    @Autowired
    private AssetDao assetDao;

    public Boolean validateDuplicateAssetCode(Integer id, String code) {
        List<Asset> assets = assetDao.findDuplicateByCodeAndId(id, code);
        if (assets != null && assets.size() > 0) {
            return false;
        }
        return true;
    }

    public Boolean validateDuplicateAssetName(Integer id, String name) {
        List<Asset> assets = assetDao.findDuplicateByNameAndId(id, name);
        if (assets != null && assets.size() > 0) {
            return false;
        }
        return true;
    }

}
