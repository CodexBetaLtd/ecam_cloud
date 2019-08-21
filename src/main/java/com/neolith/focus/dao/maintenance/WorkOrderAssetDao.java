package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.workorder.WorkOrderAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WorkOrderAssetDao extends JpaRepository<WorkOrderAsset, Integer> {

    @Modifying
    @Transactional
    @Query("delete from WorkOrderAsset where workOrder.id = :workOrderId")
    void deleteByWorkOrderId(@Param("workOrderId") Integer workOrderId);

}
