package com.neolith.focus.service.biz.impl;

import com.neolith.focus.dao.biz.BusinessGroupDao;
import com.neolith.focus.dto.biz.business.BusinessGroupDTO;
import com.neolith.focus.mappers.admin.BusinessGroupMapper;
import com.neolith.focus.model.biz.business.BusinessGroup;
import com.neolith.focus.service.biz.api.BusinessGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessGroupServiceImpl implements BusinessGroupService {
	
	@Autowired
	private BusinessGroupDao businessGroupDao;

	@Override
	public List<BusinessGroupDTO> findAllByLevel() {
		List<BusinessGroup> businessGroupList = businessGroupDao.findAll();
		try {
			return BusinessGroupMapper.getInstance().domainToDTOList(businessGroupList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
