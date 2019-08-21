package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledMaintenanceAssetDao extends JpaRepository<ScheduledMaintenanceAsset, Integer> {

	List<ScheduledMaintenance> findByAsset(Asset asset);

}
