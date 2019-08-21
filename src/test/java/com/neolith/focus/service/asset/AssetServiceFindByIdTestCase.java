package com.neolith.focus.service.asset;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.result.asset.AssetResult;
import com.neolith.focus.service.asset.api.AssetService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetServiceFindByIdTestCase extends TestCase {
	
	@Autowired
	private AssetService assetService;
	
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

            AssetDTO assetSaved = AssetDummyData.getInstance().getDummyDTOFaciltyAsset();
            AssetResult result = assetService.save(assetSaved, null);
            expectedId = result.getDomainEntity().getId();

            AssetDTO assetFound = assetService.findById(expectedId);

            if( assetFound != null){
				Assert.assertEquals("Asset should be " + expectedId + ".", expectedId, assetFound.getId());
				testEntity(assetFound);
			} else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
			assetService.delete(expectedId);
		}
	}

	private void testEntity(AssetDTO asset) {
		testEntity(asset, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(AssetDTO asset, String name) {
        Assert.assertNotNull("Asset is null.", asset);        
        Assert.assertEquals("Name ", name, asset.getName());
    }

}
