package com.neolith.focus.service.asset;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.asset.api.AssetService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class AssetServiceFindTableDataTestCase extends TestCase {
	
	@Autowired
	private AssetService assetService;
	
	// conditions
	protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			
			assetService.saveAll(AssetDummyData.getInstance().getAllDummyData());			
			
			DataTablesOutput<AssetDTO> out = assetService.findAll(new FocusDataTablesInput());
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Asset list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Assets should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));
	        
	        for (AssetDTO asset : out.getData()) {	            
	            if (expectedFirstName.equals(asset.getName())) {
	            	testAssetOne(asset);
	            } else if (expectedSecondName.equals(asset.getId())) {
	            	testAssetTwo(asset);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			assetService.deleteAll();
		}
	}

	private void testAssetTwo(AssetDTO asset) {
		testAsset(asset, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testAssetOne(AssetDTO business) {
		testAsset(business, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}
	
    private void testAsset(AssetDTO asset, String name) {
        Assert.assertNotNull("Asset is null.", asset);        
        Assert.assertEquals("Name", name, asset.getName());
    }

}
