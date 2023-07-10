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

		final AssetDTO dto = new AssetDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		dto.setIsOnline(domain.getIsOnline());
		dto.setAssetUrl(domain.getAssetUrl());
		dto.setNotes(domain.getNotes());
		dto.setAddress(domain.getAddress());
		dto.setCity(domain.getCity());
		dto.setProvince(domain.getProvince());
		setLocationString(dto, domain);
		dto.setPostalCode(domain.getPostalcode());
		dto.setSerialNo(domain.getSerialNo());
		dto.setIsDeleted(domain.getIsDeleted());
		dto.setVersion(domain.getVersion());
		dto.setImageLocation(domain.getImageLocation());
		dto.setChildCount(domain.getChildCount());

		dto.setSize(domain.getSize());
		dto.setQuantity(domain.getQuantity());
		dto.setUnitCost(domain.getUnitCost());
		dto.setTotalCost(domain.getTotalCost());
		dto.setUsefulLife(domain.getUsefulLife());
		dto.setYearlyDepreciationValue(domain.getYearlyDepreciationValue());
		dto.setYearEndNetBookValue(domain.getYearEndNetBookValue());
		dto.setAccumulatedDepreciation(domain.getAccumulatedDepreciation());
		dto.setDateOfPurchase(domain.getDateOfPurchase());
		dto.setDepartment(domain.getDepartment());
		dto.setAssetClass(domain.getAssetClass());
		dto.setRemark(domain.getRemarks());
		dto.setAddedDate(domain.getAddedDate());


		setBusiness(domain, dto);

		setAssetCategory(domain, dto);

		setSite(domain, dto);
		setSubSitr(domain, dto);
		setParentAsset(domain, dto);
		setModel(domain, dto);
		setBrand(domain, dto);
		setCustomer(domain, dto);
		setChildCount(domain, dto);
		setMeterReadings(domain, dto);
		setLocation(dto, domain);
		setAssetEventTypeAssets(domain, dto);
		setAssetConsumingReferences(dto, domain);
		setPartConsumingReferences(dto, domain);
		setWarranties(dto, domain);
		setAssetFile( domain,dto);
		setAssetSparePart( domain,dto);
		setAssetCountry(domain, dto);

		return dto;
	}

	private void setBusiness(Asset domain, final AssetDTO dto) {
		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}
	}

	private void setAssetCategory(Asset domain, final AssetDTO dto) {
		if (domain.getAssetCategory() != null) {
			dto.setAssetCategoryId(domain.getAssetCategory().getId());
			dto.setAssetCategoryType(domain.getAssetCategory().getAssetCategoryType());
			dto.setAssetCategoryName(domain.getAssetCategory().getName());
		}
	}

	private void setSite(Asset domain, final AssetDTO dto) {
		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
			dto.setSiteName(domain.getSite().getName());
		}
	}

	private void setSubSitr(Asset domain, final AssetDTO dto) {
		if (domain.getSubSite() != null) {
			dto.setSubSiteId(domain.getSubSite().getId());
			dto.setSubSiteName(domain.getSubSite().getName());
		}
	}

	private void setParentAsset(Asset domain, final AssetDTO dto) {
		if (domain.getParentAsset() != null){
			dto.setParentAssetId(domain.getParentAsset().getId());
			dto.setParentAssetName(domain.getParentAsset().getName());
		}
	}

	private void setModel(Asset domain, final AssetDTO dto) {
		if (domain.getModel() != null ){
			dto.setModel(domain.getModel().getId());
			dto.setModelName(domain.getModel().getModelName());
		}
	}

	private void setBrand(Asset domain, final AssetDTO dto) {
		if (domain.getBrand() != null){
			dto.setBrand(domain.getBrand().getId());
			dto.setBrandName(domain.getBrand().getBrandName());
		}
	}

	private void setCustomer(Asset domain, final AssetDTO dto) {
		if ( domain.getCustomer() != null ) {
			dto.setCustomerId(domain.getCustomer().getId());
			dto.setCustomerName(domain.getCustomer().getName());
		}
	}

	private void setChildCount(Asset domain, final AssetDTO dto) {
		if (domain.getChildCount() != null) {
			dto.setChildCount(domain.getChildCount());
		}
	}

	private void setAssetCountry(Asset domain, final AssetDTO dto) {
		if (domain.getCountry() != null) {
			dto.setCountryId(domain.getCountry().getId());
			dto.setCountryName(domain.getCountry().getName());
		}
	}

	private void setPartConsumingReferences(AssetDTO dto, Asset domain) throws Exception {
		if (domain.getPartConsumingReferences() != null && domain.getPartConsumingReferences().size() > 0) {
			final List<AssetConsumingReferenceDTO> assetConsumingDTOs = new ArrayList<>();
			for ( final AssetConsumingReference assetConsumePref : domain.getPartConsumingReferences()) {
				final AssetConsumingReferenceDTO assetConsumingDTO = AssetConsumingReferenceMapper.getInstance().domainToDto(assetConsumePref);
				assetConsumingDTOs.add(assetConsumingDTO);
			}
			dto.setPartConsumeRefs(assetConsumingDTOs);
		}
	}
	private void setLocation(AssetDTO dto, Asset domain){
		final LocationDTO locationDTO=new LocationDTO();

		if(domain.getLatitude() !=null){
			locationDTO.setLatitude(domain.getLatitude());
		}
		if(domain.getLongitude() !=null){
			locationDTO.setLongitude(domain.getLongitude());
		}

		dto.setLocationDTO(locationDTO);
	}

	private void setWarranties(AssetDTO dto, Asset domain) throws Exception {
		if (domain.getWarranties() != null && domain.getWarranties().size() > 0) {
			final List<WarrantyDTO> warrantyDTOs = new ArrayList<>();
			for (final Warranty warranty : domain.getWarranties()) {
				final WarrantyDTO warrantyDTO = WarrantyMapper.getInstance().domainToDto(warranty);
				warrantyDTOs.add(warrantyDTO);
			}
			dto.setWarranties(warrantyDTOs);
		}
	}

	private void setAssetConsumingReferences(AssetDTO dto, Asset domain) throws Exception {
		if (domain.getAssetConsumingReferences() != null && domain.getAssetConsumingReferences().size() > 0) {
			final List<AssetConsumingReferenceDTO> assetConsumingDTOs = new ArrayList<>();
			for ( final AssetConsumingReference assetConsumePref : domain.getAssetConsumingReferences()) {
				final AssetConsumingReferenceDTO assetConsumingDTO = AssetConsumingReferenceMapper.getInstance().domainToDto(assetConsumePref);
				assetConsumingDTOs.add(assetConsumingDTO);
			}
			dto.setAssetConsumeRefs(assetConsumingDTOs);
		}
	}

	private void setLocationString(AssetDTO dto, Asset domain) {
		final StringBuilder strBuilder = new StringBuilder("");

		if (domain.getAddress() != null && !domain.getAddress().isEmpty()) {
			strBuilder.append(domain.getAddress());
		}

		if (domain.getCity() != null && !domain.getCity().isEmpty()) {
			if (!strBuilder.toString().isEmpty()) {
				strBuilder.append(", ");
			}
			strBuilder.append(domain.getCity());
		}

		if (domain.getProvince() != null && !domain.getProvince().isEmpty()) {
			if (!strBuilder.toString().isEmpty()) {
				strBuilder.append(", ");
			}
			strBuilder.append(domain.getProvince() + ".");
		}

		dto.setLocation(strBuilder.toString());
	}

	private void setMeterReadings(Asset domain, AssetDTO dto) throws Exception {
		if (domain.getAssetMeterReadings().size() > 0) {
			for (final AssetMeterReading assetMeterReading : domain.getAssetMeterReadings()) {
				dto.getAssetMeterReadings().add(AssetMeterReadingMapper.getInstance().domainToDto(assetMeterReading));
			}
		}
	}

	private void setAssetEventTypeAssets(Asset domain, AssetDTO dto) throws Exception {
		if (domain.getAssetEventTypeAssets().size() > 0) {
			for (final AssetEventTypeAsset assetEventTypeAsset : domain.getAssetEventTypeAssets()) {
				dto.getAssetEventTypeAssets().add(AssetEventTypeAssetMapper.getInstance().domainToDto(assetEventTypeAsset));

			}
		}
	}
	private void setAssetFile(Asset domain, AssetDTO dto) throws Exception {
		if (domain.getAssetFiles().size() > 0) {
			for (final AssetFile assetFile :domain.getAssetFiles()) {
				final AssetFileDTO assetFileDTO=new AssetFileDTO();
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
			for (final SparePart sparePart :domain.getSpareParts()) {
				final SparePartDTO sparePartDTO=new SparePartDTO();
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

		domain.setSize(dto.getSize());
		domain.setQuantity(dto.getQuantity());
		domain.setUnitCost(dto.getUnitCost());
		domain.setTotalCost(dto.getTotalCost());
		domain.setUsefulLife(dto.getUsefulLife());
		domain.setYearlyDepreciationValue(dto.getYearlyDepreciationValue());
		domain.setYearEndNetBookValue(dto.getYearEndNetBookValue());
		domain.setAccumulatedDepreciation(dto.getAccumulatedDepreciation());
		domain.setDateOfPurchase(dto.getDateOfPurchase());
		domain.setDepartment(dto.getDepartment());
		domain.setAssetClass(dto.getAssetClass());
		domain.setRemarks(dto.getRemark());
		domain.setAddedDate(dto.getAddedDate());
	}

	@Override
	public AssetDTO domainToDtoForDataTable(Asset domain) throws Exception {

		final AssetDTO dto = new AssetDTO();

		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDepartment(domain.getDepartment());
		dto.setAssetCategoryName(domain.getAssetCategory().getName());
		dto.setAddedDate(domain.getAddedDate());

		setLocationString(dto, domain);
		setBusiness(domain, dto);
		setCustomer(domain, dto);
		setChildCount(domain, dto);
		setParentAsset(domain, dto);
		setSite(domain, dto);
		setSubSitr(domain, dto);

		return dto;
	}

}
