package com.neolith.focus.service.inventory.impl;

import com.neolith.focus.dao.inventory.BOMGroupPartDao;
import com.neolith.focus.dto.inventory.bom.BOMGroupPartDTO;
import com.neolith.focus.mappers.inventory.BOMGroupPartMapper;
import com.neolith.focus.model.inventory.bom.BOMGroupPart;
import com.neolith.focus.service.inventory.api.BOMGroupPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
