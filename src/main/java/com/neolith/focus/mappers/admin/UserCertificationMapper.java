package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.admin.UserCertificationDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.admin.UserCertification;
import com.neolith.focus.util.DateUtil;

public class UserCertificationMapper extends GenericMapper<UserCertification, UserCertificationDTO> {


	private static UserCertificationMapper instance = null;

	private UserCertificationMapper() {
	}

	public static UserCertificationMapper getInstance() {
		if (instance == null) {
			instance = new UserCertificationMapper();
		}
		return instance;
	}

	@Override
	public UserCertificationDTO domainToDto(UserCertification domain) throws Exception {
		UserCertificationDTO dto = new UserCertificationDTO();
		dto.setId(domain.getId());
		dto.setCertificationTypeId(domain.getCertification().getId());
		dto.setCertificationTypeName(domain.getCertification().getCertificationType());
		dto.setDescription(domain.getDescription());
		dto.setUserCertificationName(domain.getName());
		dto.setUserCertificationLevel(domain.getUserCertificateLevel());
		dto.setUserCertificationLevelName(domain.getUserCertificateLevel().getName());
		dto.setUserId(domain.getUser().getId());
		dto.setValidFromDate(domain.getValidFromDate());
		dto.setValidToDate(domain.getValidToDate());

		setCommanDTOFields(dto, domain);

		return dto;
	}


	@Override
	public void dtoToDomain(UserCertificationDTO dto, UserCertification domain) throws Exception {
		domain.setId(dto.getId());
		domain.setDescription(dto.getDescription());
		domain.setName(dto.getUserCertificationName());
		domain.setUserCertificateLevel(dto.getUserCertificationLevel());
		domain.setValidFromDate(DateUtil.getDateObj(dto.getValidFromDate())  );
		domain.setValidToDate(DateUtil.getDateObj(dto.getValidToDate()));

		setCommanDomainFields(dto, domain);
	}

	@Override
	public UserCertificationDTO domainToDtoForDataTable(UserCertification domain) throws Exception {
		UserCertificationDTO dto = new UserCertificationDTO();
		dto.setCertificationTypeName(domain.getCertification().getCertificationType());
		dto.setDescription(domain.getDescription());
		dto.setUserCertificationName(domain.getName());
		dto.setUserCertificationLevelName(domain.getUserCertificateLevel().getName());
		dto.setValidFromDate(domain.getValidFromDate());
		dto.setValidToDate(domain.getValidToDate());

		return dto;
	}


}
