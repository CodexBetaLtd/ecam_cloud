package com.codex.ecam.service.admin.cmmssettings.bussinestype;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.biz.business.BussinessTypeDTO;
import com.codex.ecam.result.admin.BusinessTypeResult;
import com.codex.ecam.service.biz.api.BusinessTypeService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class BussinessTypeServiceFindbyIdTestCase    extends TestCase{

	// conditions
	protected final String CONDITION_EXPECTED_NAME = "businessTypeDefinitionName";
	protected final String RESULT_IS_ERROR = "isError";
	@Autowired
	private BusinessTypeService businessTypeService;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
        BusinessTypeResult expectedId = null;
        try {
            BussinessTypeDTO bussinessTypeDTO = BussinessTypeDummyData.getInstance().getDummyDTOBussinessTypeOne();
			expectedId = businessTypeService.save(bussinessTypeDTO);

            BussinessTypeDTO bussinessType = businessTypeService.findById(expectedId.getDtoEntity().getId());

            if (bussinessType != null) {
                Assert.assertEquals("Asset category should be " + expectedId + ".", expectedId, bussinessType.getId());
				testEntity(bussinessType);
			} else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
            businessTypeService.delete(expectedId.getDtoEntity().getId());
        }
    }

	private void testEntity(BussinessTypeDTO bussinessTypeDTO) {
		testEntity(bussinessTypeDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(BussinessTypeDTO bussinessTypeDTO, String name) {
        Assert.assertNotNull("Bussiness Type is null.", bussinessTypeDTO);        
        Assert.assertEquals("Name ", name, bussinessTypeDTO.getBusinessTypeDefinitionName());
    }

}
