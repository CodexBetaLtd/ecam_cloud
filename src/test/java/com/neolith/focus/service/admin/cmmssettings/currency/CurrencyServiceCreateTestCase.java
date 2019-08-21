package com.neolith.focus.service.admin.cmmssettings.currency;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.CurrencyDTO;
import com.neolith.focus.result.admin.CurrencyResult;
import com.neolith.focus.service.admin.api.CurrencyService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class CurrencyServiceCreateTestCase extends TestCase{

	@Autowired
	private  CurrencyService  currencyService;
	
	// conditions
	protected final String CONDITION_SAVE_ENTITY = "entity";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
        CurrencyResult expectedId = null;
        try {
            CurrencyDTO currencyDTO = ( CurrencyDTO) getTestCondition(CONDITION_SAVE_ENTITY);
			expectedId = currencyService.save(currencyDTO);

            Assert.assertNotNull("Currency  is null.", currencyService.findById(expectedId.getDtoEntity().getId()));

        } catch (Exception e) {
            isError = true;
		} finally {
            currencyService.delete(expectedId.getDtoEntity().getId());
        }
    }

}
