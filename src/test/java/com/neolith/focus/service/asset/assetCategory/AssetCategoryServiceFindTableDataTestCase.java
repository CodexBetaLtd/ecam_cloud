package com.neolith.focus.service.asset.assetCategory;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.asset.AssetCategoryDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.asset.api.AssetCategoryService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class AssetCategoryServiceFindTableDataTestCase  extends TestCase {

	@Autowired
	private AssetCategoryService assetCategoryService;
	
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
			
			assetCategoryService.saveAll(AssetCategoryDummyData.getInstance().getAllDummyData());			
			
			DataTablesOutput<AssetCategoryDTO> out = assetCategoryService.findAll(new FocusDataTablesInput());
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Asset Category list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Asset Category  should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));
	        
	        for (AssetCategoryDTO assetCategoryDTO : out.getData()) {	            
	            if (expectedFirstName.equals(assetCategoryDTO.getName())) {
	            	testAssetCategoryOne(assetCategoryDTO);
	            } else if (expectedSecondName.equals(assetCategoryDTO.getId())) {
	            	testAssetCategoryTwo(assetCategoryDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			assetCategoryService.deleteAll();
		}
	}

	private void testAssetCategoryTwo(AssetCategoryDTO assetCategoryDTO) {
		testAssetCategory(assetCategoryDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testAssetCategoryOne(AssetCategoryDTO assetCategoryDTO) {
		testAssetCategory(assetCategoryDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}
	
    private void testAssetCategory(AssetCategoryDTO assetCategoryDTO, String name) {
        Assert.assertNotNull("Asset Category is null.", assetCategoryDTO);        
        Assert.assertEquals("firstName", name, assetCategoryDTO.getName());
    }

}
