package com.neolith.focus.service.admin.business;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.biz.business.BusinessDTO;
import com.neolith.focus.result.admin.BusinessResult;
import com.neolith.focus.service.biz.api.BusinessService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BusinessServiceCreateTestCase extends TestCase {	
	
	// conditions
	protected final String CONDITION_SAVE_ENTITY = "entity";
	protected final String RESULT_IS_ERROR = "isError";
    @Autowired
    private BusinessService businessService;
    private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		Integer expectedId = null;
		try {
			BusinessDTO businessDTO = (BusinessDTO) getTestCondition(CONDITION_SAVE_ENTITY);
            BusinessResult result = businessService.save(businessDTO);
            expectedId = result.getDomainEntity().getId();
            Assert.assertNotNull("Business is null.", businessService.findById(expectedId));

        } catch (Exception e) {
            isError = true;
		} finally {
			businessService.delete(expectedId);
		}
	}

}
