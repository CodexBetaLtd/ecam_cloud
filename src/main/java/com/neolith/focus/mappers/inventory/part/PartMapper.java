package com.neolith.focus.mappers.inventory.part;

import java.util.ArrayList;
import java.util.List;

import com.neolith.focus.dto.asset.AssetUserDTO;
import com.neolith.focus.dto.asset.WarrantyDTO;
import com.neolith.focus.dto.biz.part.PartDTO;
import com.neolith.focus.dto.biz.part.PartNotificationDTO;
import com.neolith.focus.dto.inventory.AssetConsumingReferenceDTO;
import com.neolith.focus.dto.inventory.stock.StockDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.mappers.asset.AssetUserMapper;
import com.neolith.focus.mappers.asset.WarrantyMapper;
import com.neolith.focus.mappers.inventory.AssetConsumingReferenceMapper;
import com.neolith.focus.mappers.inventory.stock.StockMapper;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.asset.AssetConsumingReference;
import com.neolith.focus.model.asset.AssetUser;
import com.neolith.focus.model.asset.Warranty;
import com.neolith.focus.model.biz.part.PartNotification;
import com.neolith.focus.model.inventory.stock.Stock;

public class PartMapper extends GenericMapper<Asset, PartDTO> {

	private static PartMapper instance = null;

	private PartMapper(){
	}

	public static PartMapper getInstance() {
		if (instance == null) {
			instance = new PartMapper();
		}
		return instance;
	}

	@Override
	public PartDTO domainToDto(Asset domain) throws Exception {
		PartDTO dto = new PartDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		dto.setCompletionNotes(domain.getNotes());
		dto.setInventoryCode(domain.getInventoryCode());
		dto.setUnspcCode(domain.getUnspcCode());
		dto.setBarcode(domain.getBarcode());
		dto.setLastPrice(domain.getLastPrice());
		dto.setImageLocation(domain.getImageLocation());

		if ((domain.getAssetUsers() != null) && (domain.getAssetUsers().size() > 0)) {
			dto.setAssetUserDTOs(AssetUserMapper.getInstance().domainToDTOList(domain.getAssetUsers()));
		}

		if ((domain.getBusiness() != null)) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		if ((domain.getBrand() != null)) {
			dto.setBrandId(domain.getBrand().getId());
			dto.setBrandName(domain.getBrand().getBrandName());
		}
		if ((domain.getAssetCategory() != null)) {
			dto.setPartCategoryId(domain.getAssetCategory().getId());
			dto.setPartCategoryName(domain.getAssetCategory().getName());
		}

		if ((domain.getModel() != null)) {
			dto.setModelId(domain.getModel().getId());
			dto.setModelName(domain.getModel().getModelName());
		}

		setAssetConsumingReferences(dto, domain);
		setWarranties(dto, domain);
		setUser(dto, domain);
		setStock(dto, domain);
		setNotification(dto, domain);

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setNotification(PartDTO dto, Asset domain) throws Exception {
		List<PartNotificationDTO> notifications = new ArrayList<>();
		for (PartNotification partNotification : domain.getPartNotifications()) {
			PartNotificationDTO partNotificationDTO = PartNotificationMapper.getInstance().domainToDto(partNotification);
			notifications.add(partNotificationDTO);
		}
		dto.setPartNotificationDTOs(notifications);
	}

	private void setWarranties(PartDTO dto, Asset domain) throws Exception {
		if ((domain.getWarranties() != null) && (domain.getWarranties().size() > 0)) {
			List<WarrantyDTO> warrantyDTOs = new ArrayList<>();
			for ( Warranty warranty : domain.getWarranties()) {
				WarrantyDTO warrantyDTO = WarrantyMapper.getInstance().domainToDto(warranty);
				warrantyDTOs.add(warrantyDTO);
			}
			dto.setWarranties(warrantyDTOs);
		}
	}

	private void setAssetConsumingReferences(PartDTO dto, Asset domain) throws Exception {
		if ((domain.getAssetConsumingReferences() != null) && (domain.getAssetConsumingReferences().size() > 0)) {
			List<AssetConsumingReferenceDTO> assetConsumingDTOs = new ArrayList<>();
			for ( AssetConsumingReference assetConsumePref : domain.getAssetConsumingReferences()) {
				AssetConsumingReferenceDTO assetConsumingDTO = AssetConsumingReferenceMapper.getInstance().domainToDto(assetConsumePref);
				assetConsumingDTOs.add(assetConsumingDTO);
			}
			dto.setAssetConsumeRefs(assetConsumingDTOs);
		}
	}

	private void setUser(PartDTO dto, Asset domain){
		for(AssetUser assetUser : domain.getAssetUsers()){
			AssetUserDTO assetUserDTO = new AssetUserDTO();
			assetUserDTO.setId(assetUser.getId());
			assetUserDTO.setName(assetUser.getUser().getFullName());
			assetUserDTO.setEmail(assetUser.getUser().getEmailAddress());
			assetUserDTO.setUserId(assetUser.getUser().getId());
			dto.getAssetUserDTOs().add(assetUserDTO);

		}

	}

	private void setStock(PartDTO dto, Asset domain) throws Exception {
		List<StockDTO> stocks = new ArrayList<>();
		for (Stock stock : domain.getStocks()) {
			StockDTO stockDTO = StockMapper.getInstance().domainToDto(stock);
			if (stock.getBusiness() != null) {
				stockDTO.setBusinessIdRef(stock.getBusiness().getId());
				stockDTO.setBusinessRef(stock.getBusiness().getName());
			}
			stocks.add(stockDTO);
		}
		dto.setStockDTOs(stocks);
	}

	@Override
	public void dtoToDomain(PartDTO dto, Asset domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setCode(dto.getCode());
		domain.setDescription(dto.getDescription());
		domain.setNotes(dto.getCompletionNotes());
		domain.setInventoryCode(dto.getInventoryCode());
		domain.setUnspcCode(dto.getUnspcCode());
		domain.setBarcode(dto.getBarcode());
		domain.setLastPrice(dto.getLastPrice());
		domain.setIsOnline(true);
		domain.setImageLocation(dto.getImageLocation());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public PartDTO domainToDtoForDataTable(Asset domain) throws Exception {
		PartDTO dto = new PartDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

}

