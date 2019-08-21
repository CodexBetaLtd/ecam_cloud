package com.codex.ecam.service.admin.cmmssettings.asseteventtype;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.AssetEventTypeDTO;
import com.codex.ecam.result.admin.AssetEventTypeResult;
import com.codex.ecam.service.admin.api.AssetEventTypeService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class AssetEventTypeServiceFindbyIdTestCase    extends TestCase{

	@Autowired
	private AssetEventTypeService assetEventTypeService;
	
	// conditions
	protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
        AssetEventTypeResult expectedId = null;
        try {
            AssetEventTypeDTO assetEventTypeDTO = AssetEventTypeDummyData.getInstance().getDummyDTOAssetEventTypeOne();
			expectedId = assetEventTypeService.save(assetEventTypeDTO);

            AssetEventTypeDTO assetEventType = assetEventTypeService.findById(expectedId.getDtoEntity().getId());

            if (assetEventType != null) {
                Assert.assertEquals("Asset category should be " + expectedId.getDtoEntity().getId() + ".", expectedId.getDtoEntity().getId(), assetEventType.getId());
                testEntity(assetEventType);
            } else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
            assetEventTypeService.delete(expectedId.getDtoEntity().getId());
        }
    }

	private void testEntity(AssetEventTypeDTO assetEventTypeDTO) {
		testEntity(assetEventTypeDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(AssetEventTypeDTO assetEventTypeDTO , String name) {
        Assert.assertNotNull("Asset category is null.", assetEventTypeDTO);        
        Assert.assertEquals("Name ", name, assetEventTypeDTO.getName());
    }

}
