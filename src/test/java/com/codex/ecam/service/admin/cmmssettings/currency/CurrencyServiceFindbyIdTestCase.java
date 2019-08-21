package com.codex.ecam.service.admin.cmmssettings.currency;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.result.admin.CurrencyResult;
import com.codex.ecam.service.admin.api.CurrencyService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CurrencyServiceFindbyIdTestCase    extends TestCase{

	@Autowired
	private CurrencyService currencyService;
	
	// conditions
	protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
        CurrencyResult expectedId = null;
        try {
            CurrencyDTO currencyDTO = CurrencyDummyData.getInstance().getDummyDTOCurrencyOne();
			expectedId = currencyService.save(currencyDTO);

            CurrencyDTO currency = currencyService.findById(expectedId.getDtoEntity().getId());

            if (currency != null) {
                Assert.assertEquals("Asset category should be " + expectedId + ".", expectedId.getDtoEntity().getId(), currency.getId());
                testEntity(currency);
            } else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
            currencyService.delete(expectedId.getDtoEntity().getId());
        }
    }

	private void testEntity(CurrencyDTO currencyDTO) {
		testEntity(currencyDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(CurrencyDTO currencyDTO , String name) {
        Assert.assertNotNull("Currency is null.", currencyDTO);        
        Assert.assertEquals("Name ", name, currencyDTO.getName());
    }

}
