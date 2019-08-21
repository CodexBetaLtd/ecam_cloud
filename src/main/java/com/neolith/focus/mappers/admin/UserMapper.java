package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.admin.UserCertificationDTO;
import com.neolith.focus.dto.admin.UserDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.model.admin.UserCertification;

public class UserMapper extends GenericMapper<User, UserDTO> {

	private static UserMapper instance = null;

	private UserMapper(){
	}

	public static UserMapper getInstance() {
		if (instance == null) {
			instance = new UserMapper();
		}
		return instance;
	}

	@Override
	public UserDTO domainToDto(User domain) throws Exception {
		UserDTO dto = new UserDTO();
		domainToDto(domain,dto);
		return dto;
	}

	public UserDTO domainToViewDto(User domain) throws Exception {
		UserDTO dto = new UserDTO();
		setBasicUserData(domain, dto);
		return dto;
	}

	private void domainToDto(User domain, UserDTO dto) throws Exception {

		dto.setCurrencyId(domain.getCurrencyId());
		dto.setImagePath(domain.getImagePath());
		dto.setHourlyRate(domain.getHourlyRate());
		dto.setIsActive(domain.getActive());

        dto.setEmailNotification(domain.getEmailNotification());
        dto.setEmailSystemError(domain.getEmailSystemError());
		dto.setWelcomeEmailSent(domain.getWelcomeEmailSent());
		dto.setInternalMailAllMsg(domain.getInternalMailAllMsg());
		dto.setSendMailOnExpire(domain.getSendMailOnExpire());
		dto.setUserSiteDTOList(UserSiteMapper.getInstance().domainToDTOList(domain.getUserSites()));
		dto.setUserCredentialDTO(UserCredentialMapper.getInstance().domainToDto(domain.getUserCredential())); 

		if ((domain.getUserJobTitel() != null) && (domain.getUserJobTitel().getId() != null)) {
			dto.setJobTitle(domain.getUserJobTitel().getId());
			dto.setJobTitleName(domain.getUserJobTitel().getJobTitle());
		}

		if ((domain.getUserLevel() != null) && (domain.getUserLevel().getId() != null)) {
			dto.setUserLevel(domain.getUserLevel());
		}

		if (domain.getUserSkillLevel() != null) {
			dto.setSkillLevel(domain.getUserSkillLevel().getId());
			dto.setSkillLevelName(domain.getUserSkillLevel().getSkill());
		}
		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
            dto.setBusinessName(domain.getBusiness().getName());
        }
		setBasicUserData(domain, dto);
		setUserCertification(domain, dto);

		setCommanDTOFields(dto, domain);
	}

	private void setBasicUserData(User domain, UserDTO dto) {
		dto.setId(domain.getId());
		dto.setFullName(domain.getFullName());
		dto.setAddress(domain.getAddress());
		dto.setEmailAddress(domain.getEmailAddress());
		dto.setPersonalCode(domain.getPersonalCode());
		dto.setNotes(domain.getNotes());
		dto.setTelephone1(domain.getTelephone1());
		dto.setTelephone2(domain.getTelephone2());
		dto.setUserTitle(domain.getUserTitle());
	}

	private void setUserCertification(User domain, UserDTO dto) throws Exception {
		for (UserCertification userCertification : domain.getUserCertifications()) {
			UserCertificationDTO userCertificationDTO = UserCertificationMapper.getInstance().domainToDto(userCertification);
			dto.getUseCertificationDTOs().add(userCertificationDTO);
		}
	}

	@Override
	public void dtoToDomain(UserDTO dto,User domain) throws Exception {

		domain.setId(dto.getId());
		domain.setVersion(dto.getVersion());
		domain.setFullName(dto.getFullName());
		domain.setAddress(dto.getAddress());
		domain.setEmailAddress(dto.getEmailAddress());
		domain.setPersonalCode(dto.getPersonalCode());
		domain.setNotes(dto.getNotes());
		domain.setTelephone1(dto.getTelephone1());
		domain.setTelephone2(dto.getTelephone2());
		domain.setUserTitle(dto.getUserTitle());

		domain.setCurrencyId(dto.getCurrencyId());
		domain.setHourlyRate(dto.getHourlyRate());
		domain.setActive(dto.getIsActive());
		domain.setUserLevel(dto.getUserLevel());

        domain.setEmailNotification(dto.getEmailNotification());
        domain.setEmailSystemError(dto.getEmailSystemError());
		domain.setWelcomeEmailSent(dto.getWelcomeEmailSent());
		domain.setInternalMailAllMsg(dto.getInternalMailAllMsg());
		domain.setSendMailOnExpire(dto.getSendMailOnExpire());
		domain.setSendMailOnExpire(dto.getSendMailOnExpire());

		setCommanDomainFields(dto, domain);
	}

	public void dtoToBasicDomain(UserDTO dto, User domain) throws Exception {

		domain.setId(dto.getId());
		domain.setVersion(dto.getVersion());
		domain.setFullName(dto.getFullName());
		domain.setAddress(dto.getAddress());
		domain.setEmailAddress(dto.getEmailAddress());
		domain.setTelephone1(dto.getTelephone1());
		domain.setTelephone2(dto.getTelephone2());
		domain.setUserTitle(dto.getUserTitle());
		domain.setImagePath(dto.getImagePath());
	}

	@Override
	public UserDTO domainToDtoForDataTable(User domain) throws Exception {
		UserDTO dto = new UserDTO();
		dto.setId(domain.getId());
		dto.setFullName(domain.getFullName());
		dto.setEmailAddress(domain.getEmailAddress());
		dto.setPersonalCode(domain.getPersonalCode());
        if (domain.getBusiness() != null) {
            dto.setBusinessId(domain.getBusiness().getId());
            dto.setBusinessName(domain.getBusiness().getName());
        }
        return dto;
	}


}
