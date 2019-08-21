package com.codex.ecam.service.admin.cmmssettings.currency;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.result.admin.CurrencyResult;
import com.codex.ecam.service.admin.api.CurrencyService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class CurrencyServiceDeleteByIdTestCase extends TestCase{
	

	@Autowired
	private CurrencyService  currencyService;
	
	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {
			
			CurrencyDTO currencyDTO = CurrencyDummyData.getInstance().getDummyDTOCurrencyOne();

            CurrencyResult expectedId = currencyService.save(currencyDTO);
            currencyService.delete(expectedId.getDtoEntity().getId());

            Assert.assertNull("Currency is not null.", currencyService.findById(expectedId.getDtoEntity().getId()));

		} catch (Exception e) {
			isError = true;
		}
	}

}
