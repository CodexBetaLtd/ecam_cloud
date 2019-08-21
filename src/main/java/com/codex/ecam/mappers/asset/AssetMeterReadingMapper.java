package com.codex.ecam.mappers.asset;

import com.codex.ecam.dto.asset.AssetMeterReadingDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.asset.AssetMeterReading;
import com.codex.ecam.model.asset.AssetMeterReadingValue;

public class AssetMeterReadingMapper extends GenericMapper<AssetMeterReading, AssetMeterReadingDTO> {

	private static AssetMeterReadingMapper instance = null;

	private AssetMeterReadingMapper() {
	}

	public static AssetMeterReadingMapper getInstance() {
		if (instance == null) {
			instance = new AssetMeterReadingMapper();
		}
		return instance;
	}

	@Override
	public AssetMeterReadingDTO domainToDto(AssetMeterReading domain) throws Exception {
		AssetMeterReadingDTO dto = new AssetMeterReadingDTO();
		dto.setMeterReadingId(domain.getId());
		dto.setMeterReadingName(domain.getMeterReadingName());
		dto.setMeterReadingUnitId(domain.getMeterReadingUnit().getId());
		dto.setMeterReadingUnitName(domain.getMeterReadingUnit().getName());
		dto.setMeterReadingAssetId(domain.getAsset().getId());
		dto.setMeterReadingDescription(domain.getDescription());
		dto.setMeterReadingAvgValue(domain.getAvgMeterReadingValue());

		setMeterReadingValues(domain, dto);

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setMeterReadingValues(AssetMeterReading domain, AssetMeterReadingDTO dto) throws Exception {
		if (domain.getAssetMeterReadingValues().size() > 0) {
			for (AssetMeterReadingValue assetMeterReadingValue : domain.getAssetMeterReadingValues()) {
				dto.setMeterReadingCurrentValue(assetMeterReadingValue.getMeterReadingValue());
				dto.setMeterReadingCurrentValueId(domain.getCurrentAssetMeterReadingValue().getId());
			}
		}
	}

	@Override
	public void dtoToDomain(AssetMeterReadingDTO dto, AssetMeterReading domain) throws Exception {
		domain.setId(dto.getMeterReadingId());
		domain.setMeterReadingName(dto.getMeterReadingName());
		domain.setDescription(dto.getMeterReadingDescription());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public AssetMeterReadingDTO domainToDtoForDataTable(AssetMeterReading domain) throws Exception {
		AssetMeterReadingDTO dto = new AssetMeterReadingDTO();
		dto.setMeterReadingId(domain.getId());
		dto.setMeterReadingName(domain.getMeterReadingName());
		dto.setMeterReadingUnitName(domain.getMeterReadingUnit().getName());
		return dto;
	}
}
