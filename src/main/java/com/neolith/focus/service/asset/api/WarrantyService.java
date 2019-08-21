package com.neolith.focus.service.asset.api;

import com.neolith.focus.dto.asset.WarrantyDTO;
import com.neolith.focus.model.asset.Asset;

import java.util.List;

public interface WarrantyService {

	void setWarranties(List<WarrantyDTO> warrantyDTOs, Asset asset) throws Exception; 

}
