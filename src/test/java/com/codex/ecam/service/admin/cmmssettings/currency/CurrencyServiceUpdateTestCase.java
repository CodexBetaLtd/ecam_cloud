package com.codex.ecam.service.admin.cmmssettings.currency;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.result.admin.CurrencyResult;
import com.codex.ecam.service.admin.api.CurrencyService;
@Component
public class CurrencyServiceUpdateTestCase extends TestCase{

	public static enum FieldName {NAME,SYMBOLE};

	@Autowired
	private CurrencyService currencyService;

	protected final String UPDATE_ENTITY_NAME = "name";

	protected final String UPDATE_ENTITY_VALUE = "value";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		CurrencyResult id = null;
		try {

			CurrencyDTO currencyDTO = CurrencyDummyData.getInstance().getDummyDTOCurrencyOne();
			id = currencyService.save(currencyDTO);
			currencyDTO.setId(id.getDtoEntity().getId());

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), currencyDTO);

			currencyService.save(currencyDTO);
			CurrencyDTO updatedcurrencyDTO = currencyService.findById(id.getDtoEntity().getId());

			Assert.assertNotNull(" Currency is null.", updatedcurrencyDTO);
			Assert.assertTrue(" Currency not updated.", isFieldUpdated(updatedcurrencyDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			currencyService.delete(id.getDtoEntity().getId());
		}
	}

	private boolean isFieldUpdated(CurrencyDTO currencyDTO , String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return currencyDTO.getName().equals(value);

		} else if (name.equals(FieldName.SYMBOLE.name())) {
			return currencyDTO.getSymbol().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value, CurrencyDTO currencyDTO) {

		if (name.equals(FieldName.NAME.name())) {
			currencyDTO.setName((String) value);

		} else if (name.equals(FieldName.SYMBOLE.name())) {
			currencyDTO.setSymbol((String) value);

		}
	}
}
