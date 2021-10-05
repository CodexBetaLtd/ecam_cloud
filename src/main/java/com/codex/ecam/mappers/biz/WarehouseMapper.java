package com.codex.ecam.mappers.biz;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.asset.LocationDTO;
import com.codex.ecam.dto.biz.warehouse.WareHouseDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.asset.Asset;

import java.util.ArrayList;
import java.util.List;

public class WarehouseMapper extends GenericMapper<Asset, WareHouseDTO> {

	private static WarehouseMapper instance = null;

	private WarehouseMapper() {
	}

	public static WarehouseMapper getInstance() {
		if (instance == null) {
			instance = new WarehouseMapper();
		}
		return instance;
	}

	@Override
	public WareHouseDTO domainToDto(Asset  domain) throws Exception {
		final WareHouseDTO dto = new WareHouseDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		dto.setCity(domain.getCity());
		dto.setAddress(domain.getAddress());
		dto.setProvince(domain.getProvince());
		dto.setPostalCode(domain.getPostalcode());

		if (domain.getParentAsset() != null) {
			dto.setParentAssetId(domain.getParentAsset().getId());
		}

		if (domain.getCountry() != null) {
			dto.setCountryId(domain.getCountry().getId());
			dto.setCountryName(domain.getCountry().getName());
		}

		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
			dto.setSiteName(domain.getSite().getName());
		}

		if(domain.getAssetCategory() != null){
			dto.setAssetCategoryId(domain.getAssetCategory().getId());
			dto.setAssetCategoryName(domain.getAssetCategory().getName());
		}

		if(domain.getAssetCategory() != null){
			if(domain.getAssetCategory().getParentAssetCategory() != null){
				dto.setParentAssetCategoryId(domain.getAssetCategory().getParentAssetCategory().getId());
				dto.setParentAssetCategoryName(domain.getAssetCategory().getParentAssetCategory().getName());
			}
		}

		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}
		if (domain.getChildCount() != null) {
			dto.setChildCount(domain.getChildCount());
		}
		if(domain.getParentAsset()!=null){
			dto.setParentAssetId(domain.getParentAsset().getId());
			dto.setParentAssetName(domain.getParentAsset().getName());
		}
		dto.setChildCount(domain.getChildCount());

		setLocation(dto, domain);
		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setLocation(WareHouseDTO dto, Asset domain){
		final LocationDTO locationDTO = new LocationDTO();

		if (domain.getLatitude() != null) {
			locationDTO.setLatitude(domain.getLatitude());
		}
		if (domain.getLongitude() != null) {
			locationDTO.setLongitude(domain.getLongitude());
		}

		dto.setLocationDTO(locationDTO);
	}

	@Override
	public void dtoToDomain(WareHouseDTO dto, Asset  domain) throws Exception {
		domain.setIsDeleted(false);
		domain.setIsOnline(dto.getIsDefault());
		domain.setCode(dto.getCode());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setVersion(dto.getVersion());
		domain.setAddress(dto.getAddress());
		domain.setCity(dto.getCity());
		domain.setProvince(dto.getProvince());
		domain.setPostalcode(dto.getPostalCode());

		if (domain.getAssetCategory() != null) {
			if(domain.getAssetCategory().getParentAssetCategory() != null){
				dto.setParentAssetCategoryId(domain.getAssetCategory().getParentAssetCategory().getId());
				dto.setParentAssetCategoryName(domain.getAssetCategory().getParentAssetCategory().getName());
			}
		}

		setCommanDomainFields(dto, domain);
	}


	@Override
	public WareHouseDTO domainToDtoForDataTable(Asset domain) throws Exception {
		final WareHouseDTO dto = new WareHouseDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setCity(domain.getCity());
		dto.setDescription(domain.getDescription());
		if (domain.getChildCount() != null) {
			dto.setChildCount(domain.getChildCount());
		}
		dto.setChildCount(domain.getChildCount());
		if(domain.getParentAsset()!=null){
			dto.setParentAssetId(domain.getParentAsset().getId());
			dto.setParentAssetName(domain.getParentAsset().getName());
		}

		return dto;
	}

	public DataTablesOutput<WareHouseDTO> domainToDTODataTablesOutputCustom(DataTablesOutput<Asset> domainOut, Integer partId) throws Exception {
		final DataTablesOutput<WareHouseDTO> out = new DataTablesOutput<WareHouseDTO>();
		out.setData(domainToDTOListForDataTablesCustom(domainOut.getData(), partId));
		out.setDraw(domainOut.getDraw());
		out.setError(domainOut.getError());
		out.setRecordsFiltered(domainOut.getRecordsFiltered());
		out.setRecordsTotal(domainOut.getRecordsTotal());

		return out;
	}

	public List<WareHouseDTO> domainToDTOListForDataTablesCustom(Iterable<Asset> domainList, Integer partId) throws Exception {
		if (domainList == null) {
			return new ArrayList<WareHouseDTO>();
		}
		final List<WareHouseDTO> dtoList = new ArrayList<WareHouseDTO>();
		for (final Asset domain : domainList) {
			dtoList.add(domainToDtoForDataTableCustom(domain, partId));
		}
		return dtoList;
	}

	public WareHouseDTO domainToDtoForDataTableCustom(Asset domain, Integer partId) throws Exception {
		final WareHouseDTO dto = new WareHouseDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setCity(domain.getCity());
		dto.setDescription(domain.getDescription());
		if(domain.getParentAsset()!=null){
			dto.setParentAssetId(domain.getParentAsset().getId());
			dto.setParentAssetName(domain.getParentAsset().getName());
		}

		return dto;
	}


}
