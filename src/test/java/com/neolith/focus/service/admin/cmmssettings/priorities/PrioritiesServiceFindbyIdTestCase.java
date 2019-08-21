package com.neolith.focus.service.admin.cmmssettings.priorities;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.PriorityDTO;
import com.neolith.focus.result.admin.PriorityResult;
import com.neolith.focus.service.admin.api.PriorityService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PrioritiesServiceFindbyIdTestCase    extends TestCase{

	@Autowired
    private PriorityService prioritiesService;

    // conditions
    protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
        PriorityResult expectedId = null;
        try {
            PriorityDTO prioritiesDTO = PrioritiesDummyData.getInstance().getDummyDTOPrioritiesOne();
            expectedId = prioritiesService.save(prioritiesDTO);

            PriorityDTO priorities = prioritiesService.findById(expectedId.getDtoEntity().getId());

            if (priorities != null) {
                Assert.assertEquals("priorities should be " + expectedId + ".", expectedId.getDtoEntity().getId(), priorities.getId());
                testEntity(priorities);
            } else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
            prioritiesService.delete(expectedId.getDtoEntity().getId());
        }
    }

    private void testEntity(PriorityDTO prioritiesDTO) {
        testEntity(prioritiesDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
    }

    private void testEntity(PriorityDTO prioritiesDTO, String name) {
        Assert.assertNotNull("priorities is null.", prioritiesDTO);        
        Assert.assertEquals("Name ", name, prioritiesDTO.getName());
    }

}
