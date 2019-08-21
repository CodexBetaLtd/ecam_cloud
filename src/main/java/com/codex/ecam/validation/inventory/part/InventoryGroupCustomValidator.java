package com.codex.ecam.validation.inventory.part;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.dao.inventory.InventoryGroupDao;
import com.codex.ecam.model.inventory.inventoryGroup.InventoryGroup;

@Component
public class InventoryGroupCustomValidator {

    @Autowired
    private InventoryGroupDao inventoryGroupDao;

    public Boolean validateDuplicateCode(Integer id, String code) {
    	List<InventoryGroup> inventoryGroups = inventoryGroupDao.findDuplicateByCodeAndId(id, code);
    	if ((inventoryGroups != null) && (inventoryGroups.size() > 0)) {
    		return false;
    	}
    	return true;
    }
    
    public Boolean validateDuplicateName(Integer id, String name) {
        List<InventoryGroup> inventoryGroups = inventoryGroupDao.findDuplicateByNameAndId(id, name);
        if ((inventoryGroups != null) && (inventoryGroups.size() > 0)) {
            return false;
        }
        return true;
    }

}
