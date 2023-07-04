package com.codex.ecam.util.search.asset;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class AssetSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static AssetSearchPropertyMapper instance = null;

	private AssetSearchPropertyMapper() {}

	public static AssetSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new AssetSearchPropertyMapper();
		}
		return instance;
	}

	@Override
	protected void mapSearchParamsToPropertyParams(String tableColumn) {

		switch (tableColumn) {

		case "assetCategoryName":
			addColumns("assetCategory.name");
			break;

		case "location":
			addColumns("address", "city", "province");
			break;

		case "businessName":
			addColumns("business.name");
			break;

		case "customerName":
			addColumns("customer.name");
			break;

		case "parentAssetName":
			addColumns("parentAsset.name");
			break;

		case "siteName":
			addColumns("site.name");
			break;

		case "subSiteName":
			addColumns("subSite.name");
			break;

		default:
			break;

		}
	}

}
