package com.codex.ecam.service.admin.cmmssettings.country;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.result.admin.CountryResult;
import com.codex.ecam.service.admin.api.CountryService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CountryServiceFindbyIdTestCase    extends TestCase{

	@Autowired
	private CountryService countryService;
	
	// conditions
	protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
        CountryResult expectedId = null;
        try {
            CountryDTO countryDTO = CountryDummyData.getInstance().getDummyDTOCountryOne();
			expectedId = countryService.save(countryDTO);

            CountryDTO country = countryService.findById(expectedId.getDtoEntity().getId());

            if (country != null) {
                Assert.assertEquals("country should be " + expectedId + ".", expectedId.getDtoEntity().getId(), country.getId());
                testEntity(country);
            } else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
            countryService.delete(expectedId.getDtoEntity().getId());
        }
    }

	private void testEntity(CountryDTO countryDTO ) {
		testEntity(countryDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(CountryDTO countryDTO  , String name) {
        Assert.assertNotNull("Country is null.", countryDTO);        
        Assert.assertEquals("Name ", name, countryDTO.getName());
    }

}
