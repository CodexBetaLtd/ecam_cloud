package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.bom.BOMGroupPart;

import java.util.List;

@Repository
public interface BOMGroupPartDao extends JpaRepository<BOMGroupPart, Integer> {

	@Query(" from BOMGroupPart where bomGroup.id = :bomId and part.assetCategory.assetCategoryType = com.codex.ecam.constants.AssetCategoryType.PARTS_AND_SUPPLIES")
	List<BOMGroupPart> findByBomGroupId(@Param("bomId") Integer bomId);

}
