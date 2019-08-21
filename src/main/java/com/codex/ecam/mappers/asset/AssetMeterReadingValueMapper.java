package com.codex.ecam.mappers.asset;

import java.util.Date;

import com.codex.ecam.dto.asset.AssetMeterReadingValueDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.asset.AssetMeterReadingValue;

public class AssetMeterReadingValueMapper extends GenericMapper<AssetMeterReadingValue, AssetMeterReadingValueDTO> {

	private static AssetMeterReadingValueMapper instance = null;

	private AssetMeterReadingValueMapper() {
	}

	public static AssetMeterReadingValueMapper getInstance() {
		if (instance == null) {
			instance = new AssetMeterReadingValueMapper();
		}
		return instance;
	}

	@Override
	public AssetMeterReadingValueDTO domainToDto(AssetMeterReadingValue domain) throws Exception {
		AssetMeterReadingValueDTO dto = new AssetMeterReadingValueDTO();
		dto.setAssetMeterReadingValueId(domain.getId());
		dto.setAssetMeterReadingValue(domain.getMeterReadingValue());
		dto.setAssetMeterReadingValueAddedDate(domain.getAddedDate().getTime());
		if (domain.getAssetMeterReading() != null) {
			dto.setAssetMeterReadingName(domain.getAssetMeterReading().getMeterReadingName());
			dto.setAssetMeterReadingId(domain.getAssetMeterReading().getId());
			dto.setUnit(domain.getAssetMeterReading().getMeterReadingUnit().getSymbol());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(AssetMeterReadingValueDTO dto, AssetMeterReadingValue domain) throws Exception {
		domain.setId(dto.getAssetMeterReadingValueId());
		domain.setMeterReadingValue(dto.getAssetMeterReadingValue());
		domain.setAddedDate(new Date(dto.getAssetMeterReadingValueAddedDate()));

		setCommanDomainFields(dto, domain);
	}

	@Override
	public AssetMeterReadingValueDTO domainToDtoForDataTable(AssetMeterReadingValue domain) throws Exception {
		AssetMeterReadingValueDTO dto = new AssetMeterReadingValueDTO();
		dto.setAssetMeterReadingValue(domain.getMeterReadingValue());
		dto.setAssetMeterReadingValueAddedDate(domain.getAddedDate().getTime());
		if (domain.getAssetMeterReading() != null) {
			dto.setAssetMeterReadingName(domain.getAssetMeterReading().getMeterReadingName());
			dto.setUnit(domain.getAssetMeterReading().getMeterReadingUnit().getSymbol());
		}
		return dto;
	}
}
