package com.codex.ecam.service.admin.cmmssettings.asseteventtype;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.AssetEventTypeDTO;
import com.codex.ecam.result.admin.AssetEventTypeResult;
import com.codex.ecam.service.admin.api.AssetEventTypeService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class AssetEventTypeServiceCreateTestCase extends TestCase{

	@Autowired
	private  AssetEventTypeService  assetEventTypeService;
	
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
        AssetEventTypeResult expectedId = null;
        try {
            AssetEventTypeDTO assetEventTypeDTO = ( AssetEventTypeDTO) getTestCondition(CONDITION_SAVE_ENTITY);
			expectedId = assetEventTypeService.save(assetEventTypeDTO);

            Assert.assertNotNull("Asset  Event Type is null.", assetEventTypeService.findById(expectedId.getDtoEntity().getId()));

        } catch (Exception e) {
            isError = true;
		} finally {
            assetEventTypeService.delete(expectedId.getDtoEntity().getId());
        }
    }

}
