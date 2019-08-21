package com.neolith.focus.service.admin.cmmssettings.certification;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.CertificationDTO;
import com.neolith.focus.result.admin.CertificationResult;
import com.neolith.focus.service.admin.api.CertificationService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class CertificationServiceCreateTestCase extends TestCase{

	@Autowired
	private  CertificationService  certificationService;
	
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
        CertificationResult expectedId = null;
        try {
            CertificationDTO certificationDTO = ( CertificationDTO) getTestCondition(CONDITION_SAVE_ENTITY);
			expectedId = certificationService.save(certificationDTO);

            Assert.assertNotNull("Certification is null.", certificationService.findById(expectedId.getDtoEntity().getId()));

        } catch (Exception e) {
            isError = true;
		} finally {
            certificationService.delete(expectedId.getDtoEntity().getId());
        }
    }

}
