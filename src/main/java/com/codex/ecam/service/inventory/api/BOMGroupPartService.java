package com.codex.ecam.service.inventory.api;

import java.util.List;

import com.codex.ecam.dto.inventory.bom.BOMGroupPartDTO;

public interface BOMGroupPartService {

	List<BOMGroupPartDTO> findByBomGroupId(Integer bomId) throws Exception;

}
