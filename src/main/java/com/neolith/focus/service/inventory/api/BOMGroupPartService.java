package com.neolith.focus.service.inventory.api;

import com.neolith.focus.dto.inventory.bom.BOMGroupPartDTO;

import java.util.List;

public interface BOMGroupPartService {

	List<BOMGroupPartDTO> findByBomGroupId(Integer bomId) throws Exception;

}
