package com.neolith.focus.model.inventory.bom;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.asset.AssetConsumingReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_bom_group_part")
public class BOMGroupPart extends BaseModel {

    private static final long serialVersionUID = -9072613193777756954L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "bom_group_part_s")
    @SequenceGenerator(name = "bom_group_part_s", sequenceName = "bom_group_part_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "part_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset part;

    @JoinColumn(name = "bom_group_id")
    @ManyToOne(targetEntity = BOMGroup.class, fetch = FetchType.LAZY)
    private BOMGroup bomGroup;

    @Column(name = "max_consumption")
    private Double maxConsumption;
    
    @OneToMany(mappedBy = "bomGroupAsset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<AssetConsumingReference> assetConsumingReferences;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Asset getPart() {
        return part;
    }

    public void setPart(Asset part) {
        this.part = part;
    }

    public BOMGroup getBomGroup() {
        return bomGroup;
    }

    public void setBomGroup(BOMGroup bomGroup) {
        this.bomGroup = bomGroup;
    }

    public Double getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Double maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

	public List<AssetConsumingReference> getAssetConsumingReferences() {
		return assetConsumingReferences;
	}

	public void setAssetConsumingReferences(List<AssetConsumingReference> assetConsumingReferences) {
		this.assetConsumingReferences = assetConsumingReferences;
	}

}
