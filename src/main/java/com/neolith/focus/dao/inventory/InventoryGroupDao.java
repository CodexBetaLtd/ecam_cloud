package com.neolith.focus.dao.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neolith.focus.model.inventory.inventoryGroup.InventoryGroup;
import com.neolith.focus.repository.FocusDataTableRepository;

@Repository
public interface InventoryGroupDao extends FocusDataTableRepository<InventoryGroup, Integer> {
	
	@Query("from InventoryGroup where inventoryGroupNo = :code and (:id is null or id != :id)")
	List<InventoryGroup> findDuplicateByCodeAndId(@Param("id") Integer id, @Param("code") String code);

	@Query("from InventoryGroup where name = :name and (:id is null or id != :id)")
	List<InventoryGroup> findDuplicateByNameAndId(@Param("id") Integer id, @Param("name") String name);

}
