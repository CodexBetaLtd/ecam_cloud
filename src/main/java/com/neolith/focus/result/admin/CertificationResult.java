package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.CertificationDTO;
import com.neolith.focus.model.admin.Certification;
import com.neolith.focus.result.BaseResult;

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