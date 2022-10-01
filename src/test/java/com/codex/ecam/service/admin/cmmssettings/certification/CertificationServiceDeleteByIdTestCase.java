package com.codex.ecam.service.admin.cmmssettings.certification;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.result.admin.CertificationResult;
import com.codex.ecam.service.admin.api.CertificationService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class CertificationServiceDeleteByIdTestCase extends TestCase{
	

	@Autowired
	private CertificationService  certificationService;
	
	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {
			
			CertificationDTO certificationDTO = CertificationDummyData.getInstance().getDummyDTOCertificationOne();

            CertificationResult expectedId = certificationService.save(certificationDTO);
            certificationService.delete(expectedId.getDtoEntity().getId());

            Assert.assertNull("Certification is not null.", certificationService.findById(expectedId.getDtoEntity().getId()));

		} catch (Exception e) {
			isError = true;
		}
	}

}
