package com.codex.ecam.service.admin.business;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.biz.business.BusinessDTO;
import com.codex.ecam.result.admin.BusinessResult;
import com.codex.ecam.service.biz.api.BusinessService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BusinessServiceDeleteByIdTestCase extends TestCase {

    protected final String RESULT_IS_ERROR = "isError";
    @Autowired
	private BusinessService businessService;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {
			
			BusinessDTO business = BusinessDummyData.getInstance().getDummyDTOBusinessOne();

            BusinessResult result = businessService.save(business);
            Integer expectedId = result.getDomainEntity().getId();

            businessService.delete(expectedId);

            Assert.assertNull("Business is not null.", businessService.findById(expectedId));

        } catch (Exception e) {
			isError = true;
		}
	}

}
