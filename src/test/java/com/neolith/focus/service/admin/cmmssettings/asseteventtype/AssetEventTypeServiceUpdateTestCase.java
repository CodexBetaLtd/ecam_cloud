package com.neolith.focus.service.admin.cmmssettings.asseteventtype;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dto.admin.AssetEventTypeDTO;
import com.neolith.focus.result.admin.AssetEventTypeResult;
import com.neolith.focus.service.admin.api.AssetEventTypeService;
@Component
public class AssetEventTypeServiceUpdateTestCase extends TestCase{

	public static enum FieldName {NAME,DESCRIPTION,CODE};

	@Autowired
	private AssetEventTypeService assetEventTypeService;

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
		AssetEventTypeResult id = null;
		try {

			AssetEventTypeDTO assetEventTypeDTO = AssetEventTypeDummyData.getInstance().getDummyDTOAssetEventTypeOne();
			id = assetEventTypeService.save(assetEventTypeDTO);
			assetEventTypeDTO.setId(id.getDtoEntity().getId());

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), assetEventTypeDTO);

			assetEventTypeService.save(assetEventTypeDTO);
			AssetEventTypeDTO updatedassetEventTypeDTO = assetEventTypeService.findById(id.getDtoEntity().getId());

			Assert.assertNotNull(" asset Category is null.", updatedassetEventTypeDTO);
			Assert.assertTrue(" asset Category not updated.", isFieldUpdated(updatedassetEventTypeDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			assetEventTypeService.delete(id.getDtoEntity().getId());
		}
	}

	private boolean isFieldUpdated(AssetEventTypeDTO assetEventTypeDTO, String name, Object value) {

		if (name.equals(FieldName.NAME.name())) {
			return assetEventTypeDTO.getName().equals(value);

		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			return assetEventTypeDTO.getDescription().equals(value);

		} else if (name.equals(FieldName.CODE.name())) {
			return assetEventTypeDTO.getCode().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value, AssetEventTypeDTO assetEventTypeDTO) {

		if (name.equals(FieldName.NAME.name())) {
			assetEventTypeDTO.setName((String) value);

		} else if (name.equals(FieldName.DESCRIPTION.name())) {
			assetEventTypeDTO.setDescription((String) value);

		} else if (name.equals(FieldName.CODE.name())) {
			assetEventTypeDTO.setCode((String) value);
		}
	}
}
