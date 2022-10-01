package com.codex.ecam.service.admin.cmmssettings.businessclassification;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.result.admin.BusinessClassificationResult;
import com.codex.ecam.service.biz.api.BusinessClassificationService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class BussinessClassificationServiceFindbyIdTestCase    extends TestCase{

	// conditions
	protected final String CONDITION_EXPECTED_NAME = "name";
	protected final String RESULT_IS_ERROR = "isError";
	@Autowired
	private BusinessClassificationService businessClassificationService;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
        BusinessClassificationResult expectedId = null;
        try {
            BusinessClassificationDTO businessClassificationDTO = BussinessClassificationDummyData.getInstance().getDummyDTOBussinessClassificationOne();
			expectedId = businessClassificationService.save(businessClassificationDTO);

            BusinessClassificationDTO businessClassification = businessClassificationService.findById(expectedId.getDtoEntity().getId());

            if (businessClassification != null) {
                Assert.assertEquals("Asset category should be " + expectedId + ".", expectedId.getDtoEntity().getId(), businessClassification.getId());
                testEntity(businessClassification);
            } else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
            businessClassificationService.delete(expectedId.getDtoEntity().getId());
        }
    }

	private void testEntity(BusinessClassificationDTO businessClassificationDTO) {
		testEntity(businessClassificationDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(BusinessClassificationDTO businessClassificationDTO , String name) {
        Assert.assertNotNull("Business Classification is null.", businessClassificationDTO);        
        Assert.assertEquals("Name ", name, businessClassificationDTO.getName());
    }

}
