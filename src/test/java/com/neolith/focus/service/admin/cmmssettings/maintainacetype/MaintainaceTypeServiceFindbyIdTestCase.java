package com.neolith.focus.service.admin.cmmssettings.maintainacetype;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.MaintenanceTypeDTO;
import com.neolith.focus.result.admin.MaintenanceTypeResult;
import com.neolith.focus.service.admin.api.MaintenanceTypeService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MaintainaceTypeServiceFindbyIdTestCase    extends TestCase{

	@Autowired
    private MaintenanceTypeService maintainaceTypeService;

    // conditions
    protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
        MaintenanceTypeResult expectedId = null;
        try {
            MaintenanceTypeDTO maintainanceTypesDTO = MaintainanceTypeDummyData.getInstance().getDummyDTOMaintainanceOne();
            expectedId = maintainaceTypeService.save(maintainanceTypesDTO);

            MaintenanceTypeDTO maintainanceTypes = maintainaceTypeService.findById(expectedId.getDtoEntity().getId());

            if (maintainanceTypes != null) {
                Assert.assertEquals("Asset category should be " + expectedId + ".", expectedId.getDtoEntity().getId(), maintainanceTypes.getId());
                testEntity(maintainanceTypes);
            } else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
            maintainaceTypeService.delete(expectedId.getDtoEntity().getId());
        }
    }

    private void testEntity(MaintenanceTypeDTO maintainanceTypesDTO) {
        testEntity(maintainanceTypesDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
    }

    private void testEntity(MaintenanceTypeDTO maintainanceTypesDTO, String name) {
        Assert.assertNotNull("Maintainance Type is null.", maintainanceTypesDTO);        
        Assert.assertEquals("Name ", name, maintainanceTypesDTO.getName());
    }

}
