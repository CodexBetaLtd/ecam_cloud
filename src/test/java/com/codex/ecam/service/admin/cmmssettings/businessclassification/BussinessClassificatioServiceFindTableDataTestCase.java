package com.codex.ecam.service.admin.cmmssettings.businessclassification;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.biz.api.BusinessClassificationService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class BussinessClassificatioServiceFindTableDataTestCase  extends TestCase {

	// conditions
	protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";
	protected final String RESULT_IS_ERROR = "isError";
	@Autowired
	private BusinessClassificationService businessClassificationService;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			
			businessClassificationService.saveAll(BussinessClassificationDummyData.getInstance().getAllDummyData());			
			
			DataTablesOutput<BusinessClassificationDTO> out = businessClassificationService.findAllDataTable(new FocusDataTablesInput());
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Asset Event Type list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Asset Event Type  should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));
	        
	        for (BusinessClassificationDTO businessClassificationDTO : out.getData()) {	            
	            if (expectedFirstName.equals(businessClassificationDTO.getName())) {
	            	testBusinessClassificationeOne(businessClassificationDTO);
	            } else if (expectedSecondName.equals(businessClassificationDTO.getId())) {
	            	testBusinessClassificationTwo(businessClassificationDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			businessClassificationService.deleteAll();
		}
	}

	private void testBusinessClassificationTwo(BusinessClassificationDTO businessClassificationDTO) {
		testBusinessClassification(businessClassificationDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testBusinessClassificationeOne(BusinessClassificationDTO businessClassificationDTO) {
		testBusinessClassification(businessClassificationDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}
	
    private void testBusinessClassification(BusinessClassificationDTO businessClassificationDTO, String name) {
        Assert.assertNotNull("Asset Event Type is null.", businessClassificationDTO);        
        Assert.assertEquals("firstName", name, businessClassificationDTO.getName());
    }
}
