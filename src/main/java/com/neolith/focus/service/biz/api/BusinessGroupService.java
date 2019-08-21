package com.neolith.focus.service.biz.api;

import com.neolith.focus.dto.biz.business.BusinessGroupDTO;

import java.util.List;

public interface BusinessGroupService {

	List<BusinessGroupDTO> findAllByLevel();

}
