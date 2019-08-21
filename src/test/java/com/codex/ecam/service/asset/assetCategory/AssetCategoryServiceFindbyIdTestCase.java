package com.codex.ecam.service.asset.assetCategory;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.result.admin.AssetCategoryResult;
import com.codex.ecam.service.asset.api.AssetCategoryService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class AssetCategoryServiceFindbyIdTestCase  extends TestCase{

	@Autowired
	private AssetCategoryService assetCategoryService;
	
	// conditions
	protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		Integer expectedId = null;
		try {			
			
			AssetCategoryDTO assetCategoryDTO = AssetCategoryDummyData.getInstance().getDummyDTOAssetCategoryOne();
            AssetCategoryResult result = assetCategoryService.save(assetCategoryDTO);
            expectedId = result.getDomainEntity().getId();
            AssetCategoryDTO assetCategory = assetCategoryService.findById(expectedId);
			if( assetCategory != null){
				Assert.assertEquals("Asset category should be " + expectedId + ".", expectedId, assetCategory.getId());
				testEntity(assetCategory);
			} else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
			assetCategoryService.delete(expectedId);
		}
	}

	private void testEntity(AssetCategoryDTO assetCategoryDTO ) {
		testEntity(assetCategoryDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(AssetCategoryDTO assetCategoryDTO , String name) {
        Assert.assertNotNull("Asset category is null.", assetCategoryDTO);        
        Assert.assertEquals("Name ", name, assetCategoryDTO.getName());
    }
}
