package com.codex.ecam.result.inventory;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.inventory.bom.BOMGroupDTO;
import com.codex.ecam.model.inventory.bom.BOMGroup;
import com.codex.ecam.model.inventory.bom.BOMGroupPart;
import com.codex.ecam.result.BaseResult;

public class BOMGroupResult extends BaseResult<BOMGroup, BOMGroupDTO> {
	
	private List<BOMGroupPart> assets = new ArrayList<>();

    public BOMGroupResult(BOMGroup domain, BOMGroupDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

	public List<BOMGroupPart> getAssets() {
		return assets;
	}

	public void setAssets(List<BOMGroupPart> assets) {
		this.assets = assets;
	}
	
	public void addAsset(BOMGroupPart asset) {
		if (asset != null) {
			this.assets.add(asset);
		}
	}

}
