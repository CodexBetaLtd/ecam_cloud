package com.codex.ecam.service.admin.cmmssettings.businessclassification;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.result.admin.BusinessClassificationResult;
import com.codex.ecam.service.biz.api.BusinessClassificationService;
@Component
public class BussinessClassificationServiceUpdateTestCase extends TestCase{

	protected final String UPDATE_ENTITY_NAME = "name";
	;
	protected final String UPDATE_ENTITY_VALUE = "value";
	protected final String RESULT_IS_ERROR = "isError";
	@Autowired
	private BusinessClassificationService businessClassificationService;
	private boolean isError;

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		BusinessClassificationResult id = null;
		try {

			BusinessClassificationDTO businessClassificationDTO= BussinessClassificationDummyData.getInstance().getDummyDTOBussinessClassificationOne();
			id = businessClassificationService.save(businessClassificationDTO);
			businessClassificationDTO.setId(id.getDtoEntity().getId());

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), businessClassificationDTO);

			businessClassificationService.save(businessClassificationDTO);
			BusinessClassificationDTO updatedbusinessClassificationDTO = businessClassificationService.findById(id.getDtoEntity().getId());

			Assert.assertNotNull(" asset Category is null.", updatedbusinessClassificationDTO);
			Assert.assertTrue(" asset Category not updated.", isFieldUpdated(updatedbusinessClassificationDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			businessClassificationService.delete(id.getDtoEntity().getId());
		}
	}

	private boolean isFieldUpdated(BusinessClassificationDTO businessClassificationDTO, String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return businessClassificationDTO.getName().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value, BusinessClassificationDTO businessClassificationDTO) {

		if (name.equals(FieldName.NAME.name())) {
			businessClassificationDTO.setName((String) value);

		}
	}

	public static enum FieldName {NAME}
}
