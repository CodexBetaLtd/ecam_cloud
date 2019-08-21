package com.codex.ecam.service.admin.cmmssettings.maintainacetype;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.MaintenanceTypeService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class MaintainaceTypeServiceFindTableDataTestCase  extends TestCase {

	@Autowired
    private MaintenanceTypeService maintainaceTypeService;

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

            maintainaceTypeService.saveAll(MaintainanceTypeDummyData.getInstance().getAllDummyData());

            DataTablesOutput<MaintenanceTypeDTO> out = maintainaceTypeService.findAll(new FocusDataTablesInput());

            Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
            String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Mainatainace Type list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Mainatainace Type  should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));

            for (MaintenanceTypeDTO maintainanceTypesDTO : out.getData()) {
                if (expectedFirstName.equals(maintainanceTypesDTO.getName())) {
                    testMaintainanceTypesOne(maintainanceTypesDTO);
	            } else if (expectedSecondName.equals(maintainanceTypesDTO.getId())) {
	            	testMaintainanceTypesTwo(maintainanceTypesDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			maintainaceTypeService.deleteAll();
		}
	}

    private void testMaintainanceTypesTwo(MaintenanceTypeDTO maintainanceTypesDTO) {
        testMaintainanceTypes(maintainanceTypesDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
    }

    private void testMaintainanceTypesOne(MaintenanceTypeDTO maintainanceTypesDTO) {
        testMaintainanceTypes(maintainanceTypesDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
    }

    private void testMaintainanceTypes(MaintenanceTypeDTO maintainanceTypesDTO, String name) {
        Assert.assertNotNull("Asset Event Type is null.", maintainanceTypesDTO);        
        Assert.assertEquals("firstName", name, maintainanceTypesDTO.getName());
    }
}
