package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.workorder.WorkOrderNotification;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface WorkOrderNotificationDao extends FocusDataTableRepository<WorkOrderNotification, Integer> {


    @Modifying
    @Transactional
    @Query("delete from WorkOrderNotification where workOrder.id = :workOrderId")
    void deleteByWorkOrderId(@Param("workOrderId") Integer workOrderId);

}
