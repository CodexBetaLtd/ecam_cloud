package com.codex.ecam.dao.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.inventoryGroup.InventoryGroup;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface InventoryGroupDao extends FocusDataTableRepository<InventoryGroup, Integer> {
	
	@Query("from InventoryGroup where inventoryGroupNo = :code and (:id is null or id != :id)")
	List<InventoryGroup> findDuplicateByCodeAndId(@Param("id") Integer id, @Param("code") String code);

	@Query("from InventoryGroup where name = :name and (:id is null or id != :id)")
	List<InventoryGroup> findDuplicateByNameAndId(@Param("id") Integer id, @Param("name") String name);

}
