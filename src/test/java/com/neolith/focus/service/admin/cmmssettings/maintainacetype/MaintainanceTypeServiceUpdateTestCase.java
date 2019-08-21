package com.neolith.focus.service.admin.cmmssettings.maintainacetype;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.MaintenanceTypeDTO;
import com.neolith.focus.result.admin.MaintenanceTypeResult;
import com.neolith.focus.service.admin.api.MaintenanceTypeService;
@Component
public class MaintainanceTypeServiceUpdateTestCase extends TestCase{

	public static enum FieldName {NAME,DESCRIPTION,COLOR};

	@Autowired
	private MaintenanceTypeService maintainaceTypeService;

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
		MaintenanceTypeResult id = null;
		try {

			MaintenanceTypeDTO maintainanceTypesDTO = MaintainanceTypeDummyData.getInstance().getDummyDTOMaintainanceOne();
			id = maintainaceTypeService.save(maintainanceTypesDTO);
			maintainanceTypesDTO.setId(id.getDtoEntity().getId());

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), maintainanceTypesDTO);

			maintainaceTypeService.save(maintainanceTypesDTO);
			MaintenanceTypeDTO updatedmaintainanceTypesDTO = maintainaceTypeService.findById(id.getDtoEntity().getId());

			Assert.assertNotNull(" asset Category is null.", updatedmaintainanceTypesDTO);
			Assert.assertTrue(" asset Category not updated.", isFieldUpdated(updatedmaintainanceTypesDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			maintainaceTypeService.delete(id.getDtoEntity().getId());
		}
	}

	private boolean isFieldUpdated(MaintenanceTypeDTO maintainanceTypesDTO, String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return maintainanceTypesDTO.getName().equals(value);

		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			return maintainanceTypesDTO.getDescription().equals(value);

		} else if (name.equals(FieldName.COLOR.name())) {
			return maintainanceTypesDTO.getColor().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value, MaintenanceTypeDTO maintainanceTypesDTO) {

		if (name.equals(FieldName.NAME.name())) {
			maintainanceTypesDTO.setName((String) value);

		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			maintainanceTypesDTO.setDescription((String) value);

		} else if (name.equals(FieldName.COLOR.name())) {
			maintainanceTypesDTO.setColor((String) value);
		}
	}
}
