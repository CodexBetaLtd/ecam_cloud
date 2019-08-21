package com.neolith.focus.service.admin.cmmssettings.country;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.CountryDTO;
import com.neolith.focus.result.admin.CountryResult;
import com.neolith.focus.service.admin.api.CountryService;
@Component
public class CountryServiceUpdateTestCase extends TestCase{

	public static enum FieldName {NAME,SHORT_NAME};

	@Autowired
	private CountryService countryService;

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
		CountryResult id = null;
		try {

			CountryDTO countryDTO = CountryDummyData.getInstance().getDummyDTOCountryOne();
			id = countryService.save(countryDTO);
			countryDTO.setId(id.getDtoEntity().getId());

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), countryDTO);

			countryService.save(countryDTO);
			CountryDTO updatedcountryDTO = countryService.findById(id.getDtoEntity().getId());

			Assert.assertNotNull(" asset Category is null.", updatedcountryDTO);
			Assert.assertTrue(" asset Category not updated.", isFieldUpdated(updatedcountryDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			countryService.delete(id.getDtoEntity().getId());
		}
	}

	private boolean isFieldUpdated( CountryDTO countryDTO, String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return countryDTO.getName().equals(value);

		} else if (name.equals(FieldName.SHORT_NAME.name())) {
			return countryDTO.getShortName().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value,  CountryDTO countryDTO) {

		if (name.equals(FieldName.NAME.name())) {
			countryDTO.setName((String) value);

		} else if (name.equals(FieldName.SHORT_NAME.name())) {
			countryDTO.setShortName((String) value);

		}
	}
}
