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
public class AssetServiceDeleteByIdTestCase extends TestCase {
	
	@Autowired
	private AssetService assetService;

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
        try {
            AssetDTO asset = AssetDummyData.getInstance().getDummyDTOFaciltyAsset();

            AssetResult result = assetService.save(asset, null);
            Integer expectedId = result.getDomainEntity().getId();

            assetService.delete(expectedId);

            Assert.assertNull("Asset is not null.", assetService.findById(expectedId));

        } catch (Exception e) {
			isError = true;
		}
	}

}
