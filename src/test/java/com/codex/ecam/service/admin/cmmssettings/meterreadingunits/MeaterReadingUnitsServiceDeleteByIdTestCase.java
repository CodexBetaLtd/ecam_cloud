package com.codex.ecam.service.admin.cmmssettings.meterreadingunits;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.MeterReadingUnitDTO;
import com.codex.ecam.result.admin.MeterReadingUnitResult;
import com.codex.ecam.service.admin.api.MeterReadingUnitService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class MeaterReadingUnitsServiceDeleteByIdTestCase extends TestCase{
	

	@Autowired
	private MeterReadingUnitService meaterReadingUnitsService;

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {

			MeterReadingUnitDTO meterReadingUnitsDTO = MeterReadingUnitsDummyData.getInstance().getDummyDTOMeterReadingUnitseOne();

            MeterReadingUnitResult expectedId = meaterReadingUnitsService.save(meterReadingUnitsDTO);
            meaterReadingUnitsService.delete(expectedId.getDtoEntity().getId());

            Assert.assertNull("Meter Reading Units is not null.", meaterReadingUnitsService.findById(expectedId.getDtoEntity().getId()));

		} catch (Exception e) {
			isError = true;
		}
	}

}
