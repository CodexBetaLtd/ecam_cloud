package com.codex.ecam.service.asset.api;

import java.util.List;

import com.codex.ecam.dto.asset.WarrantyDTO;
import com.codex.ecam.model.asset.Asset;

public interface WarrantyService {

	void setWarranties(List<WarrantyDTO> warrantyDTOs, Asset asset) throws Exception; 

}
