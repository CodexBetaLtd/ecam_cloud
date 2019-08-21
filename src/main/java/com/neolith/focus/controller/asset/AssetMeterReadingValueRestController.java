package com.neolith.focus.controller.asset;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neolith.focus.dto.asset.AssetMeterReadingValueDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.asset.api.AssetMeterReadingValueService;

@RestController
@RequestMapping(AssetMeterReadingValueRestController.REQUEST_MAPPING_URL)
public class AssetMeterReadingValueRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/assetmeterreadingvalue";

	@Autowired
	private AssetMeterReadingValueService assetMeterReadingValueService;

	@RequestMapping(value = "/historybymeterreading/{meterReadingId}", method = RequestMethod.GET)
	public DataTablesOutput<AssetMeterReadingValueDTO> assetMtrReadingValuesByMtrReadingId(@Valid FocusDataTablesInput input, @PathVariable("meterReadingId") Integer meterReadingId) {
		try {
			return assetMeterReadingValueService.findMeterReadingHistory(input, meterReadingId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getMeterReadingValue", method = RequestMethod.GET)
	public AssetMeterReadingValueDTO getAssetMeterReadingValueById(Integer id){
		try {
			return assetMeterReadingValueService.findMeterReadingValueById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
