package com.codex.ecam.service.asset.assetCategory;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.result.admin.AssetCategoryResult;
import com.codex.ecam.service.asset.api.AssetCategoryService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AssetCategoryServiceUpdateTestCase extends TestCase {
	
	public static enum FieldName {NAME,DESCRIPTION,OVERRIDERULE,TYPE,PARENTID,SYSCODE};

	@Autowired
	private AssetCategoryService assetCategoryService;
	
	protected final String UPDATE_ENTITY_NAME = "name";
	
	protected final String UPDATE_ENTITY_VALUE = "value";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		Integer id = null;
		try {			
			
			AssetCategoryDTO assetCategoryDTO = AssetCategoryDummyData.getInstance().getDummyDTOAssetCategoryOne();	

            AssetCategoryResult result = assetCategoryService.save(assetCategoryDTO);
            id = result.getDomainEntity().getId();
            assetCategoryDTO.setId(id);

            setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), assetCategoryDTO);

            assetCategoryService.update(assetCategoryDTO);
            AssetCategoryDTO updatedassetCategoryDTO = assetCategoryService.findById(id);
	
			Assert.assertNotNull(" asset Category is null.", updatedassetCategoryDTO);
			Assert.assertTrue(" asset Category not updated.", isFieldUpdated(updatedassetCategoryDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));
					
		} catch (Exception e) {
			isError = true;
		} finally {
			assetCategoryService.delete(id);
		}
	}
	
	private boolean isFieldUpdated(AssetCategoryDTO assetCategoryDTO, String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return assetCategoryDTO.getName().equals((String) value);
			
		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			return assetCategoryDTO.getDescription().equals((String) value);
			
		} else if (name.equals(FieldName.OVERRIDERULE.name())) {
			return assetCategoryDTO.getOverideRule().equals((String) value);
			
		} else if (name.equals(FieldName.TYPE.name())) {
			return assetCategoryDTO.getType().equals((String) value);
			
		} else if (name.equals(FieldName.PARENTID.name())) {
			return assetCategoryDTO.getParentId().equals((String) value);
			
		} else if (name.equals(FieldName.SYSCODE.name())) {
			return assetCategoryDTO.getSysCode().equals((String) value);
			
		} 
		return false;
	}

	private void setGivenValue(String name, Object value, AssetCategoryDTO assetCategoryDTO) {

		if (name.equals(FieldName.NAME.name())) {
			assetCategoryDTO.setName((String) value);
			
		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			assetCategoryDTO.setDescription((String) value);
			
		} else if (name.equals(FieldName.OVERRIDERULE.name())) {
			assetCategoryDTO.setOverideRule((String) value);
			
		} else if (name.equals(FieldName.TYPE.name())) {
			assetCategoryDTO.setType((AssetCategoryType) value);
			
		}  else if (name.equals(FieldName.PARENTID.name())) {
		 assetCategoryDTO.setParentId((Integer) value);
			
		}else if (name.equals(FieldName.SYSCODE.name())) {
			assetCategoryDTO.setSysCode((Integer) value);
			
		}
	}

}
