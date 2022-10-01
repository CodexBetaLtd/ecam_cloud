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
public class MeaterReadingUnitsServiceCreateTestCase extends TestCase{

	@Autowired
    private MeterReadingUnitService meaterReadingUnitsService;

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
        MeterReadingUnitResult expectedId = null;
        try {
            MeterReadingUnitDTO meterReadingUnitsDTO = (MeterReadingUnitDTO) getTestCondition(CONDITION_SAVE_ENTITY);
            expectedId = meaterReadingUnitsService.save(meterReadingUnitsDTO);

            Assert.assertNotNull("Meater Reading Units is null.", meaterReadingUnitsService.findById(expectedId.getDtoEntity().getId()));

        } catch (Exception e) {
            isError = true;
		} finally {
            meaterReadingUnitsService.delete(expectedId.getDtoEntity().getId());
        }
    }

}
