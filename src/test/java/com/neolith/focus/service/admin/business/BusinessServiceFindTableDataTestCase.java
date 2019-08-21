package com.neolith.focus.service.admin.business;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.biz.business.BusinessDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.biz.api.BusinessService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class BusinessServiceFindTableDataTestCase extends TestCase {
	
	// conditions
	protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";
	protected final String RESULT_IS_ERROR = "isError";
	@Autowired
	private BusinessService businessService;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			
			businessService.saveAll(BusinessDummyData.getInstance().getAllDummyData());			
			
			DataTablesOutput<BusinessDTO> out = businessService.findAll(new FocusDataTablesInput());
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Business list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Businesses should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));
	        
	        for (BusinessDTO business : out.getData()) {	            
	            if (expectedFirstName.equals(business.getName())) {
	            	testBusinessOne(business);
	            } else if (expectedSecondName.equals(business.getId())) {
	            	testBusinessTwo(business);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			businessService.deleteAll();
		}
	}

	private void testBusinessTwo(BusinessDTO business) {
		testBusiness(business, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testBusinessOne(BusinessDTO business) {
		testBusiness(business, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}
	
    private void testBusiness(BusinessDTO business, String name) {
        Assert.assertNotNull("Business is null.", business);        
        Assert.assertEquals("firstName", name, business.getName());
    }

}
