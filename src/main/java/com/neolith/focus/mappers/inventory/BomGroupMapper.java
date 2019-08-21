package com.neolith.focus.mappers.inventory;

import java.util.ArrayList;
import java.util.List;

import com.neolith.focus.constants.AssetCategoryType;
import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.dto.biz.part.PartDTO;
import com.neolith.focus.dto.inventory.bom.BOMGroupDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.mappers.asset.AssetMapper;
import com.neolith.focus.mappers.inventory.part.PartMapper;
import com.neolith.focus.model.inventory.bom.BOMGroup;
import com.neolith.focus.model.inventory.bom.BOMGroupPart;

public class BomGroupMapper extends GenericMapper<BOMGroup, BOMGroupDTO> {

    private static BomGroupMapper instance = null;

    private BomGroupMapper() {
    }

    public static BomGroupMapper getInstance() {
        if (instance == null) {
            instance = new BomGroupMapper();
        }
        return instance;
    }

    @Override
    public BOMGroupDTO domainToDto(BOMGroup domain) throws Exception {
        BOMGroupDTO dto = new BOMGroupDTO();
        dto.setId(domain.getId());
        dto.setIsDeleted(domain.getIsDeleted());
        dto.setVersion(domain.getVersion());

        dto.setName(domain.getName());
        if (domain.getBusiness() != null) {
            dto.setBusinessId(domain.getBusiness().getId());
        }
        updatePartsAndAssets(domain, dto);
        return dto;
    }

    @Override
    public void dtoToDomain(BOMGroupDTO dto, BOMGroup domain) throws Exception {
        domain.setId(dto.getId());
        domain.setName(dto.getName());
        domain.setIsDeleted(dto.getIsDeleted());
        domain.setVersion(dto.getVersion());
    }

    private void updatePartsAndAssets(BOMGroup domain, BOMGroupDTO dto) throws Exception {
        List<BOMGroupPart> groupParts = domain.getBomGroupParts();

        List<PartDTO> parts = new ArrayList<>();
        List<AssetDTO> assets = new ArrayList<>();

        if (groupParts != null && groupParts.size() > 0) {
            for (BOMGroupPart groupPart : groupParts) {
                if (groupPart.getPart().getAssetCategory().getAssetCategoryType().equals(AssetCategoryType.PARTS_AND_SUPPLIES)) {
                    parts.add(PartMapper.getInstance().domainToDto(groupPart.getPart()));
                } else {
                    assets.add(AssetMapper.getInstance().domainToDto(groupPart.getPart()));
                }
            }
        }

        dto.setAssets(assets);
        dto.setParts(parts);
    }
    
    private void updatePartsAndAssetsCount(BOMGroup domain, BOMGroupDTO dto) throws Exception {
    	
        List<BOMGroupPart> groupParts = domain.getBomGroupParts(); 
        Integer partCount = 0;
        Integer assetCount = 0;

        if (groupParts != null && groupParts.size() > 0) {
            for (BOMGroupPart groupPart : groupParts) {
                if (groupPart.getPart().getAssetCategory().getAssetCategoryType().equals(AssetCategoryType.PARTS_AND_SUPPLIES)) {
                	partCount++;
                } else {
                	assetCount++;
                }
            }
        }
        
        dto.setNoOfAssets(assetCount);
        dto.setNoOfParts(partCount);
        
    }

    @Override
    public BOMGroupDTO domainToDtoForDataTable(BOMGroup domain) throws Exception {
    	BOMGroupDTO dto = new BOMGroupDTO();
    	dto.setId(domain.getId());
    	dto.setName(domain.getName());
    	updatePartsAndAssetsCount(domain, dto);
    	
		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		updatePartsAndAssets(domain, dto);
        return dto;
    }

}
