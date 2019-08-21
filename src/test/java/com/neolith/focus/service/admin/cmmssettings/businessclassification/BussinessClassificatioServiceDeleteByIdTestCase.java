package com.neolith.focus.service.admin.cmmssettings.businessclassification;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.biz.business.BusinessClassificationDTO;
import com.neolith.focus.result.admin.BusinessClassificationResult;
import com.neolith.focus.service.biz.api.BusinessClassificationService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class BussinessClassificatioServiceDeleteByIdTestCase extends TestCase{


    protected final String RESULT_IS_ERROR = "isError";
    @Autowired
	private BusinessClassificationService  businessClassificationService;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {
			
			BusinessClassificationDTO businessClassificationDTO = BussinessClassificationDummyData.getInstance().getDummyDTOBussinessClassificationOne();

            BusinessClassificationResult expectedId = businessClassificationService.save(businessClassificationDTO);
            businessClassificationService.delete(expectedId.getDtoEntity().getId());

            Assert.assertNull("Asset Category is not null.", businessClassificationService.findById(expectedId.getDtoEntity().getId()));

		} catch (Exception e) {
			isError = true;
		}
	}

}
