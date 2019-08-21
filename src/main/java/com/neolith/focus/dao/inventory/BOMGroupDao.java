package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.bom.BOMGroup;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BOMGroupDao extends FocusDataTableRepository<BOMGroup, Integer> {

	@Query("select bomPart.bomGroup from BOMGroupPart as bomPart left join bomPart.part as asset where asset.id = :assetId group by bomPart.bomGroup")
	List<BOMGroup> findGroupsByAssetId(@Param("assetId") Integer assetId);

}
