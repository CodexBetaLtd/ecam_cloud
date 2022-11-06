package com.codex.ecam.controller.asset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.*;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.asset.AssetCustomerDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.asset.api.AssetTreeService;
import com.codex.ecam.service.asset.api.CustomerService;
import com.codex.ecam.util.FileDownloadUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	@RequestMapping(value = "/getMeterReadingList", method = RequestMethod.GET)
	public List<AssetMeterReadingDTO> getMeterReadingListByAssetId(Integer assetId) {
		try {
			return assetService.findAssetMeterReadingByAssetId(assetId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getMeterReadingById", method = RequestMethod.GET)
	public AssetMeterReadingDTO getMeterReadingById(Integer id) {
		try {
			return assetService.findAssetMeterReadingById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@RequestMapping(value = "/getSitesForBusiness", method = RequestMethod.GET)
	public List<AssetDTO> getSiteListForBusiness(Integer businessId) {
		try {
			return assetService.findAllSiteByBusiness(businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getParentAssetsByBusiness", method = RequestMethod.GET)
	public List<AssetDTO> getParentAssetByBusiness(Integer businessId, AssetCategoryType type) {
		try {
			return assetService.findByAssetCategoryType(businessId, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/assets-by-category-n-business", method = RequestMethod.GET)
	public List<AssetDTO> getAssetByBusinessNCategory(@Valid Integer bizId, @Valid Integer categoryId) {
		try {
			return assetService.findAssetsByCategoryBusiness(bizId, categoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/tabledata-parts-by-business", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getPartsByBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findPartsByBusiness(input, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/tabledata-parts-repairable-by-business", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getRepairablePartsByBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findRepairablePartsByBusiness(input, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/assetList", method = RequestMethod.GET)
	public List<AssetDTO> getAllAssets() {
		try {
			return assetService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	
	
	@RequestMapping(value = "/childrens", method = RequestMethod.GET)
	public List<AssetDTO> getAllChildAssets(@Valid Integer id) {
		try {
			return assetTreeService.findAllChildrens(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	@RequestMapping(value = "/machine-childrens", method = RequestMethod.GET)
	public List<AssetDTO> getAllChildMachines(@Valid Integer id) {
		try {
			return assetTreeService.findAllMachineChildrens(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	@RequestMapping(value = "/sub-location", method = RequestMethod.GET)
	public List<AssetDTO> getsubLocationByParentLocation(@Valid Integer parentLocationId) {
		try {
			return 	 assetService.findAllSubLocationByMainLocationtId(parentLocationId);

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	@RequestMapping(value = "/sub-location2", method = RequestMethod.GET)
	public List<AssetDTO> getsubLocation2BysubLocation(@Valid Integer subLocationId) {
		try {
			return 	 assetService.findAllSubLocation2BySublocationLocationtId(subLocationId);

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	@RequestMapping(value = "/parent-facilities", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getFacilityParents(@Valid FocusDataTablesInput input) {
		try {
			return assetTreeService.findAllParentFacilities(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/parent-machines", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getParentMachines(@Valid FocusDataTablesInput input) {
		try {
			return assetTreeService.findAllParentMachinesAndTools(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/parent-machines-by-location", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getParentMachineLocation(@Valid FocusDataTablesInput input,Integer id,Integer assetId) {
		try {
			return assetTreeService.findMachineParentLocation(input,id,assetId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getAssetList(@Valid FocusDataTablesInput input) {
		try {
			return assetService.findAll(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/machine-tabledata", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getMachine(@Valid FocusDataTablesInput input) {
		try {
			return assetService.findAllMachineAndToolsByLevel(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/facility-tabledata", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getFacility(@Valid FocusDataTablesInput input) {
		try {
			return assetService.findAllFacilitiesByLevel(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/assetsbycategorytype/{type}", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> assetsByCategorytype(@Valid FocusDataTablesInput input, @PathVariable("type") AssetCategoryType type) {
		try {
			return assetService.findByCategoryType(input, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/customerhistory/{assetId}", method = RequestMethod.GET)
	public DataTablesOutput<AssetCustomerDTO> getCustomers(@Valid FocusDataTablesInput input, @PathVariable("assetId") Integer assetId) {
		try {
			return customerService.findByAsset(input, assetId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/parts", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getParts(@Valid FocusDataTablesInput input) {
		try {
			return assetService.findParts(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getSiteByBusiness", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getSiteByBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findSiteByBusiness(input, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getAnyAssetTypeByBusiness", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getAnyAssetTypeByBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findAnyAssetTypeByBusiness(input, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getAssetByBusiness", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> findAssetByAssetTypeAndBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findAssetByAssetTypeAndBusiness(input, AssetCategoryType.PARTS_AND_SUPPLIES, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/findNotPartByBusiness", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> findAssetNotByAssetTypeAndBusiness(@Valid FocusDataTablesInput input, @Valid Integer id) {
		try {
			return assetService.findAssetNotByAssetTypeAndBusiness(input, AssetCategoryType.PARTS_AND_SUPPLIES, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getAssetByLogUserBusiness", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getAssetByLogUserBusiness(@Valid FocusDataTablesInput input) {
		try {
			return assetService.getAssetByLogUserBusiness(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/machines-tools-by-business-tabledata", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getMachineToolsByBusiness(@Valid FocusDataTablesInput input, @Valid Integer bizId) {
		try {
			return assetService.getMachineToolsByBusiness(input, bizId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/parent-assets-by-business", method = RequestMethod.GET)
	public DataTablesOutput<AssetDTO> getAssetByBusiness(@Valid FocusDataTablesInput input, Integer businessId, AssetCategoryType type) {
		try {
			return assetService.findAssetByCategoryTypeBusiness(input, businessId, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/asset-image" , method = RequestMethod.GET)
	public @ResponseBody byte[] getAssetImage( Integer id, HttpServletRequest request) throws IOException {
		try {
			return assetService.getAssetImageStream(id, request);
		} catch (Exception e) {
			return FileDownloadUtil.getByteInputStream( request.getServletContext().getRealPath("").concat(ASSET_DEFAULT_IMAGE));
		} 
	} 
	
	@RequestMapping(value = "/asset-qr" , method = RequestMethod.GET)
	public @ResponseBody byte[] getAssetQR( Integer id, HttpServletRequest request) throws IOException {
		try {
			return assetService.getAssetQRStream(id, request);
		} catch (Exception e) {
			return FileDownloadUtil.getByteInputStream( request.getServletContext().getRealPath("").concat(ASSET_NO_QR_IMAGE));
		} 
	} 

 

}
