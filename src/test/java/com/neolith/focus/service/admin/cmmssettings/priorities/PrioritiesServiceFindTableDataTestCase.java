package com.neolith.focus.service.admin.cmmssettings.priorities;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.PriorityDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.admin.api.PriorityService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class PrioritiesServiceFindTableDataTestCase  extends TestCase {

	@Autowired
    private PriorityService prioritiesService;

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

            prioritiesService.saveAll(PrioritiesDummyData.getInstance().getAllDummyData());

            DataTablesOutput<PriorityDTO> out = prioritiesService.findAll(new FocusDataTablesInput());

            Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
            String expectedFirstName = (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME);
			String expectedSecondName = (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME);
	
			Assert.assertTrue("priorities list is empty.", !out.getData().isEmpty());
			Assert.assertEquals("Number of priorities  should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));

            for (PriorityDTO prioritiesDTO : out.getData()) {
                if (expectedFirstName.equals(prioritiesDTO.getName())) {
                    testPrioritiesOne(prioritiesDTO);
	            } else if (expectedSecondName.equals(prioritiesDTO.getId())) {
	            	testPrioritiesDTOTwo(prioritiesDTO);
	            }
	        }        
		} catch (Exception e) {
			isError = true;
		} finally {
			prioritiesService.deleteAll();
		}
	}

    private void testPrioritiesDTOTwo(PriorityDTO prioritiesDTO) {
        testPriorities(prioritiesDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
    }

    private void testPrioritiesOne(PriorityDTO prioritiesDTO) {
        testPriorities(prioritiesDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
    }

    private void testPriorities(PriorityDTO prioritiesDTO, String name) {
        Assert.assertNotNull("priorities is null.", prioritiesDTO);        
        Assert.assertEquals("firstName", name, prioritiesDTO.getName());
    }
}
