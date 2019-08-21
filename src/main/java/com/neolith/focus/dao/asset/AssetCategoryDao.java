package com.neolith.focus.dao.asset;


import com.neolith.focus.constants.AssetCategoryType;
import com.neolith.focus.model.asset.AssetCategory;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetCategoryDao extends FocusDataTableRepository<AssetCategory, Integer>{

	AssetCategory findById(Integer id);

	@Query("from AssetCategory where assetCategoryType = :type")
	List<AssetCategory> findByAssetCategoyType(@Param("type") AssetCategoryType type);
	
	@Query("from AssetCategory where parentAssetCategory = :parentId")
	Integer findParentIdSystemCode(@Param("parentId") Integer parentId);

}
