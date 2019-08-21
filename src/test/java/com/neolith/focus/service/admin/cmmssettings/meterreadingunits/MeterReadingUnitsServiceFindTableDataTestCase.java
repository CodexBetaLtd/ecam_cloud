package com.neolith.focus.service.admin.cmmssettings.meterreadingunits;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.MeterReadingUnitDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.admin.api.MeterReadingUnitService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class MeterReadingUnitsServiceFindTableDataTestCase  extends TestCase {

	@Autowired
    private MeterReadingUnitService meaterReadingUnitsService;

    // conditions
    protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {

            meaterReadingUnitsService.saveAll(MeterReadingUnitsDummyData.getInstance().getAllDummyData());

            DataTablesOutput<MeterReadingUnitDTO> out = meaterReadingUnitsService.findAll(new FocusDataTablesInput());

            Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
            String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Meter reading units list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Meter reading units  should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));

            for (MeterReadingUnitDTO meterReadingUnitsDTO : out.getData()) {
                if (expectedFirstName.equals(meterReadingUnitsDTO.getName())) {
                    testmeaterReadingUnitsOne(meterReadingUnitsDTO);
	            } else if (expectedSecondName.equals(meterReadingUnitsDTO.getId())) {
	            	testmeaterReadingUnitsTwo(meterReadingUnitsDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			meaterReadingUnitsService.deleteAll();
		}
	}

    private void testmeaterReadingUnitsTwo(MeterReadingUnitDTO meterReadingUnitsDTO) {
        testMeaterReadingUnits(meterReadingUnitsDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
    }

    private void testmeaterReadingUnitsOne(MeterReadingUnitDTO meterReadingUnitsDTO) {
        testMeaterReadingUnits(meterReadingUnitsDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
    }

    private void testMeaterReadingUnits(MeterReadingUnitDTO meterReadingUnitsDTO, String name) {
        Assert.assertNotNull("meter Reading Units is null.", meterReadingUnitsDTO);        
        Assert.assertEquals("firstName", name, meterReadingUnitsDTO.getName());
    }
}
