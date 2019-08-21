package com.codex.ecam.model.maintenance.workorder;

import javax.persistence.*;

import com.codex.ecam.listeners.workorder.WorkOrderAssetLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;

@Entity
@Table(name = "tbl_wo_asset")
@EntityListeners( WorkOrderAssetLogListener.class )
public class WorkOrderAsset extends BaseModel {

	private static final long serialVersionUID = 972208513274075010L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "workorder_asset_s")
    @SequenceGenerator(name = "workorder_asset_s", sequenceName = "workorder_asset_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "work_order_id")
    @ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private WorkOrder workOrder;

    @JoinColumn(name = "asset_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset asset;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
}
