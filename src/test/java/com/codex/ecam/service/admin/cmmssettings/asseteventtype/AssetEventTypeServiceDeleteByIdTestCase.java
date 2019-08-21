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
public class AssetEventTypeServiceDeleteByIdTestCase extends TestCase{
	

	@Autowired
	private AssetEventTypeService  assetEventTypeService;
	
	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {
			
			AssetEventTypeDTO assetEventTypeDTO = AssetEventTypeDummyData.getInstance().getDummyDTOAssetEventTypeOne();

            AssetEventTypeResult expectedId = assetEventTypeService.save(assetEventTypeDTO);
            assetEventTypeService.delete(expectedId.getDtoEntity().getId());

            Assert.assertNull("Asset Category is not null.", assetEventTypeService.findById(expectedId.getDtoEntity().getId()));

		} catch (Exception e) {
			isError = true;
		}
	}

}
