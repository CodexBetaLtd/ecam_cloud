package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.model.admin.Certification;
import com.codex.ecam.result.BaseResult;

public class CertificationResult extends BaseResult<Certification, CertificationDTO>{

	public CertificationResult(Certification domain, CertificationDTO dto) {
		super(domain, dto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());		
	}
}