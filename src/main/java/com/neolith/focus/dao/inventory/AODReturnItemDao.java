package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.aodRetun.AODReturnItem;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AODReturnItemDao extends FocusDataTableRepository<AODReturnItem, Integer> {

    @Query("select returnItem from AODReturnItem as returnItem " +
            "left join returnItem.aodItem as aodItem " +
            "left join aodItem.site as site " +
            "left join aodItem.part as part " +
            "where site.id = :siteId and part.id = :partId")
    List<AODReturnItem> findItemBySiteAndItem(@Param("siteId") Integer siteId, @Param("partId") Integer partId);

    @Query("select returnItem from AODReturnItem as returnItem " +
            "left join returnItem.aodItem as aodItem " +
            "left join aodItem.site as site " +
            "left join aodItem.part as part " +
            "where site.id = :siteId")
    List<AODReturnItem> findItemBySite(@Param("siteId") Integer siteId);

    @Query("select returnItem from AODReturnItem as returnItem " +
            "left join returnItem.aodItem as aodItem " +
            "left join aodItem.site as site " +
            "left join aodItem.part as part " +
            "where part.id = :partId")
    List<AODReturnItem> findItemByPart(@Param("partId") Integer partId);


}
