package com.neolith.focus.service.admin.cmmssettings.meterreadingunits;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.MeterReadingUnitDTO;
import com.neolith.focus.result.admin.MeterReadingUnitResult;
import com.neolith.focus.service.admin.api.MeterReadingUnitService;
@Component
public class MeterReadingUnitsServiceUpdateTestCase extends TestCase{

	public static enum FieldName {NAME,PRECISION,SYMBOL};

	@Autowired
	private MeterReadingUnitService meaterReadingUnitsService;

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
		MeterReadingUnitResult id = null;
		try {

			MeterReadingUnitDTO meterReadingUnitsDTO = MeterReadingUnitsDummyData.getInstance().getDummyDTOMeterReadingUnitseOne();
			id = meaterReadingUnitsService.save(meterReadingUnitsDTO);
			meterReadingUnitsDTO.setId(id.getDtoEntity().getId());

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), meterReadingUnitsDTO);

			meaterReadingUnitsService.save(meterReadingUnitsDTO);
			MeterReadingUnitDTO updatedmeterReadingUnitsDTO = meaterReadingUnitsService.findById(id.getDtoEntity().getId());

			Assert.assertNotNull(" asset Category is null.", updatedmeterReadingUnitsDTO);
			Assert.assertTrue(" asset Category not updated.", isFieldUpdated(updatedmeterReadingUnitsDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			meaterReadingUnitsService.delete(id.getDtoEntity().getId());
		}
	}

	private boolean isFieldUpdated(MeterReadingUnitDTO meterReadingUnitsDTO, String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return meterReadingUnitsDTO.getName().equals(value);

		} else if (name.equals(FieldName.PRECISION.name())) {
			return meterReadingUnitsDTO.getPrecision().equals(value);

		} else if (name.equals(FieldName.SYMBOL.name())) {
			return meterReadingUnitsDTO.getSymbol().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value, MeterReadingUnitDTO meterReadingUnitsDTO) {

		if (name.equals(FieldName.NAME.name())) {
			meterReadingUnitsDTO.setName((String) value);

		} else if (name.equals(FieldName.PRECISION.name())) {
			meterReadingUnitsDTO.setPrecision((Double) value);

		} else if (name.equals(FieldName.SYMBOL.name())) {
			meterReadingUnitsDTO.setSymbol((String) value);
		}
	}
}
