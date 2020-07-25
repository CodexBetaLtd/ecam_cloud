package com.codex.ecam.dao.asset;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.model.asset.*;
import com.codex.ecam.repository.FocusDataTableRepository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AssetDao extends FocusDataTableRepository<Asset, Integer> {

	@Query("from Asset where assetCategory.assetCategoryType = :type")
	List<Asset> findByAssetCategoyType(@Param("type") AssetCategoryType type);
	
	@Query("from Asset where assetCategory.assetCategoryType = :type and parentAsset = :id")
	List<Asset> findWarehouseListByTypeAndParentWarehouseId(@Param("type") AssetCategoryType type,@Param("id") Integer id);

	/*
    @Modifying
    @Transactional
    @Query("from Asset where assetCategory.assetCategoryType = :type and parentAsset.id =: businessId")
    List<Asset> findAssetByBusinessAndCategoryType(@Param("businessId") Integer businessId, @Param("type") AssetCategoryType type);
	 */


	@Query("select distinct a from Asset a where a.assetCategory.assetCategoryType = :type and a.id != :assetList")
	List<Asset> findByExcludingAssetList(@Param("type") AssetCategoryType type,@Param("assetList") List<Integer> assetList);

	/*    @Query("from Asset where assetCategory.assetCategoryType = :type and business.id = :businessId")
    List<Asset> findAssetByBusinessAndType(@Param("businessId") Integer id, @Param("type") AssetCategoryType type);

    @Query("from AssetBusiness where business.id = :businessId")
    List<Asset> findAssetByBusinessId(@Param("businessId") Integer id);*/

	@Query("from Asset where assetCategory.assetCategoryType = :type and id=:assetId")
	List<Asset> findAssetByBusinessAndTypeandId(@Param("assetId") Integer id, @Param("type") AssetCategoryType type);

	@Query("from AssetMeterReading where  id = :assetId")
	AssetMeterReading findAssetMetereReadingsById(@Param("assetId") Integer id);

	@Query("from Asset where  name = :assetName")
	Asset findAssetMetereReadingsByAssetName(@Param("assetName") String asseName);

	@Query(" from AssetMeterReading where  asset.id = :assetId and createdDate=(select max(createdDate) from AssetMeterReading where  asset.id = :assetId)")
	List<AssetMeterReading> findLatestAssetMetereReadingsByAsset(@Param("assetId") Integer id);

	@Query(" from AssetMeterReadingValue where  assetMeterReading.id = :assetMeterReadingId and createdDate=(select max(createdDate) from AssetMeterReadingValue where  assetMeterReading.id = :assetMeterReadingId)")
	List<AssetMeterReadingValue> findAssetMeterReadingvalueById(@Param("assetMeterReadingId") Integer id);

	@Query(" from AssetMeterReadingValue where  assetMeterReading.id = :assetMeterReadingId and createdDate=(select max(createdDate) from AssetMeterReadingValue where  assetMeterReading.id = :assetMeterReadingId)")
	AssetMeterReadingValue findAssetMeterReadingvalueByMeterReadingId(@Param("assetMeterReadingId") Integer id);

	@Query("from AssetMeterReading where  id = :assetId")
	List<AssetMeterReading> findAssetMetereReadingsHistoryById(@Param("assetId") Integer id);


    @Query("from AssetConsumingReference where  asset.id = :assetId")
    List<AssetConsumingReference> findAssetConsumingPartReferenceByAssetId(@Param("assetId") Integer assetId);

	@Query("from AssetEventTypeAsset where  id = :eventTypeId")
	AssetEventTypeAsset findAssetEvent(@Param("eventTypeId") Integer id);

	@Modifying
	@Transactional
	@Query("delete from AssetMeterReadingValue where  assetMeterReading.id = :assetId")
	void deleteByMeterReadingValueMeterReadingId(@Param("assetId") Integer workOrderId);

	@Modifying
	@Transactional
	@Query("delete from AssetMeterReading where  id = :meterReadingId")
	void deleteByMeterReadingAssetId(@Param("meterReadingId") Integer workOrderId);

	@Modifying
	@Transactional
	@Query("delete from WorkOrderMeterReading where  assetMeterReading.id = :meterReadingId")
	void deleteByMeterReadingOnworkOderId(@Param("meterReadingId") Integer workOrderId);

	@Query("from Asset where code = :code and (:id is null or id != :id)")
	List<Asset> findDuplicateByCodeAndId(@Param("id") Integer id, @Param("code") String code);

	@Query("from Asset where name = :name and (:id is null or id != :id)")
	List<Asset> findDuplicateByNameAndId(@Param("id") Integer id, @Param("name") String name);
	
    @Query("from AssetFile where id = :id")
    AssetFile findByFileId(@Param("id") Integer id);

    @Query("SELECT a.imageLocation FROM Asset a WHERE a.id = :id")
    String getAssetImageLocation(@Param("id") Integer id);
    
    @Query("SELECT a.assetUrl FROM Asset a WHERE a.id = :id")
    String getAssetQRLocation(@Param("id") Integer id);

    @Query("from Asset where assetCategory.assetCategoryType = :type")
	List<Asset> findWarehouseListByType(@Param("type") AssetCategoryType type);
    
    @Query("select count(asset.assetCategory.id) as assetCount , asset from Asset asset where asset.business.id=:id group by asset.assetCategory.id ")
    List<Object> getAssetCountByCategory(@Param("id") Integer id);
    
    @Query("select count(asset.assetCategory.id) as assetCount , asset from Asset asset where asset.business.id=:businessId and asset.model.id=:modelId group by asset.assetCategory.id ")
    List<Object> getAssetCountByCategoryAndModel(@Param("businessId") Integer businessId,@Param("modelId") Integer modelId);
    
    @Query("select count(asset.assetCategory.id) as assetCount , asset from Asset asset where asset.business.id=:businessId and asset.site.id=:locationId group by asset.assetCategory.id ")
    List<Object> getAssetCountByCategoryAndLocation(@Param("businessId") Integer businessId,@Param("locationId") Integer locationId);
    
    @Query("select count(asset.assetCategory.id) as assetCount , asset from Asset asset where asset.business.id=:businessId and asset.site.id=:locationId and asset.model.id=:modelId group by asset.assetCategory.id ")
    List<Object> getAssetCountByCategoryAndLocationAndModel(@Param("businessId") Integer businessId,@Param("locationId") Integer locationId,@Param("modelId") Integer modelId);
}
