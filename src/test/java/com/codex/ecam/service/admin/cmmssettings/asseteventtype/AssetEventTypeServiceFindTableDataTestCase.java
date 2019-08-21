package com.codex.ecam.service.admin.cmmssettings.asseteventtype;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.AssetEventTypeDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.AssetEventTypeService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class AssetEventTypeServiceFindTableDataTestCase  extends TestCase {

	@Autowired
	private  AssetEventTypeService assetEventTypeService;
	
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
			
			assetEventTypeService.saveAll(AssetEventTypeDummyData.getInstance().getAllDummyData());			
			
			DataTablesOutput<AssetEventTypeDTO> out = assetEventTypeService.findAll(new FocusDataTablesInput());
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Asset Event Type list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Asset Event Type  should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));
	        
	        for (AssetEventTypeDTO assetEventTypeDTO : out.getData()) {	            
	            if (expectedFirstName.equals(assetEventTypeDTO.getName())) {
	            	testAssetEventTypeOne(assetEventTypeDTO);
	            } else if (expectedSecondName.equals(assetEventTypeDTO.getId())) {
	            	testAssetEventTypeTwo(assetEventTypeDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			assetEventTypeService.deleteAll();
		}
	}

	private void testAssetEventTypeTwo(AssetEventTypeDTO assetEventTypeDTO) {
		testAssetCategory(assetEventTypeDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testAssetEventTypeOne(AssetEventTypeDTO assetEventTypeDTO) {
		testAssetCategory(assetEventTypeDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}
	
    private void testAssetCategory(AssetEventTypeDTO assetEventTypeDTO, String name) {
        Assert.assertNotNull("Asset Event Type is null.", assetEventTypeDTO);        
        Assert.assertEquals("firstName", name, assetEventTypeDTO.getName());
    }
}
