package com.codex.ecam.service.admin.cmmssettings.businessclassification;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.result.admin.BusinessClassificationResult;
import com.codex.ecam.service.biz.api.BusinessClassificationService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class BussinessClassificationServiceCreateTestCase extends TestCase{

    // conditions
    protected final String CONDITION_SAVE_ENTITY = "entity";
	protected final String RESULT_IS_ERROR = "isError";
    @Autowired
    private BusinessClassificationService businessClassificationService;
    private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
        BusinessClassificationResult expectedId = null;
        try {
            BusinessClassificationDTO businessClassificationDTO = ( BusinessClassificationDTO) getTestCondition(CONDITION_SAVE_ENTITY);
			expectedId = businessClassificationService.save(businessClassificationDTO);

            Assert.assertNotNull("Business Classification is null.", businessClassificationService.findById(expectedId.getDtoEntity().getId()));

        } catch (Exception e) {
            isError = true;
		} finally {
            businessClassificationService.delete(expectedId.getDtoEntity().getId());
        }
    }

}
