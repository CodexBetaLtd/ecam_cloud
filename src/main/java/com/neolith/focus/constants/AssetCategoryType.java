package com.neolith.focus.constants;

import java.util.ArrayList;
import java.util.List;

public enum AssetCategoryType {

	EQUIPMENTS_OR_MACHINES(0, "Equipments or Machines"),
	LOCATIONS_OR_FACILITIES(1, "Locations or Facilities"),
	TOOLS(2, "Tools"),
	PARTS_AND_SUPPLIES(3, "Parts And Supplies"),
	WAREHOUSE(4, "Warehouse");

	private Integer id;
	private String assetCategoryTypeName;

	private AssetCategoryType(Integer id, String assetCategoryTypeName) {
		setId(id);
		setAssetCategoryTypeName(assetCategoryTypeName);
	}

	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	public String getAssetCategoryTypeName() {
		return assetCategoryTypeName;
	}

	private void setAssetCategoryTypeName(String assetCategoryTypeName) {
		this.assetCategoryTypeName = assetCategoryTypeName;
	}

	public static List<AssetCategoryType> getAssetCategoryTypes(){
		List<AssetCategoryType> list = new ArrayList<AssetCategoryType>();
		list.add(AssetCategoryType.EQUIPMENTS_OR_MACHINES);
		list.add(AssetCategoryType.LOCATIONS_OR_FACILITIES);
		list.add(AssetCategoryType.TOOLS);
		list.add(AssetCategoryType.PARTS_AND_SUPPLIES);
		list.add(AssetCategoryType.WAREHOUSE);
		return list;
	}

}
