package com.codex.ecam.service.admin.cmmssettings.currency;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.CurrencyService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class CurrencyServiceFindTableDataTestCase  extends TestCase {

	@Autowired
	private  CurrencyService currencyService;
	
	// conditions
	protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			
			currencyService.saveAll(CurrencyDummyData.getInstance().getAllDummyData());			
			
			DataTablesOutput<CurrencyDTO> out = currencyService.findAll(new FocusDataTablesInput());
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Currency  list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Currency  should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));
	        
	        for (CurrencyDTO currencyDTO : out.getData()) {	            
	            if (expectedFirstName.equals(currencyDTO.getName())) {
	            	testCurrencyOne(currencyDTO);
	            } else if (expectedSecondName.equals(currencyDTO.getId())) {
	            	testCurrencyTwo(currencyDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			currencyService.deleteAll();
		}
	}

	private void testCurrencyTwo(CurrencyDTO currencyDTO ) {
		testCurrency(currencyDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testCurrencyOne(CurrencyDTO currencyDTO ) {
		testCurrency(currencyDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}
	
    private void testCurrency(CurrencyDTO currencyDTO , String name) {
        Assert.assertNotNull("Asset Event Type is null.", currencyDTO);        
        Assert.assertEquals("firstName", name, currencyDTO.getName());
    }
}
