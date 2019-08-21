package com.codex.ecam.service.admin.cmmssettings.priorities;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.result.admin.PriorityResult;
import com.codex.ecam.service.admin.api.PriorityService;
@Component
public class PrioritiesServiceUpdateTestCase extends TestCase{

	public static enum FieldName {NAME,DESCRIPTION,COLOR};

	@Autowired
	private PriorityService prioritiesService;

	protected final String UPDATE_ENTITY_NAME = "name";

	protected final String UPDATE_ENTITY_VALUE = "value";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		PriorityResult result = null;
		try {

			PriorityDTO prioritiesDTO = PrioritiesDummyData.getInstance().getDummyDTOPrioritiesOne();
			result = prioritiesService.save(prioritiesDTO);
			prioritiesDTO.setId(result.getDtoEntity().getId());

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), prioritiesDTO);

			prioritiesService.save(prioritiesDTO);
			PriorityDTO updatedprioritiesDTO = prioritiesService.findById(result.getDtoEntity().getId());

			Assert.assertNotNull(" asset Category is null.", updatedprioritiesDTO);
			Assert.assertTrue(" asset Category not updated.", isFieldUpdated(updatedprioritiesDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			prioritiesService.delete(result.getDtoEntity().getId());
		}
	}

	private boolean isFieldUpdated(PriorityDTO prioritiesDTO, String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return prioritiesDTO.getName().equals(value);

		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			return prioritiesDTO.getDescription().equals(value);

		} else if (name.equals(FieldName.COLOR.name())) {
			return prioritiesDTO.getColor().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value, PriorityDTO prioritiesDTO) {

		if (name.equals(FieldName.NAME.name())) {
			prioritiesDTO.setName((String) value);

		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			prioritiesDTO.setDescription((String) value);

		} else if (name.equals(FieldName.COLOR.name())) {
			prioritiesDTO.setColor((String) value);
		}
	}
}
