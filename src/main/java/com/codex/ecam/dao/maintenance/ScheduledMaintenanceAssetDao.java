package com.codex.ecam.dao.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceAsset;

import java.util.List;

@Repository
public interface ScheduledMaintenanceAssetDao extends JpaRepository<ScheduledMaintenanceAsset, Integer> {

	List<ScheduledMaintenance> findByAsset(Asset asset);

}
