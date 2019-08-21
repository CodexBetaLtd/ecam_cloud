package com.neolith.focus.service.admin.cmmssettings.certification;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.CertificationDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.admin.api.CertificationService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class CertificationServiceFindTableDataTestCase  extends TestCase {

	@Autowired
	private  CertificationService certificationService;
	
	// conditions
	protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			
			certificationService.saveAll(CertificationDummyData.getInstance().getAllDummyData());			
			
			DataTablesOutput<CertificationDTO> out = certificationService.findAll(new FocusDataTablesInput());
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Certification list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Certification should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));
	        
	        for (CertificationDTO certificationDTO : out.getData()) {	            
	            if (expectedFirstName.equals(certificationDTO.getCertificationType())) {
	            	testCertificationOne(certificationDTO);
	            } else if (expectedSecondName.equals(certificationDTO.getId())) {
	            	testCertificationTwo(certificationDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			certificationService.deleteAll();
		}
	}

	private void testCertificationTwo(CertificationDTO certificationDTO) {
		testCertification(certificationDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testCertificationOne(CertificationDTO certificationDTO) {
		testCertification(certificationDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}
	
    private void testCertification(CertificationDTO certificationDTO, String name) {
        Assert.assertNotNull("Certification is null.", certificationDTO);        
        Assert.assertEquals("Certification type", name, certificationDTO.getCertificationType());
    }
}
