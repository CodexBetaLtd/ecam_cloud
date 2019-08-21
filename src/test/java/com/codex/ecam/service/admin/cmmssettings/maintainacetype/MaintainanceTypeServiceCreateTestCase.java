package com.codex.ecam.service.admin.cmmssettings.maintainacetype;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.result.admin.MaintenanceTypeResult;
import com.codex.ecam.service.admin.api.MaintenanceTypeService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class MaintainanceTypeServiceCreateTestCase extends TestCase{

	@Autowired
    private MaintenanceTypeService maintainaceTypeService;

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
        MaintenanceTypeResult expectedId = null;
        try {
            MaintenanceTypeDTO maintainanceTypesDTO = (MaintenanceTypeDTO) getTestCondition(CONDITION_SAVE_ENTITY);
            expectedId = maintainaceTypeService.save(maintainanceTypesDTO);

            Assert.assertNotNull("Maintainace TypeType is null.", maintainaceTypeService.findById(expectedId.getDtoEntity().getId()));

        } catch (Exception e) {
            isError = true;
		} finally {
            maintainaceTypeService.delete(expectedId.getDtoEntity().getId());
        }
    }

}
