package com.codex.ecam.service.biz.api;

import java.util.List;

import com.codex.ecam.dto.biz.business.BusinessGroupDTO;

public interface BusinessGroupService {

	List<BusinessGroupDTO> findAllByLevel();

}
