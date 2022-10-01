package com.codex.ecam.service.admin.business;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.biz.business.BusinessDTO;
import com.codex.ecam.result.admin.BusinessResult;
import com.codex.ecam.service.biz.api.BusinessService;

@Component
public class BusinessServiceUpdateTestCase extends TestCase {

	protected final String UPDATE_ENTITY_NAME = "name";
	;
	protected final String UPDATE_ENTITY_VALUE = "value";
	protected final String RESULT_IS_ERROR = "isError";
	@Autowired
	private BusinessService businessService;
	private boolean isError;

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		Integer id = null;
		try {

			final BusinessDTO business = BusinessDummyData.getInstance().getDummyDTOBusinessOne();

			final BusinessResult result = businessService.save(business);
			id = result.getDomainEntity().getId();
			business.setId(id);

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), business);

			businessService.save(business);
			final BusinessDTO updatedBusiness = businessService.findById(id);

			Assert.assertNotNull("Business is null.", updatedBusiness);
			Assert.assertTrue("Business not updated.", isFieldUpdated(updatedBusiness, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (final Exception e) {
			isError = true;
		} finally {
			businessService.delete(id);
		}
	}

	private boolean isFieldUpdated(BusinessDTO business, String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return business.getName().equals(value);

		} else if (name.equals(FieldName.CODE.name())) {
			return business.getCode().equals(value);

		} else if (name.equals(FieldName.PHONE.name())) {
			return business.getPhone().equals(value);

		} else if (name.equals(FieldName.PHONE2.name())) {
			return business.getPhone2().equals(value);

		} else if (name.equals(FieldName.FAX.name())) {
			return business.getFax().equals(value);

		} else if (name.equals(FieldName.WEBSITE.name())) {
			return business.getWebSite().equals(value);

		} else if (name.equals(FieldName.PRIMARY_EMAIL.name())) {
			return business.getPrimaryEmail().equals(value);

		} else if (name.equals(FieldName.SECONDARY_EMAIL.name())) {
			return business.getSecondaryEmail().equals(value);

		} else if (name.equals(FieldName.NOTES.name())) {
			return business.getNotes().equals(value);

		} else if (name.equals(FieldName.BUSINESS_CLASSIFICATION_ID.name())) {
			return business.getBusinessClassificationId().equals(value);

		} else if (name.equals(FieldName.CURRENCY_ID.name())) {
			return business.getCurrencyId().equals(value);

		} else if (name.equals(FieldName.ADDRESS.name())) {
			return business.getAddress().equals(value);

		} else if (name.equals(FieldName.CITY.name())) {
			return business.getCity().equals(value);

		} else if (name.equals(FieldName.PROVINCE.name())) {
			return business.getProvince().equals(value);

		} else if (name.equals(FieldName.POSTAL_CODE.name())) {
			return business.getPostalCode().equals(value);

		} else if (name.equals(FieldName.COUNTRY_ID.name())) {
			return business.getCountryId().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value, BusinessDTO business) {

		if (name.equals(FieldName.NAME.name())) {
			business.setName((String) value);

		} else if (name.equals(FieldName.CODE.name())) {
			business.setCode((String) value);

		} else if (name.equals(FieldName.PHONE.name())) {
			business.setPhone((String) value);

		} else if (name.equals(FieldName.PHONE2.name())) {
			business.setPhone2((String) value);

		} else if (name.equals(FieldName.FAX.name())) {
			business.setFax((String) value);

		} else if (name.equals(FieldName.WEBSITE.name())) {
			business.setWebSite((String) value);

		} else if (name.equals(FieldName.PRIMARY_EMAIL.name())) {
			business.setPrimaryEmail((String) value);

		} else if (name.equals(FieldName.SECONDARY_EMAIL.name())) {
			business.setSecondaryEmail((String) value);

		} else if (name.equals(FieldName.NOTES.name())) {
			business.setNotes((String) value);

		} else if (name.equals(FieldName.BUSINESS_CLASSIFICATION_ID.name())) {
			business.setBusinessClassificationId((Integer) value);

		} else if (name.equals(FieldName.CURRENCY_ID.name())) {
			business.setCurrencyId((Integer) value);

		} else if (name.equals(FieldName.ADDRESS.name())) {
			business.setAddress((String) value);

		} else if (name.equals(FieldName.CITY.name())) {
			business.setCity((String) value);

		} else if (name.equals(FieldName.PROVINCE.name())) {
			business.setProvince((String) value);

		} else if (name.equals(FieldName.POSTAL_CODE.name())) {
			business.setPostalCode((String) value);

		} else if (name.equals(FieldName.COUNTRY_ID.name())) {
			business.setCountryId((Integer) value);

		}

	}

	public static enum FieldName {
		NONE, NAME, CODE, PHONE, PHONE2, FAX, WEBSITE, PRIMARY_EMAIL, SECONDARY_EMAIL, NOTES,
		BUSINESS_CLASSIFICATION_ID, CURRENCY_ID, ADDRESS, CITY, PROVINCE, POSTAL_CODE, COUNTRY_ID
	}

}