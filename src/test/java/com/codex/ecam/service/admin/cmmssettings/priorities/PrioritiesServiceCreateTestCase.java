package com.codex.ecam.service.admin.cmmssettings.priorities;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.result.admin.PriorityResult;
import com.codex.ecam.service.admin.api.PriorityService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class PrioritiesServiceCreateTestCase extends TestCase{

	@Autowired
    private PriorityService prioritiesService;

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
        PriorityResult expectedId = null;
        try {
            PriorityDTO prioritiesDTO = (PriorityDTO) getTestCondition(CONDITION_SAVE_ENTITY);
            expectedId = prioritiesService.save(prioritiesDTO);

            Assert.assertNotNull("priorities is null.", prioritiesService.findById(expectedId.getDtoEntity().getId()));

        } catch (Exception e) {
            isError = true;
		} finally {
            prioritiesService.delete(expectedId.getDtoEntity().getId());
        }
    }

}
