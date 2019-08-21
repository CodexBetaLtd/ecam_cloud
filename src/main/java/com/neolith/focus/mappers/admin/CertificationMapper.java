package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.admin.CertificationDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.admin.Certification;

public class CertificationMapper extends GenericMapper<Certification, CertificationDTO> {

	private static CertificationMapper instance = null;

	private CertificationMapper() {
	}

	public static CertificationMapper getInstance() {
		if (instance == null) {
			instance = new CertificationMapper();
		}
		return instance;
	}

	@Override
	public CertificationDTO domainToDto(Certification domain) throws Exception {
		CertificationDTO dto = new CertificationDTO();
		dto.setId(domain.getId());
		dto.setCertificationType(domain.getCertificationType());

		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(CertificationDTO dto, Certification domain) throws Exception {
		domain.setId(dto.getId());
		domain.setCertificationType(dto.getCertificationType());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public CertificationDTO domainToDtoForDataTable(Certification domain) throws Exception {
		CertificationDTO dto = new CertificationDTO();
		dto.setId(domain.getId());
		dto.setCertificationType(domain.getCertificationType());

		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;

	}

}
