package com.codex.ecam.service.admin.cmmssettings.country;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.CountryService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class CountryServiceFindTableDataTestCase  extends TestCase {

	@Autowired
	private  CountryService countryService;
	
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
			
			countryService.saveAll(CountryDummyData.getInstance().getAllDummyData());			
			
			DataTablesOutput<CountryDTO> out = countryService.findAll(new FocusDataTablesInput());
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Country list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Country should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));
	        
	        for (CountryDTO countryDTO : out.getData()) {	            
	            if (expectedFirstName.equals(countryDTO.getName())) {
	            	testCountryOne(countryDTO);
	            } else if (expectedSecondName.equals(countryDTO.getId())) {
	            	testCountryTwo(countryDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			countryService.deleteAll();
		}
	}

	private void testCountryTwo(CountryDTO countryDTO) {
		testCountry(countryDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testCountryOne(CountryDTO countryDTO) {
		testCountry(countryDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}
	
    private void testCountry(CountryDTO countryDTO, String name) {
        Assert.assertNotNull("Country is null.", countryDTO);        
        Assert.assertEquals("Country type", name, countryDTO.getName());
    }
}