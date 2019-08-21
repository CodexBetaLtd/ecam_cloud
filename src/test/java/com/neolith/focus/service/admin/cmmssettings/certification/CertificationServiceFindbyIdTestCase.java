package com.neolith.focus.service.admin.cmmssettings.certification;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.CertificationDTO;
import com.neolith.focus.result.admin.CertificationResult;
import com.neolith.focus.service.admin.api.CertificationService;
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
