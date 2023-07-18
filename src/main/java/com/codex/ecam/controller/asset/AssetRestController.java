package com.codex.ecam.controller.asset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.asset.AssetCustomerDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.asset.api.AssetTreeService;
import com.codex.ecam.service.asset.api.CustomerService;
import com.codex.ecam.util.FileDownloadUtil;

@RestController
@RequestMapping(AssetRestController.REQUEST_MAPPING_URL)
public class AssetRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/asset";

	private final String ASSET_DEFAULT_IMAGE = "/resources/images/no_image.png";
	private final String ASSET_NO_QR_IMAGE = "/resources/images/no_qr.png";

	@Autowired
	private AssetService assetService;

	@Autowired
	private AssetTreeService assetTreeService;

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/getMeterReadingList")
	public List<AssetMeterReadingDTO> getMeterReadingListByAssetId(Integer assetId) {
		try {
			return assetService.findAssetMeterReadingByAssetId(assetId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@GetMapping(value = "/getMeterReadingById")
	public AssetMeterReadingDTO getMeterReadingById(Integer id) {
		try {
			return assetService.findAssetMeterReadingById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@GetMapping(value = "/getSitesForBusiness")
	public List<AssetDTO> getSiteListForBusiness(Integer businessId) {
		try {
			return assetService.findAllSiteByBusiness(businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@GetMapping(value = "/getParentAssetsByBusiness")
	public List<AssetDTO> getParentAssetByBusiness(Integer businessId, AssetCategoryType type) {
		try {
			return assetService.findByAssetCategoryType(businessId, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@GetMapping(value = "/assets-by-category-n-business")
	public List<AssetDTO> getAssetByBusinessNCategory(@Valid Integer bizId, @Valid Integer categoryId) {
		try {
			return assetService.findAssetsByCategoryBusiness(bizId, categoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@GetMapping(value = "/tabledata-parts-by-business")
	public DataTablesOutput<AssetDTO> getPartsByBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findPartsByBusiness(input, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@GetMapping(value = "/tabledata-parts-repairable-by-business")
	public DataTablesOutput<AssetDTO> getRepairablePartsByBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findRepairablePartsByBusiness(input, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/sub-location/tabledata")
	public DataTablesOutput<AssetDTO> getSubLocationsByParentLocation(@Valid FocusDataTablesInput input, @Valid Integer parentLocationId) {
		try {
			return assetService.findAllSubLocationByMainLocationtId(input, parentLocationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<> ();
	}

	@GetMapping(value = "/assetList")
	public List<AssetDTO> getAllAssets() {
		try {
			return assetService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@GetMapping(value = "/childrens")
	public List<AssetDTO> getAllChildAssets(@Valid Integer id) {
		try {
			return assetTreeService.findAllChildrens(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@GetMapping(value = "/machine-childrens", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AssetDTO>  getAllChildMachines(@Valid Integer id) {
		try {
			List<AssetDTO> dtos = assetTreeService.findAllMachineChildrens(id);
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@GetMapping(value = "/sub-location")
	public List<AssetDTO> getsubLocationByParentLocation(@Valid Integer parentLocationId) {
		try {
			return 	 assetService.findAllSubLocationByMainLocationtId(parentLocationId);

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@GetMapping(value = "/sub-location2")
	public List<AssetDTO> getsubLocation2BysubLocation(@Valid Integer subLocationId) {
		try {
			return 	 assetService.findAllSubLocation2BySublocationLocationtId(subLocationId);

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@GetMapping(value = "/parent-facilities")
	public DataTablesOutput<AssetDTO> getFacilityParents(@Valid FocusDataTablesInput input) {
		try {
			return assetTreeService.findAllParentFacilities(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/parent-machines")
	public DataTablesOutput<AssetDTO> getParentMachines(@Valid FocusDataTablesInput input) {
		try {
			return assetTreeService.findAllParentMachinesAndTools(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@GetMapping(value = "/parent-machines-by-location")
	public DataTablesOutput<AssetDTO> getParentMachineLocation(@Valid FocusDataTablesInput input,Integer id,Integer assetId) {
		try {
			return assetTreeService.findMachineParentLocation(input,id,assetId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/tabledata")
	public DataTablesOutput<AssetDTO> getAssetList(@Valid FocusDataTablesInput input) {
		try {
			return assetService.findAll(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/machine-tabledata")
	public DataTablesOutput<AssetDTO> getMachine(@Valid FocusDataTablesInput input) {
		try {
			return assetService.findAllMachineAndToolsByLevel(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/facility-tabledata")
	public DataTablesOutput<AssetDTO> getFacility(@Valid FocusDataTablesInput input, @RequestParam(name = "bizId", required = false) Integer bizId) {
		try {
			return assetService.findAssetByCategoryTypeBusiness(input, bizId, AssetCategoryType.LOCATIONS_OR_FACILITIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/assetsbycategorytype/{type}")
	public DataTablesOutput<AssetDTO> assetsByCategorytype(@Valid FocusDataTablesInput input, @PathVariable("type") AssetCategoryType type) {
		try {
			return assetService.findByCategoryType(input, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/customerhistory/{assetId}")
	public DataTablesOutput<AssetCustomerDTO> getCustomers(@Valid FocusDataTablesInput input, @PathVariable("assetId") Integer assetId) {
		try {
			return customerService.findByAsset(input, assetId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/parts")
	public DataTablesOutput<AssetDTO> getParts(@Valid FocusDataTablesInput input) {
		try {
			return assetService.findParts(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/getSiteByBusiness")
	public DataTablesOutput<AssetDTO> getSiteByBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findSiteByBusiness(input, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/getAnyAssetTypeByBusiness")
	public DataTablesOutput<AssetDTO> getAnyAssetTypeByBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findAnyAssetTypeByBusiness(input, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/getAssetByBusiness")
	public DataTablesOutput<AssetDTO> findAssetByAssetTypeAndBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findAssetByAssetTypeAndBusiness(input, AssetCategoryType.PARTS_AND_SUPPLIES, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/findNotPartByBusiness")
	public DataTablesOutput<AssetDTO> findAssetNotByAssetTypeAndBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findAssetNotByAssetTypeAndBusiness(input, AssetCategoryType.PARTS_AND_SUPPLIES, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/getAssetByLogUserBusiness")
	public DataTablesOutput<AssetDTO> getAssetByLogUserBusiness(@Valid FocusDataTablesInput input) {
		try {
			return assetService.getAssetByLogUserBusiness(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/machines-tools-by-business-tabledata")
	public DataTablesOutput<AssetDTO> getMachineToolsByBusiness(@Valid FocusDataTablesInput input, @Valid Integer bizId) {
		try {
			return assetService.getMachineToolsByBusiness(input, bizId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/parent-assets-by-business")
	public DataTablesOutput<AssetDTO> getAssetByBusiness(@Valid FocusDataTablesInput input, Integer businessId, AssetCategoryType type) {
		try {
			return assetService.findAssetByCategoryTypeBusiness(input, businessId, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/asset-image" )
	public @ResponseBody byte[] getAssetImage( Integer id, HttpServletRequest request) throws IOException {
		try {
			return assetService.getAssetImageStream(id, request);
		} catch (Exception e) {
			return FileDownloadUtil.getByteInputStream( request.getServletContext().getRealPath("").concat(ASSET_DEFAULT_IMAGE));
		}
	}

	@GetMapping(value = "/asset-qr" )
	public @ResponseBody byte[] getAssetQR( Integer id, HttpServletRequest request) throws IOException {
		try {
			return assetService.getAssetQRStream(id, request);
		} catch (Exception e) {
			return FileDownloadUtil.getByteInputStream( request.getServletContext().getRealPath("").concat(ASSET_NO_QR_IMAGE));
		}
	}



}
