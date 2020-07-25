package com.codex.ecam.mappers.asset;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.AssetFileDTO;
import com.codex.ecam.dto.asset.LocationDTO;
import com.codex.ecam.dto.asset.SparePartDTO;
import com.codex.ecam.dto.asset.WarrantyDTO;
import com.codex.ecam.dto.inventory.AssetConsumingReferenceDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.mappers.inventory.AssetConsumingReferenceMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetConsumingReference;
import com.codex.ecam.model.asset.AssetEventTypeAsset;
import com.codex.ecam.model.asset.AssetFile;
import com.codex.ecam.model.asset.AssetMeterReading;
import com.codex.ecam.model.asset.SparePart;
import com.codex.ecam.model.asset.Warranty;

public class AssetMapper extends GenericMapper<Asset, AssetDTO> {

	private static AssetMapper instance = null;

	private AssetMapper(){
	}

	public static AssetMapper getInstance() {
		if (instance == null) {
			instance = new AssetMapper();
		}
		return instance;
	}

	@Override
	public AssetDTO domainToDto(Asset domain) throws Exception {
		AssetDTO dto = new AssetDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		dto.setIsOnline(domain.getIsOnline());
dto.setAssetUrl(domain.getAssetUrl());
		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}

		if (domain.getAssetCategory() != null) {
			dto.setAssetCategoryId(domain.getAssetCategory().getId());
			dto.setAssetCategoryType(domain.getAssetCategory().getAssetCategoryType());
			dto.setAssetCategoryName(domain.getAssetCategory().getName());
		}

		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
		}

		if (domain.getParentAsset() != null){
			dto.setParentAssetId(domain.getParentAsset().getId());
			dto.setParentAssetName(domain.getParentAsset().getName());
		}

		if (domain.getModel() != null ){
			dto.setModel(domain.getModel().getId());
			dto.setModelName(domain.getModel().getModelName());
		}

		if (domain.getBrand() != null){
			dto.setBrand(domain.getBrand().getId());
			dto.setBrandName(domain.getBrand().getBrandName());
		}

		if ( domain.getCustomer() != null ) {
			dto.setCustomerId(domain.getCustomer().getId());
			dto.setCustomerName(domain.getCustomer().getName());
		}
		
		if (domain.getChildCount() != null) {
			dto.setChildCount(domain.getChildCount());
		}
		
		setMeterReadings(domain, dto);
		setLocation(dto, domain);
		setAssetEventTypeAssets(domain, dto);
		setAssetConsumingReferences(dto, domain);
		setPartConsumingReferences(dto, domain);
		setWarranties(dto, domain);
		setAssetFile( domain,dto);
		setAssetSparePart( domain,dto);

		dto.setNotes(domain.getNotes());

		dto.setAddress(domain.getAddress());
		dto.setCity(domain.getCity());
		dto.setProvince(domain.getProvince());
		setLocationString(dto, domain);
		dto.setPostalCode(domain.getPostalcode());
		
		if (domain.getCountry() != null) {
			dto.setCountryId(domain.getCountry().getId());
		}

		dto.setSerialNo(domain.getSerialNo());
		dto.setIsDeleted(domain.getIsDeleted());
		dto.setVersion(domain.getVersion()); 		
		dto.setImageLocation(domain.getImageLocation());
		dto.setChildCount(domain.getChildCount());

		return dto;
	}

	private void setPartConsumingReferences(AssetDTO dto, Asset domain) throws Exception {
		if ((domain.getPartConsumingReferences() != null) && (domain.getPartConsumingReferences().size() > 0)) {
			List<AssetConsumingReferenceDTO> assetConsumingDTOs = new ArrayList<>();
			for ( AssetConsumingReference assetConsumePref : domain.getPartConsumingReferences()) {
				AssetConsumingReferenceDTO assetConsumingDTO = AssetConsumingReferenceMapper.getInstance().domainToDto(assetConsumePref);
				assetConsumingDTOs.add(assetConsumingDTO);
			}
			dto.setPartConsumeRefs(assetConsumingDTOs);
		}
	}
	private void setLocation(AssetDTO dto, Asset domain){
		 LocationDTO locationDTO=new LocationDTO();
			
		 if(domain.getLatitude() !=null){
			 locationDTO.setLatitude(domain.getLatitude());
		 }
		if(domain.getLongitude() !=null){
			locationDTO.setLongitude(domain.getLongitude());
		}
		
		dto.setLocationDTO(locationDTO);
	}

	private void setWarranties(AssetDTO dto, Asset domain) throws Exception {
		if ((domain.getWarranties() != null) && (domain.getWarranties().size() > 0)) {
			List<WarrantyDTO> warrantyDTOs = new ArrayList<>();
			for (Warranty warranty : domain.getWarranties()) {
				WarrantyDTO warrantyDTO = WarrantyMapper.getInstance().domainToDto(warranty);
				warrantyDTOs.add(warrantyDTO);
			}
			dto.setWarranties(warrantyDTOs);
		}
	}

	private void setAssetConsumingReferences(AssetDTO dto, Asset domain) throws Exception {
		if ((domain.getAssetConsumingReferences() != null) && (domain.getAssetConsumingReferences().size() > 0)) {
			List<AssetConsumingReferenceDTO> assetConsumingDTOs = new ArrayList<>();
			for ( AssetConsumingReference assetConsumePref : domain.getAssetConsumingReferences()) {
				AssetConsumingReferenceDTO assetConsumingDTO = AssetConsumingReferenceMapper.getInstance().domainToDto(assetConsumePref);
				assetConsumingDTOs.add(assetConsumingDTO);
			}
			dto.setAssetConsumeRefs(assetConsumingDTOs);
		}
	}

	private void setLocationString(AssetDTO dto, Asset domain) {
		StringBuilder strBuilder = new StringBuilder("");

		if ((domain.getAddress() != null) && !domain.getAddress().isEmpty()) {
			strBuilder.append(domain.getAddress());
		}

		if ((domain.getCity() != null) && !domain.getCity().isEmpty()) {
			if (!strBuilder.toString().isEmpty()) {
				strBuilder.append(", ");
			}
			strBuilder.append(domain.getCity());
		}

		if ((domain.getProvince() != null) && !domain.getProvince().isEmpty()) {
			if (!strBuilder.toString().isEmpty()) {
				strBuilder.append(", ");
			}
			strBuilder.append(domain.getProvince() + ".");
		}

		dto.setLocation(strBuilder.toString());
	}

	private void setMeterReadings(Asset domain, AssetDTO dto) throws Exception {
		if (domain.getAssetMeterReadings().size() > 0) {
			for (AssetMeterReading assetMeterReading : domain.getAssetMeterReadings()) {
				dto.getAssetMeterReadings().add(AssetMeterReadingMapper.getInstance().domainToDto(assetMeterReading));
			}
		}
	}

	private void setAssetEventTypeAssets(Asset domain, AssetDTO dto) throws Exception {
		if (domain.getAssetEventTypeAssets().size() > 0) {
			for (AssetEventTypeAsset assetEventTypeAsset : domain.getAssetEventTypeAssets()) {
				dto.getAssetEventTypeAssets().add(AssetEventTypeAssetMapper.getInstance().domainToDto(assetEventTypeAsset));
				
			}
		}
	}
	private void setAssetFile(Asset domain, AssetDTO dto) throws Exception {
		if (domain.getAssetFiles().size() > 0) {
			for (AssetFile assetFile :domain.getAssetFiles()) {
				AssetFileDTO assetFileDTO=new AssetFileDTO();
				assetFileDTO.setId(assetFile.getId());
				assetFileDTO.setItemDescription(assetFile.getItemDescription());
				assetFileDTO.setVersion(assetFile.getVersion());
				assetFileDTO.setFileLocation(assetFile.getFileLocation());
				assetFileDTO.setFileType(assetFile.getFileType());
				assetFileDTO.setFileDate(assetFile.getFileDate());
				dto.getAssetFileDTOs().add(assetFileDTO);

			}
		}
	}
	
	private void setAssetSparePart(Asset domain, AssetDTO dto) throws Exception {
		if (domain.getSpareParts().size() > 0) {
			for (SparePart sparePart :domain.getSpareParts()) {
				SparePartDTO sparePartDTO=new SparePartDTO();
				sparePartDTO.setId(sparePart.getId());
				sparePartDTO.setQuantity(sparePart.getQuantity());
				sparePartDTO.setVersion(sparePart.getVersion());
				sparePartDTO.setDescription(sparePart.getDescription());
				if(sparePart.getAsset()!=null){
					sparePartDTO.setPartCode(sparePart.getAsset().getCode());
					sparePartDTO.setPartId(sparePart.getAsset().getId());
				}

				dto.getSparePartDTOs().add(sparePartDTO);

			}
		}
	}

	@Override
	public void dtoToDomain(AssetDTO dto, Asset domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setCode(dto.getCode());
		domain.setDescription(dto.getDescription());
		domain.setIsOnline(dto.getIsOnline());
		domain.setNotes(dto.getNotes());
		domain.setAddress(dto.getAddress());
		domain.setCity(dto.getCity());
		domain.setProvince(dto.getProvince());
		domain.setPostalcode(dto.getPostalCode());
		domain.setSerialNo(dto.getSerialNo());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());
		domain.setImageLocation(dto.getImageLocation());
	}

	@Override
	public AssetDTO domainToDtoForDataTable(Asset domain) throws Exception {
		AssetDTO dto = new AssetDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		setLocationString(dto, domain);
		dto.setAssetCategoryName(domain.getAssetCategory().getName());

		if (domain.getBusiness() != null) { 
			dto.setBusinessName(domain.getBusiness().getName());
		}
		
		if ( domain.getCustomer() != null ) { 
			dto.setCustomerName(domain.getCustomer().getName());
		}   
		if (domain.getChildCount() != null) {
			dto.setChildCount(domain.getChildCount());
		}
		return dto;
	}

}
