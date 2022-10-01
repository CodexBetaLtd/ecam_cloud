package com.codex.ecam.service.admin.cmmssettings.certification;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.result.admin.CertificationResult;
import com.codex.ecam.service.admin.api.CertificationService;
@Component
public class CertificationServiceUpdateTestCase extends TestCase{

	public static enum FieldName {CERTIFICATION_TYPE};

	@Autowired
	private CertificationService certificationService;

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
		CertificationResult id = null;
		try {

			CertificationDTO certificationDTO = CertificationDummyData.getInstance().getDummyDTOCertificationOne();
			id = certificationService.save(certificationDTO);
			certificationDTO.setId(id.getDtoEntity().getId());

			setGivenValue((String) getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), certificationDTO);

			certificationService.save(certificationDTO);
			CertificationDTO updatedcertificationDTO = certificationService.findById(id.getDtoEntity().getId());

			Assert.assertNotNull(" asset Category is null.", updatedcertificationDTO);
			Assert.assertTrue(" asset Category not updated.", isFieldUpdated(updatedcertificationDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));

		} catch (Exception e) {
			isError = true;
		} finally {
			certificationService.delete(id.getDtoEntity().getId());
		}
	}

	private boolean isFieldUpdated(CertificationDTO certificationDTO , String name, Object value) {

		if (name.equals(FieldName.CERTIFICATION_TYPE.name())) {
			return certificationDTO.getCertificationType().equals(value);

		}
		return false;
	}

	private void setGivenValue(String name, Object value, CertificationDTO certificationDTO ) {

		if (name.equals(FieldName.CERTIFICATION_TYPE.name())) {
			certificationDTO.setCertificationType((String) value);

		}
	}
}
