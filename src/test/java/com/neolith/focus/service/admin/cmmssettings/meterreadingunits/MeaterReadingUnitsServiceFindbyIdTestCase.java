package com.neolith.focus.service.admin.cmmssettings.meterreadingunits;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.MeterReadingUnitDTO;
import com.neolith.focus.result.admin.MeterReadingUnitResult;
import com.neolith.focus.service.admin.api.MeterReadingUnitService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MeaterReadingUnitsServiceFindbyIdTestCase    extends TestCase{

	@Autowired
    private MeterReadingUnitService meaterReadingUnitsService;

    // conditions
    protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
        MeterReadingUnitResult expectedId = null;
        try {
            MeterReadingUnitDTO meterReadingUnitsDTO = MeterReadingUnitsDummyData.getInstance().getDummyDTOMeterReadingUnitseOne();
            expectedId = meaterReadingUnitsService.save(meterReadingUnitsDTO);

            MeterReadingUnitDTO meterReadingUnits = meaterReadingUnitsService.findById(expectedId.getDtoEntity().getId());

            if (meterReadingUnits != null) {
                Assert.assertEquals("Meter Reading Units should be " + expectedId + ".", expectedId.getDtoEntity().getId(), meterReadingUnits.getId());
                testEntity(meterReadingUnits);
            } else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
            meaterReadingUnitsService.delete(expectedId.getDtoEntity().getId());
        }
    }

    private void testEntity(MeterReadingUnitDTO meterReadingUnitsDTO) {
        testEntity(meterReadingUnitsDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
    }

    private void testEntity(MeterReadingUnitDTO meterReadingUnitsDTO, String name) {
        Assert.assertNotNull("Meter Reading Units is null.", meterReadingUnitsDTO);        
        Assert.assertEquals("Name ", name, meterReadingUnitsDTO.getName());
    }

}
