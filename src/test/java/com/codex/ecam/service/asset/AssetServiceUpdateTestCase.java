package com.codex.ecam.service.asset;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.result.asset.AssetResult;
import com.codex.ecam.service.asset.api.AssetService;

@Component
public class AssetServiceUpdateTestCase extends TestCase {

	public static enum FieldName { NONE, NAME, CODE, DESCRIPTION, IS_ONLINE, ASSET_CATEGORY_ID, PARENT_ASSET_ID, NOTES,
		ASSET_CATEGORY_TYPE, ADDRESS, CITY, PROVINCE, POSTAL_CODE, COUNTRY_ID, BRAND_ID, MODEL_ID, SERIAL_NO};

		@Autowired
		private AssetService assetService;

		protected final String UPDATE_ENTITY_NAME = "name";

		protected final String UPDATE_ENTITY_VALUE = "value";

		protected final String RESULT_IS_ERROR = "isError";

		private boolean isError;

		@Override
		protected void checkActualResults() throws Exception {
			setActualResult(RESULT_IS_ERROR, isError);
		}

		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		protected void executeTest() throws Exception {
			Integer id = null;
			try {

				AssetDTO asset = AssetDummyData.getInstance().getDummyDTOFaciltyAsset();

				AssetResult result = assetService.save(asset, null);
				id = result.getDomainEntity().getId();
				asset.setId(id);

				setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), asset);

				assetService.save(asset, null);
				AssetDTO updatedAsset = assetService.findById(id);

				Assert.assertNotNull("Asset is null.", updatedAsset);
				Assert.assertTrue("Asset not updated.", isFieldUpdated(updatedAsset, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

			} catch (Exception e) {
				isError = true;
			} finally {
				assetService.delete(id);
			}
		}

		private boolean isFieldUpdated(AssetDTO asset, String name, Object value) {

			if (name.equals(FieldName.NAME.name())) {
				return asset.getName().equals(value);

			} else if (name.equals(FieldName.CODE.name())) {
				return asset.getCode().equals(value);

			} else if (name.equals(FieldName.DESCRIPTION.name())) {
				return asset.getDescription().equals(value);

			} else if (name.equals(FieldName.IS_ONLINE.name())) {
				return asset.getIsOnline().equals(value);

			} else if (name.equals(FieldName.ASSET_CATEGORY_ID.name())) {
				return asset.getAssetCategoryId().equals(value);

			} else if (name.equals(FieldName.PARENT_ASSET_ID.name())) {
				return asset.getParentAssetId().equals(value);

			} else if (name.equals(FieldName.NOTES.name())) {
				return asset.getNotes().equals(value);

			} else if (name.equals(FieldName.ASSET_CATEGORY_TYPE.name())) {
				return asset.getAssetCategoryType().equals(value);

			} else if (name.equals(FieldName.ADDRESS.name())) {
				return asset.getAddress().equals(value);

			} else if (name.equals(FieldName.CITY.name())) {
				return asset.getCity().equals(value);

			} else if (name.equals(FieldName.PROVINCE.name())) {
				return asset.getProvince().equals(value);

			} else if (name.equals(FieldName.POSTAL_CODE.name())) {
				return asset.getPostalCode().equals(value);

			} else if (name.equals(FieldName.COUNTRY_ID.name())) {
				return asset.getCountryId().equals(value);

			}  else if (name.equals(FieldName.BRAND_ID.name())) {
				return asset.getBrand().equals(value);

			} else if (name.equals(FieldName.MODEL_ID.name())) {
				return asset.getModel().equals(value);

			}else if (name.equals(FieldName.SERIAL_NO.name())) {
				return asset.getSerialNo().equals(value);

			}
			return false;
		}

		private void setGivenValue(String name, Object value, AssetDTO asset) {

			if (name.equals(FieldName.NAME.name())) {
				asset.setName((String) value);

			} else if (name.equals(FieldName.CODE.name())) {
				asset.setCode((String) value);

			} else if (name.equals(FieldName.DESCRIPTION.name())) {
				asset.setDescription((String) value);

			} else if (name.equals(FieldName.IS_ONLINE.name())) {
				asset.setIsOnline((Boolean) value);

			} else if (name.equals(FieldName.ASSET_CATEGORY_ID.name())) {
				asset.setAssetCategoryId((Integer) value);

			} else if (name.equals(FieldName.PARENT_ASSET_ID.name())) {
				asset.setParentAssetId((Integer) value);

			} else if (name.equals(FieldName.NOTES.name())) {
				asset.setNotes((String) value);

			} else if (name.equals(FieldName.ASSET_CATEGORY_TYPE.name())) {
				asset.setAssetCategoryType((AssetCategoryType) value);

			} else if (name.equals(FieldName.ADDRESS.name())) {
				asset.setAddress((String) value);

			} else if (name.equals(FieldName.CITY.name())) {
				asset.setCity((String) value);

			} else if (name.equals(FieldName.PROVINCE.name())) {
				asset.setProvince((String) value);

			} else if (name.equals(FieldName.POSTAL_CODE.name())) {
				asset.setPostalCode((String) value);

			} else if (name.equals(FieldName.COUNTRY_ID.name())) {
				asset.setCountryId((Integer) value);

			} else if (name.equals(FieldName.BRAND_ID.name())) {
				asset.setBrand((Integer) value);

			} else if (name.equals(FieldName.MODEL_ID.name())) {
				asset.setModel((Integer) value);

			} else if (name.equals(FieldName.SERIAL_NO.name())) {
				asset.setSerialNo((String) value);

			}
		}

}