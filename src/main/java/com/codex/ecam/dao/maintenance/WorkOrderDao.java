package com.codex.ecam.dao.maintenance;

import java.util.Date;
import java.util.List;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.model.maintenance.workorder.WorkOrderFile;
import com.codex.ecam.model.maintenance.workorder.WorkOrderMeterReading;
import com.codex.ecam.repository.FocusDataTableRepository;


@Repository
public interface WorkOrderDao extends FocusDataTableRepository<WorkOrder, Integer>{

	WorkOrder findById(Integer id) throws Exception;

	@Query("from WorkOrderMeterReading where  id = :Id")
	WorkOrderMeterReading findWorkOrderMetereReadingsById(@Param("Id") Integer id);

	@Query("from WorkOrder where code = :code and (:id is null or id != :id)")
	List<WorkOrder> findDuplicateByCodeAndId(@Param("id") Integer id, @Param("code") String code);

	@Query("SELECT wo.startDate FROM WorkOrder as wo WHERE wo.startDate >= :fromDate AND wo.startDate <= :toDate GROUP BY wo.startDate")
	List<Date> findLastThirtyDayWorkOrders(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	@Query("SELECT wo.startDate FROM WorkOrder as wo WHERE wo.startDate >= :fromDate AND wo.startDate <= :toDate AND wo.business.id = :businessId GROUP BY wo.startDate")
	List<Date> findLastThirtyDayWorkOrdersByBusiness(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("businessId") Integer businessId);

	@Query("SELECT wo.startDate FROM WorkOrder as wo WHERE wo.startDate >= :fromDate AND wo.startDate <= :toDate AND wo.site.id = :siteId GROUP BY wo.startDate")
	List<Date> findLastThirtyDayWorkOrdersBySite(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("siteId") Integer siteId);

	@Query("SELECT COUNT(wo) FROM WorkOrder as wo WHERE wo.currentStatus.noteDate >= :fromDate AND wo.currentStatus.noteDate <= :toDate AND wo.currentStatus.workOrderStatus = :status")
	Integer findAllWorkOrdersOnDurationByStatus(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("status")WorkOrderStatus status);

	@Query("SELECT COUNT(wo) FROM WorkOrder as wo WHERE wo.currentStatus.noteDate >= :fromDate AND wo.currentStatus.noteDate <= :toDate AND wo.business.id = :businessId AND wo.currentStatus.workOrderStatus = :status")
	Integer findAllWorkOrdersOnDurationByStatusBusiness(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("status")WorkOrderStatus status, @Param("businessId")Integer businessId);

	@Query("SELECT COUNT(wo) FROM WorkOrder as wo WHERE wo.currentStatus.noteDate >= :fromDate AND wo.currentStatus.noteDate <= :toDate AND wo.site.id = :siteId AND wo.currentStatus.workOrderStatus = :status")
	Integer findAllWorkOrdersOnDurationByStatusSite(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("status")WorkOrderStatus status,  @Param("siteId")Integer siteId);

    @Query("from WorkOrderFile where id = :id")
    WorkOrderFile findByFileId(@Param("id") Integer id);

	@Query("from WorkOrder where id = ( select max(id) from WorkOrder where business.id = :businessId )")
	List<WorkOrder> findLastInsertWoByBusiness(@Param("businessId") Integer businessId);

}
