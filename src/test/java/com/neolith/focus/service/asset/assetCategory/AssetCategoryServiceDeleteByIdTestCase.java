package com.neolith.focus.service.asset.assetCategory;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.asset.AssetCategoryDTO;
import com.neolith.focus.result.admin.AssetCategoryResult;
import com.neolith.focus.service.asset.api.AssetCategoryService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class AssetCategoryServiceDeleteByIdTestCase extends TestCase{
	

	@Autowired
	private AssetCategoryService  assetCategoryService;
	
	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {			
			
			AssetCategoryDTO assetCategoryDTO = AssetCategoryDummyData.getInstance().getDummyDTOAssetCategoryOne();

            AssetCategoryResult result = assetCategoryService.save(assetCategoryDTO);
            Integer expectedId = result.getDomainEntity().getId();

            assetCategoryService.delete(expectedId);
	
			Assert.assertNull("Asset Category is not null.", assetCategoryService.findById(expectedId));		

		} catch (Exception e) {
			isError = true;
		}
	}

}
