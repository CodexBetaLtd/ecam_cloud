package com.codex.ecam.mappers.asset;

import com.codex.ecam.dto.asset.WarrantyDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.asset.Warranty;
import com.codex.ecam.util.DateUtil;

public class WarrantyMapper extends GenericMapper<Warranty, WarrantyDTO> {

	private static WarrantyMapper instance = null;

	private WarrantyMapper(){
	}

	public static WarrantyMapper getInstance() {
		if (instance == null) {
			instance = new WarrantyMapper();
		}
		return instance;
	}

	@Override
	public WarrantyDTO domainToDto(Warranty domain) throws Exception {
		final WarrantyDTO dto = new WarrantyDTO();

		dto.setWarrantyId(domain.getId());
		if ( domain.getAsset() != null ) {
			dto.setWarrantyAssetId(domain.getAsset().getId());
			dto.setWarrantyAssetName(domain.getAsset().getName());
		}

		if ( domain.getMeterReadingUnit() != null ) {
			dto.setWarrantyMeterReadingUnitId(domain.getMeterReadingUnit().getId());
			dto.setWarrantyMeterReadingUnitName(domain.getMeterReadingUnit().getName() + "(" + domain.getMeterReadingUnit().getSymbol() + ")");
		}

		if ( domain.getProvider() != null ) {
			dto.setWarrantyProviderId(domain.getProvider().getId());
			dto.setWarrantyProviderName(domain.getProvider().getName());
		}

		dto.setWarrantyMeterReadingValueLimit(domain.getMeterReadingValueLimit());
		dto.setWarrantyCertificateNo(domain.getCertificateNo());
		dto.setWarrantyExpiryDate(domain.getExpiryDate());
		dto.setWarrantyDescription(domain.getDescription());
		dto.setWarrantyType(domain.getWarrantyType());
		dto.setWarrantyUsageTermType(domain.getWarrantyUsageTermType());
		dto.setVersion(domain.getVersion());
		dto.setIsDeleted(domain.getIsDeleted());

		return dto;
	}

	@Override
	public void dtoToDomain(WarrantyDTO dto, Warranty domain) throws Exception {
		domain.setId(dto.getWarrantyId());
		domain.setMeterReadingValueLimit(dto.getWarrantyMeterReadingValueLimit());
		domain.setCertificateNo(dto.getWarrantyCertificateNo());
		domain.setExpiryDate(DateUtil.getDateObj(dto.getWarrantyExpiryDate()));
		domain.setDescription(dto.getWarrantyDescription());
		domain.setWarrantyType(dto.getWarrantyType());
		domain.setWarrantyUsageTermType(dto.getWarrantyUsageTermType());
		domain.setVersion(dto.getVersion());
		domain.setIsDeleted(dto.getIsDeleted());
	}

	@Override
	public WarrantyDTO domainToDtoForDataTable(Warranty domain) throws Exception {
		final WarrantyDTO dto = new WarrantyDTO();
		dto.setWarrantyId(domain.getId());
		dto.setWarrantyAssetName(domain.getAsset().getName());

		return dto;
	}
}
