package com.codex.ecam.dao.asset;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.model.asset.AssetCategory;
import com.codex.ecam.repository.FocusDataTableRepository;

import java.util.List;

@Repository
public interface AssetCategoryDao extends FocusDataTableRepository<AssetCategory, Integer>{

	AssetCategory findById(Integer id);

	@Query("from AssetCategory where assetCategoryType = :type")
	List<AssetCategory> findByAssetCategoyType(@Param("type") AssetCategoryType type);
	
	@Query("from AssetCategory where name = :name")
	AssetCategory findByAssetCategoryByName(@Param("name") String name);
	
	@Query("from AssetCategory where assetCategoryType = :type and business.id=:id ")
	List<AssetCategory> findByAssetCategoyTypeByBusiness(@Param("type") AssetCategoryType type,@Param("id") Integer id);
	
	@Query("from AssetCategory where parentAssetCategory = :parentId")
	Integer findParentIdSystemCode(@Param("parentId") Integer parentId);

}
