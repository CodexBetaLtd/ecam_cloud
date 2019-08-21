package com.neolith.focus.service.admin.cmmssettings.businessclassification;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.AssetEventTypeDTO;
import com.neolith.focus.service.admin.api.AssetEventTypeService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BussinessClassificationFindAllTestCase  extends TestCase{

	
	@Autowired
	private AssetEventTypeService  assetEventTypeService;
	
	// conditions
	protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_ID = "firstId";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_FIRST_DESCRIPTION = "firstDescription";
	protected final String CONDITION_EXPECTED_FIRST_CODE = "firstCode";

	protected final String CONDITION_EXPECTED_SECOND_ID = "secondId";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";
	protected final String CONDITION_EXPECTED_SECOND_DESCRIPTION = "secondDescription";
	protected final String CONDITION_EXPECTED_SECOND_CODE= "secondCode";


	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			List<AssetEventTypeDTO> list = assetEventTypeService.findAll();
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			Integer expectedFirstId = (Integer) getTestCondition(CONDITION_EXPECTED_FIRST_ID);
			Integer expectedSecondId = (Integer) getTestCondition(CONDITION_EXPECTED_SECOND_ID);
	
			Assert.assertNotNull("Asset Event Type  list is null.", list);
			Assert.assertEquals("Number of Asset Event Type  should be " + expectedCount + ".", expectedCount, new Integer(list.size()));
	        
	        for (AssetEventTypeDTO assetEventTypeDTO : list) {	            
	            if (expectedFirstId.equals(assetEventTypeDTO.getId())) {
	            	testAssetEventTypeOne(assetEventTypeDTO);
	            } else if (expectedSecondId.equals(assetEventTypeDTO.getId())) {
	            	testAssetEventTypeTwo(assetEventTypeDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		}
	}

	private void testAssetEventTypeTwo(AssetEventTypeDTO assetEventTypeDTO) {
		testAssetEventType(assetEventTypeDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME), (String) getTestCondition(CONDITION_EXPECTED_SECOND_DESCRIPTION), (String) getTestCondition(CONDITION_EXPECTED_FIRST_CODE));
	}

	private void testAssetEventTypeOne(AssetEventTypeDTO assetEventTypeDTO) {
		testAssetEventType(assetEventTypeDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME), (String) getTestCondition(CONDITION_EXPECTED_FIRST_DESCRIPTION), (String) getTestCondition(CONDITION_EXPECTED_SECOND_CODE));
	}
	
    private void testAssetEventType(AssetEventTypeDTO assetEventTypeDTO, String name,String decription,String  code) {
        Assert.assertNotNull("Asset category is null.", assetEventTypeDTO);        
        Assert.assertEquals("name", name, assetEventTypeDTO.getName());
        Assert.assertEquals("description", decription, assetEventTypeDTO.getDescription());
        Assert.assertEquals("Code", code, assetEventTypeDTO.getCode());
    }
}
