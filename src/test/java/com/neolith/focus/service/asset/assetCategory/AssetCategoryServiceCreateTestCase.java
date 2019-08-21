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
public class AssetCategoryServiceCreateTestCase extends TestCase{

	@Autowired
	private  AssetCategoryService  assetCategoryService;
	
	// conditions
	protected final String CONDITION_SAVE_ENTITY = "entity";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		Integer expectedId = null;
		try {
			
			AssetCategoryDTO assetCategoryDTO = ( AssetCategoryDTO) getTestCondition(CONDITION_SAVE_ENTITY);
            AssetCategoryResult result = assetCategoryService.save(assetCategoryDTO);
			
			
			expectedId = result.getDomainEntity().getId();
	
			Assert.assertNotNull("Asset  Event Type is null.", assetCategoryService.findById(expectedId));		
							
		} catch (Exception e) {
			isError = true;
		} finally {
			assetCategoryService.delete(expectedId);			
		}
	}


}
