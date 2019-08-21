package com.neolith.focus.service.admin.business;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.biz.business.BusinessDTO;
import com.neolith.focus.result.admin.BusinessResult;
import com.neolith.focus.service.biz.api.BusinessService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessServiceFindByIdTestCase extends TestCase {

	// conditions
	protected final String CONDITION_EXPECTED_NAME = "name";
	protected final String RESULT_IS_ERROR = "isError";
    @Autowired
    private BusinessService businessService;
    private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		Integer expectedId = null;
		try {			
			BusinessDTO businessDTO = BusinessDummyData.getInstance().getDummyDTOBusinessOne();
            BusinessResult result = businessService.save(businessDTO);
            expectedId = result.getDomainEntity().getId();

            BusinessDTO business = businessService.findById(expectedId);

            if (business != null) {
                Assert.assertEquals("User Group should be " + expectedId + ".", expectedId, business.getId());
				testEntity(business);
			} else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
			businessService.delete(expectedId);
		}
	}

	private void testEntity(BusinessDTO business) {
		testEntity(business, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(BusinessDTO business, String name) {
        Assert.assertNotNull("userGroup is null.", business);        
        Assert.assertEquals("Name ", name, business.getName());
    }
}