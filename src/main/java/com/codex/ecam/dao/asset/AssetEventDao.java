package com.codex.ecam.dao.asset;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.asset.AssetEvent;
import com.codex.ecam.repository.FocusDataTableRepository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AssetEventDao extends FocusDataTableRepository<AssetEvent, Integer> {

    @Query("select assetevent from AssetEvent  assetevent,AssetEventTypeAsset assetevntasset where assetevntasset.asset.id= :assetId")
    List<AssetEvent> findByAssetId(@Param("assetId") Integer id);

    @Query("from AssetEvent where assetEventTypeAsset.id= :asseteventId and createdDate=(select max(createdDate) from AssetEvent where  assetEventTypeAsset.id= :asseteventId)")
    AssetEvent findLatestAssetEventByAssetEventTypeAssetId(@Param("asseteventId") Integer id);

    @Query("select ae from AssetEventTypeAsset aet inner join aet.assetEvents ae  where aet.asset.id=:assetId order by ae.createdDate DESC")
    List<AssetEvent> findAssetEventHistory(@Param("assetId") Integer id);

    @Modifying
    @Transactional
    @Query("delete from AssetEvent where  id = :Id")
    void deleteByEventId(@Param("Id") Integer assetId);
}
