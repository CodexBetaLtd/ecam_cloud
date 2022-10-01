package com.codex.ecam.service.inventory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.inventory.BOMGroupPartDao;
import com.codex.ecam.dto.inventory.bom.BOMGroupPartDTO;
import com.codex.ecam.mappers.inventory.BOMGroupPartMapper;
import com.codex.ecam.model.inventory.bom.BOMGroupPart;
import com.codex.ecam.service.inventory.api.BOMGroupPartService;

import java.util.List;

@Service
public class BOMGroupPartServiceImpl implements BOMGroupPartService {
	
	@Autowired
	private BOMGroupPartDao bomGroupPartDao;

	@Override
	public List<BOMGroupPartDTO> findByBomGroupId(Integer bomId) throws Exception {
		List<BOMGroupPart> groupParts = bomGroupPartDao.findByBomGroupId(bomId);
		return BOMGroupPartMapper.getInstance().domainToDTOList(groupParts);
	}

}
