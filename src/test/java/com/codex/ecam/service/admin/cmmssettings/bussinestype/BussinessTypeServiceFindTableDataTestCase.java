package com.codex.ecam.service.admin.cmmssettings.bussinestype;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.biz.business.BussinessTypeDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.biz.api.BusinessTypeService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class BussinessTypeServiceFindTableDataTestCase  extends TestCase {

	// conditions
	protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";
	protected final String RESULT_IS_ERROR = "isError";
    @Autowired
    private BusinessTypeService businessTypeService;
    private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			
			businessTypeService.saveAll(BussinessTypeDummyData.getInstance().getAllDummyData());			
			
			DataTablesOutput<BussinessTypeDTO> out = businessTypeService.findAll(new FocusDataTablesInput());
		
			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("Bussiness Type list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of Bussiness Type  should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));
	        
	        for (BussinessTypeDTO bussinessTypeDTO : out.getData()) {	            
	            if (expectedFirstName.equals(bussinessTypeDTO.getBusinessTypeDefinitionName())) {
	            	testBussinessTypeOne(bussinessTypeDTO);
	            } else if (expectedSecondName.equals(bussinessTypeDTO.getId())) {
	            	testBussinessTypeTwo(bussinessTypeDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			businessTypeService.deleteAll();
		}
	}

	private void testBussinessTypeTwo(BussinessTypeDTO bussinessTypeDTO) {
		testAssetCategory(bussinessTypeDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testBussinessTypeOne(BussinessTypeDTO bussinessTypeDTO) {
		testAssetCategory(bussinessTypeDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}
	
    private void testAssetCategory(BussinessTypeDTO bussinessTypeDTO, String name) {
        Assert.assertNotNull("Bussiness TypeType is null.", bussinessTypeDTO);        
        Assert.assertEquals("firstName", name, bussinessTypeDTO.getBusinessTypeDefinitionName());
    }
}
