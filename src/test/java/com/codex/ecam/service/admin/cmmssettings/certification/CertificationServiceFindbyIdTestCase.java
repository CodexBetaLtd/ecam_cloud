package com.codex.ecam.service.admin.cmmssettings.certification;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.result.admin.CertificationResult;
import com.codex.ecam.service.admin.api.CertificationService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CertificationServiceFindbyIdTestCase    extends TestCase{

	@Autowired
	private CertificationService certificationService;
	
	// conditions
	protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
        CertificationResult expectedId = null;
        try {
            CertificationDTO certificationDTO = CertificationDummyData.getInstance().getDummyDTOCertificationOne();
			expectedId = certificationService.save(certificationDTO);

            CertificationDTO certification = certificationService.findById(expectedId.getDtoEntity().getId());

            if (certification != null) {
                Assert.assertEquals("Asset category should be " + expectedId + ".", expectedId.getDtoEntity().getId(), certification.getId());
                testEntity(certification);
            } else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
            certificationService.delete(expectedId.getDtoEntity().getId());
        }
    }

	private void testEntity(CertificationDTO certificationDTO) {
		testEntity(certificationDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(CertificationDTO certificationDTO , String name) {
        Assert.assertNotNull("Certification type is null.", certificationDTO);        
        Assert.assertEquals("Name ", name, certificationDTO.getCertificationType());
    }

}
