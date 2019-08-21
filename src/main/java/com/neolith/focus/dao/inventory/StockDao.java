package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.inventory.stock.Stock;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StockDao extends FocusDataTableRepository<Stock, Integer> {


    @Query("select stock from Stock stock left join stock.site as site left join stock.part as part  where site.id = :siteId and part.id = :itemId ")
    List<Stock> findStock(@Param("siteId") Integer siteId, @Param("itemId") Integer itemId);

    @Query("select stock from Stock stock left join stock.site as site where site.id = :siteId")
    List<Stock> findStockBySite(@Param("siteId") Integer siteId);

    @Query("select stock from Stock stock left join stock.part as part  where part.id = :partId")
    List<Stock> findStockByPart(@Param("partId") Integer partId);
    
    @Query("select stock from Stock stock left join stock.part as part where part.id = :partId and stock.currentQuantity>0 and stock.date=(select min(stock2.date) from Stock  as stock2 left join stock2.part as part1 where part1.id = :partId and stock2.currentQuantity>0)")
    Stock findStockByFIFO(@Param("partId") Integer partId);


    /*
    *
    * Todo: need to change this
    *
    * */
    @Query("select stock from Stock stock " +
            "left join stock.site as site " +
            "left join stock.part as part  " +
            "where site.id = :siteId and part.id = :partId and stock.createdDate=(select max(createdDate) from Stock where site.id = :siteId and part.id = :partId )")
    Stock findLastStockByPart(@Param("siteId") Integer siteId, @Param("partId") Integer partId);


    @Query("select distinct part from Stock stock left join stock.part as part")
    List<Asset> findStockItems();


    @Query(value = "SELECT distinct(part.id),part.name FROM Stock stock LEFT JOIN stock.part as part " +
            "where part.id in (select DISTINCT id from Asset)")
    List<Stock> findStockGroupByPart();


    @Query("select stock from Stock stock where stock.id =(select max(lst.id) from Stock lst) ")
    Stock findLastDomain();


    @Query("select stock from Stock stock " +
            "left join stock.part as part " +
            "where part.id = :partId and stock.currentQuantity>0 order by stock.createdDate asc ")
    List<Stock> findNonEmptyStockByItemId(@Param("partId") Integer partId);

    @Query("select stock from Stock stock " +
            "left join stock.part as part " +
            "left join stock.warehouse as warehouse " +
            "where part.id = :partId and warehouse.id = :warehouseId and stock.currentQuantity>0 order by stock.createdDate asc ")
    List<Stock> findNonEmptyStockByItemIdAndWarehouse(@Param("partId") Integer partId, @Param("warehouseId") Integer warehouseId);


    @Query("select stock from Stock stock " +
            "left join stock.part as part " +
            "where part.id = :partId and stock.createdDate > :createdDate order by stock.createdDate asc ")
    List<Stock> findStockListByItem(@Param("partId") Integer partId, @Param("createdDate") Date createdDate);


}
