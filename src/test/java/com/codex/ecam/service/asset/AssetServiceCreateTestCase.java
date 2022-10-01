package com.codex.ecam.service.asset;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.result.asset.AssetResult;
import com.codex.ecam.service.asset.api.AssetService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AssetServiceCreateTestCase extends TestCase {

	@Autowired
	private AssetService assetService;

	// conditions
	protected final String CONDITION_SAVE_ENTITY = "entity";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		Integer expectedId = null;
		try {
			AssetDTO assetDTO = (AssetDTO) getTestCondition(CONDITION_SAVE_ENTITY);
            AssetResult result = assetService.save(assetDTO, null);
            expectedId = result.getDomainEntity().getId();
			Assert.assertNotNull("Asset is null.", assetService.findById(expectedId));

		} catch (Exception e) {
			isError = true;
		} finally {
			assetService.delete(expectedId);
		}
	}


}
