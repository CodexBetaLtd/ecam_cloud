package com.neolith.focus.service.admin.cmmssettings.country;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.CountryDTO;
import com.neolith.focus.result.admin.CountryResult;
import com.neolith.focus.service.admin.api.CountryService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class CountryServiceDeleteByIdTestCase extends TestCase{
	

	@Autowired
	private CountryService  countryService;
	
	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {
			
			CountryDTO countryDTO = CountryDummyData.getInstance().getDummyDTOCountryOne();

            CountryResult expectedId = countryService.save(countryDTO);
            countryService.delete(expectedId.getDtoEntity().getId());

            Assert.assertNull("Country is not null.", countryService.findById(expectedId.getDtoEntity().getId()));

		} catch (Exception e) {
			isError = true;
		}
	}

}
