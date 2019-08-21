package com.codex.ecam.validation.asset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.model.asset.Asset;

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
