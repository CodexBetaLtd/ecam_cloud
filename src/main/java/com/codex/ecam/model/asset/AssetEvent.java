package com.codex.ecam.model.asset;

import javax.persistence.*;

import com.codex.ecam.listeners.ScheduleTriggerEntityListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;

import java.util.Date;

@Entity
@Table(name="tbl_asset_event")
@EntityListeners(ScheduleTriggerEntityListener.class)
public class AssetEvent extends BaseModel {

	private static final long serialVersionUID = 6274638401650831218L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="asset_event_s")
	@SequenceGenerator(name="asset_event_s", sequenceName="asset_event_s", allocationSize=1)
	@Column(name="id")
    private Integer id;

    @JoinColumn(name = "work_order_id")
    @ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
    private WorkOrder workOrder;    

    @JoinColumn(name = "asset_event_type_asset_id")
    @ManyToOne(targetEntity = AssetEventTypeAsset.class, fetch = FetchType.LAZY)
    private AssetEventTypeAsset assetEventTypeAsset;

    @Column(name = "occured_date")
    private Date occuredDate;

    @Column(name = "additional_description")
    private String additionalDescription;

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

	public String getAdditionalDescription() {
		return additionalDescription;
	}

	public void setAdditionalDescription(String additionalDescription) {
		this.additionalDescription = additionalDescription;
	}

	public AssetEventTypeAsset getAssetEventTypeAsset() {
		return assetEventTypeAsset;
	}

	public void setAssetEventTypeAsset(AssetEventTypeAsset assetEventTypeAsset) {
		this.assetEventTypeAsset = assetEventTypeAsset;
    }

    public Date getOccuredDate() {
        return occuredDate;
    }

    public void setOccuredDate(Date occuredDate) {
        this.occuredDate = occuredDate;
    }

}
