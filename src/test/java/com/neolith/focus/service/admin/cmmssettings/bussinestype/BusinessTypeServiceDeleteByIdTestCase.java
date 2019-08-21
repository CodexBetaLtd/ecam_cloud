package com.neolith.focus.service.admin.cmmssettings.bussinestype;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.biz.business.BussinessTypeDTO;
import com.neolith.focus.result.admin.BusinessTypeResult;
import com.neolith.focus.service.biz.api.BusinessTypeService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class BusinessTypeServiceDeleteByIdTestCase extends TestCase{


    protected final String RESULT_IS_ERROR = "isError";
    @Autowired
	private BusinessTypeService  businessTypeService;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {
			
			BussinessTypeDTO bussinessTypeDTO = BussinessTypeDummyData.getInstance().getDummyDTOBussinessTypeOne();

            BusinessTypeResult expectedId = businessTypeService.save(bussinessTypeDTO);
            businessTypeService.delete(expectedId.getDtoEntity().getId());

            Assert.assertNull("Asset Category is not null.", businessTypeService.findById(expectedId.getDtoEntity().getId()));

		} catch (Exception e) {
			isError = true;
		}
	}

}
