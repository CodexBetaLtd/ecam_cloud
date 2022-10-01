package com.codex.ecam.mappers.asset;

import com.codex.ecam.dto.asset.AssetUserDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.asset.AssetUser;

public class AssetUserMapper extends GenericMapper<AssetUser, AssetUserDTO> {

    private static AssetUserMapper instance = null;

    private AssetUserMapper(){
	}
	
	public static AssetUserMapper getInstance() {
		if (instance == null) {
			instance = new AssetUserMapper();
		}
		return instance;
	}

	@Override
	public AssetUserDTO domainToDto(AssetUser domain) throws Exception {
		AssetUserDTO dto = new AssetUserDTO();
        dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        if (domain.getAsset() != null && domain.getAsset().getId() != null) {
            dto.setAssetId(domain.getAsset().getId());
        }
        if (domain.getUser() != null && domain.getUser().getId() != null) {
            dto.setUserId(domain.getUser().getId());
            dto.setName(domain.getUser().getFullName());
            dto.setEmail(domain.getUser().getEmailAddress());
        }
        return dto;
	}

	@Override
	public void dtoToDomain(AssetUserDTO dto, AssetUser domain) throws Exception {
		
	}

    @Override
    public AssetUserDTO domainToDtoForDataTable(AssetUser domain) throws Exception {
		AssetUserDTO dto = new AssetUserDTO();
		dto.setId(domain.getId());
        dto.setUserId(domain.getUser().getId());
        dto.setName(domain.getUser().getFullName());
        dto.setEmail(domain.getUser().getEmailAddress());
        return dto;
    }
}
