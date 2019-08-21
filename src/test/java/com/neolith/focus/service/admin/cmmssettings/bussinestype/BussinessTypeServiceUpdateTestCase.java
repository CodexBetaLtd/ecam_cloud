package com.neolith.focus.service.admin.cmmssettings.bussinestype;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.biz.business.BussinessTypeDTO;
import com.neolith.focus.result.admin.BusinessTypeResult;
import com.neolith.focus.service.biz.api.BusinessTypeService;
@Component
public class BussinessTypeServiceUpdateTestCase extends TestCase{

	protected final String UPDATE_ENTITY_NAME = "name";
	;
	protected final String UPDATE_ENTITY_VALUE = "value";
	protected final String RESULT_IS_ERROR = "isError";
	@Autowired
	private BusinessTypeService businessTypeService;
	private boolean isError;

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		BusinessTypeResult id = null;
		try {

			BussinessTypeDTO bussinessTypeDTO = BussinessTypeDummyData.getInstance().getDummyDTOBussinessTypeOne();
			id = businessTypeService.save(bussinessTypeDTO);
			bussinessTypeDTO.setId(id.getDtoEntity().getId());

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), bussinessTypeDTO);

			businessTypeService.save(bussinessTypeDTO);
			BussinessTypeDTO updatedbussinessTypeDTO = businessTypeService.findById(id.getDtoEntity().getId());

			Assert.assertNotNull(" asset Category is null.", updatedbussinessTypeDTO);
			Assert.assertTrue(" asset Category not updated.", isFieldUpdated(updatedbussinessTypeDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			businessTypeService.delete(id.getDtoEntity().getId());
		}
	}

	private boolean isFieldUpdated( BussinessTypeDTO bussinessTypeDTO, String name, Object value) {

		if (name.equals(FieldName.ALL_PARENT.name())) {
			return bussinessTypeDTO.getAllParent().equals(value);

		} else if (name.equals(FieldName.BUSINESS_TYPE_DEFINITION_NAME.name())) {
			return bussinessTypeDTO.getBusinessTypeDefinitionName().equals(value);

		} else if (name.equals(FieldName.BUSINESS_TYPE_DEFINITION_SHORT.name())) {
			return bussinessTypeDTO.getBusinessTypeDefinitionShort().equals(value);

		}
		else if (name.equals(FieldName.DEFINABLE.name())) {
			return bussinessTypeDTO.getDefinable().equals(value);

		}
		else if (name.equals(FieldName.DEFAULT_PARENT.name())) {
			return bussinessTypeDTO.getDefaultParentId().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value,	 BussinessTypeDTO bussinessTypeDTO) {

		if (name.equals(FieldName.ALL_PARENT.name())) {
			bussinessTypeDTO.setAllParent((String) value);

		} else if (name.equals(FieldName.BUSINESS_TYPE_DEFINITION_NAME.name())) {
			bussinessTypeDTO.setBusinessTypeDefinitionName((String) value);

		} else if (name.equals(FieldName.BUSINESS_TYPE_DEFINITION_SHORT.name())) {
			bussinessTypeDTO.setBusinessTypeDefinitionShort((String) value);
		}
		else if (name.equals(FieldName.DEFINABLE.name())) {
			bussinessTypeDTO.setDefinable((Boolean) value);

		} else if (name.equals(FieldName.DEFAULT_PARENT.name())) {
			bussinessTypeDTO.setDefaultParentId((Boolean) value);
		}
	}

	public static enum FieldName {ALL_PARENT, BUSINESS_TYPE_DEFINITION_NAME, BUSINESS_TYPE_DEFINITION_SHORT, DEFINABLE, DEFAULT_PARENT}
}
