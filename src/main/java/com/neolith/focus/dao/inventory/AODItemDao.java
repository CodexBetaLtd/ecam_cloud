package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.aod.AODItem;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AODItemDao extends FocusDataTableRepository<AODItem, Integer> {

    @Query("select item from AODItem as item " +
            "left join item.site as site " +
            "left join item.part as part " +
            "where site.id = :siteId and part.id = :partId")
    List<AODItem> findItemBySiteAndItem(@Param("siteId") Integer siteId, @Param("partId") Integer partId);


    @Query("select aodItem from AODItem as aodItem " +
            "left join aodItem.site as site " +
            "left join aodItem.part as part " +
            "where site.id = :siteId")
    List<AODItem> findItemBySite(@Param("siteId") Integer siteId);


    @Query("select aodItem from AODItem as aodItem " +
            "left join aodItem.site as site " +
            "left join aodItem.part as part " +
            "where part.id = :partId")
    List<AODItem> findItemByPart(@Param("partId") Integer partId);


}
