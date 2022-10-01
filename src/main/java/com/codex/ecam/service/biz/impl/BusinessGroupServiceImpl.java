package com.codex.ecam.service.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.biz.BusinessGroupDao;
import com.codex.ecam.dto.biz.business.BusinessGroupDTO;
import com.codex.ecam.mappers.admin.BusinessGroupMapper;
import com.codex.ecam.model.biz.business.BusinessGroup;
import com.codex.ecam.service.biz.api.BusinessGroupService;

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
