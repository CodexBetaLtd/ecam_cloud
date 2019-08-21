package com.neolith.focus.service.admin.cmmssettings.maintainacetype;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.MaintenanceTypeDTO;
import com.neolith.focus.result.admin.MaintenanceTypeResult;
import com.neolith.focus.service.admin.api.MaintenanceTypeService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class MaintainanceTypeServiceDeleteByIdTestCase extends TestCase{
	

	@Autowired
    private MaintenanceTypeService maintainaceTypeService;

    protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {

            MaintenanceTypeDTO maintainanceTypesDTO = MaintainanceTypeDummyData.getInstance().getDummyDTOMaintainanceOne();

            MaintenanceTypeResult expectedId = maintainaceTypeService.save(maintainanceTypesDTO);
            maintainaceTypeService.delete(expectedId.getDtoEntity().getId());

            Assert.assertNull("Maintainace Type is not null.", maintainaceTypeService.findById(expectedId.getDtoEntity().getId()));

		} catch (Exception e) {
			isError = true;
		}
	}

}
