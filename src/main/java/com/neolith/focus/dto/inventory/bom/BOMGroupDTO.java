package com.neolith.focus.dto.inventory.bom;

import com.neolith.focus.dto.BaseDTO;
import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.dto.biz.part.PartDTO;

import java.util.ArrayList;
import java.util.List;

public class BOMGroupDTO extends BaseDTO {

    private Integer id;
    private Integer businessId;
    private Integer noOfAssets;
    private Integer noOfParts;
    
    private String name;
    private String businessName;

    private List<PartDTO> parts = new ArrayList<>();
    private List<AssetDTO> assets = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PartDTO> getParts() {
        return parts;
    }

    public void setParts(List<PartDTO> parts) {
        this.parts = parts;
    }

    public List<AssetDTO> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetDTO> assets) {
        this.assets = assets;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getNoOfAssets() {
        return noOfAssets;
    }

	public void setNoOfAssets(Integer noOfAssets) {
		this.noOfAssets = noOfAssets;
	}

    public Integer getNoOfParts() {
        return noOfParts;
    }

	public void setNoOfParts(Integer noOfParts) {
		this.noOfParts = noOfParts;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
