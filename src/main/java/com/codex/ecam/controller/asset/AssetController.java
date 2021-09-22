package com.codex.ecam.controller.asset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.WarrantyType;
import com.codex.ecam.constants.WarrantyUsageTermType;
import com.codex.ecam.dto.admin.AssetBrandDTO;
import com.codex.ecam.dto.admin.AssetModelDTO;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.result.asset.AssetResult;
import com.codex.ecam.service.admin.api.AssetBrandService;
import com.codex.ecam.service.admin.api.AssetEventTypeService;
import com.codex.ecam.service.admin.api.AssetModelService;
import com.codex.ecam.service.admin.api.CountryService;
import com.codex.ecam.service.admin.api.CurrencyService;
import com.codex.ecam.service.admin.api.MeterReadingUnitService;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.biz.api.BusinessTypeService;
import com.codex.ecam.service.biz.api.SupplierService;
import com.codex.ecam.util.AzureEventHubUtil;
import com.codex.ecam.util.SendGridUtil;

@Controller
@RequestMapping(AssetController.REQUEST_MAPPING_URL)
public class AssetController {

	public static final String REQUEST_MAPPING_URL = "/asset";

	@Autowired
	private AssetService assetService;

	@Autowired
	private AssetCategoryService assetCategoryService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private BusinessTypeService businessTypeService;

	@Autowired
	private MeterReadingUnitService meterReadingUnitsService;

	@Autowired
	private AssetEventTypeService assetEventTypeService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private AssetBrandService assetBrandService;

	@Autowired
	private AssetModelService assetModelService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {

	return "asset/home-view";
	}

	@RequestMapping(value = "/machine", method = RequestMethod.GET)
	public String indexMachine(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);


		return "asset/machine/home-view";
	}
	
	@RequestMapping(value = "/facility", method = RequestMethod.GET)
	public String indexFacility(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "asset/facility/home-view";
	} 
	
	@RequestMapping(value = "/facility-table", method = RequestMethod.GET)
	public String facilityTableView(final Model model, @ModelAttribute("success") final ArrayList<String> success,
			@ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "asset/facility/home-view :: tableDiv";
	}

	@RequestMapping(value = "/machine-table", method = RequestMethod.GET)
	public String machineTableView(final Model model, @ModelAttribute("success") final ArrayList<String> success,
			@ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);

		return "asset/machine/home-view :: tableDiv";
	}
	
	@RequestMapping(value = "/facility/edit", method = RequestMethod.GET)
	public String editFacilityForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			AssetDTO asset = assetService.findById(id);
			setCommonData(model, asset.getAssetCategoryType(), asset);
			return "asset/facility/add-view";
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/asset/facility";
		}
	}  
	
	@RequestMapping(value = "/machine/edit", method = RequestMethod.GET)
	public String editMachineForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			AssetDTO asset = assetService.findById(id);
			setCommonData(model, asset.getAssetCategoryType(), asset);
			return "asset/machine/add-view";
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/asset/machine";
		}
	}  
	
	@RequestMapping(value = "/machine/delete", method = RequestMethod.GET)
	public String deleteMachine(Integer id, Model model, RedirectAttributes ra) { 
		delete(id, ra);
		return "redirect:/asset/machine/";
	}
	
	@RequestMapping(value = "/facility/delete", method = RequestMethod.GET)
	public String deleteFacility(Integer id, Model model, RedirectAttributes ra) { 
		delete(id, ra);
		return "redirect:/asset/facility/";
	}

	private void delete(Integer id, RedirectAttributes ra) {
		AssetResult result = assetService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "asset/home-view";
	}

	@RequestMapping(value = "/assetcategoryview", method = RequestMethod.GET)
	public String categoryView(Model model, AssetCategoryType type) {
		model.addAttribute("type", type);
		return "asset/modals/category/asset-category-select-modal";
	}

	@RequestMapping(value = "/assetcategoryadd", method = RequestMethod.GET)
	public String categoryAdd(Model model, AssetCategoryType type) {
		model.addAttribute("type", type);
		model.addAttribute("sysCode", type.getId());
		model.addAttribute("assetCategory", new AssetCategoryDTO());
		model.addAttribute("assetCategoryParents", assetCategoryService.findByAssetCategoyType(type));
		return "asset/modals/category/asset-category-add-modal";
	}

	@RequestMapping(value = "/parentassetselectview", method = RequestMethod.GET)
	public  String parentAssetSelectView(Model model){
		return "asset/modals/parent-asset-select-modal";
	}

	@RequestMapping(value = "/customerselectview", method = RequestMethod.GET)
	public  String customerSelectView(Model model){
		return "asset/modals/customer-select-modal";
	}

	@RequestMapping(value = "/assetbrandview", method = RequestMethod.GET)
	public String brandView(Model model) {
		return "asset/modals/brand/asset-brand-select-modal";
	}

	@RequestMapping(value = "/assetbrandaddview", method = RequestMethod.GET)
	public String brandAddView(Model model) {
		model.addAttribute("assetBrand", new AssetBrandDTO());
		return "asset/modals/brand/asset-brand-add-modal";
	}

	@RequestMapping(value = "/assetmodelview", method = RequestMethod.GET)
	public String modelView(Model model) {
		return "asset/modals/model/asset-model-select-modal";
	}
	
	@RequestMapping(value = "/sparepartaddview", method = RequestMethod.GET)
	public String modelSparePartAddView(Model model) {
		return "asset/modals/spare-part-add-modal";
	}
	
	@RequestMapping(value = "/sparepartselectmodalview", method = RequestMethod.GET)
	public String modelSparePartSelectView(Model model) {
		return "asset/modals/part-select-modal";
	}

	@RequestMapping(value = "/assetmodeladdview", method = RequestMethod.GET)
	public String modelAddView(Model model) {
		model.addAttribute("assetModel", new AssetModelDTO());
		return "asset/modals/model/asset-model-add-modal";
	}

	@RequestMapping(value = "/warrantymodelview", method = RequestMethod.GET)
	public String getWarrantyAddView(Model model) {
		model.addAttribute("warrantyTypes", WarrantyType.getWarrantyTypes());
		model.addAttribute("warrantyUsageTermTypes", WarrantyUsageTermType.getWarrantyUsageTermTypes());
		model.addAttribute("providers", businessService.findAllActualBusinessByLevel());
		model.addAttribute("meterReadingUnits", meterReadingUnitsService.findAllMeterReadings());
		return "asset/modals/warranty-add-modal";
	}

	@RequestMapping(value = "/asseteventmodelview", method = RequestMethod.GET)
	public String getAssetEventAddView(Model model) {
		model.addAttribute("assetEventTypes", assetEventTypeService.findAll());
		return "asset/modals/asset-event-add-modal";
	}

	@RequestMapping(value = "/asseteventtypeview", method = RequestMethod.GET)
	public String getAssetEventTypeView(Model model) {
		return "asset/modals/event/asset-event-select-modal";
	}

	@RequestMapping(value = "/assetmeterreadingmodelview", method = RequestMethod.GET)
	public String getAssetMeterReadingAddView(Model model) {
		model.addAttribute("meterReadingUnits", meterReadingUnitsService.findAllMeterReadings());
		return "asset/modals/meter-reading-add-modal";
	}

	@RequestMapping(value = "/assetmeterreadingvaluemodelview", method = RequestMethod.GET)
	public String getAssetMeterReadingValueAddView(Model model) {
		return "asset/modals/meter-reading-value-add-modal";
	}
	@RequestMapping(value = "/assetmeterreadingconsumptionvariablemodelview", method = RequestMethod.GET)
	public String getAssetMeterReadingConsumtionAddView(Model model) {
		model.addAttribute("meterReadingUnits", meterReadingUnitsService.findAllMeterReadings());
		return "asset/modals/meter-reading-consumption-add-modal";
	}

	@RequestMapping(value = "/asseteventhistorymodelview", method = RequestMethod.GET)
	public String getAssetEventHistoryView(Model model) {
		return "asset/modals/asset-event-history-modal";
	}

	@RequestMapping(value = "/assetmeterreadinghistorymodelview", method = RequestMethod.GET)
	public String getAssetMeterReadingHistoryView(Model model) {
		return "asset/modals/meter-reading-history-modal";
	}

	@RequestMapping(value = "/customerhistorymodalview", method = RequestMethod.GET)
	public String getCustomerHistoryView(Model model) {
		return "asset/modals/customer-history-modal";
	}

	@RequestMapping(value = "/customercontactmodalview", method = RequestMethod.GET)
	public String getCustomerContactAddView(Model model) {
		return "asset/modals/customer-contact-add-modal";
	}

	@RequestMapping(value = "/usermodelview", method = RequestMethod.GET)
	public String getUserTableView(Model model) {
		return "asset/modals/user-select-modal";
	}

	@RequestMapping(value = "/partmodelview", method = RequestMethod.GET)
	public String getPartTableView(Model model) {
		return "asset/modals/part-select-modal";
	}

	@RequestMapping(value = "/bommodelview", method = RequestMethod.GET)
	public String getBomTableView(Model model) {
		return "asset/modals/bom-select-modal";

	}
	@RequestMapping(value = "/filemodelview", method = RequestMethod.GET)
	public String getFileTableView(Model model) {
		return "asset/modals/file-add-modal";
	}

	@RequestMapping(value = "/meterreadinghistoryview", method = RequestMethod.GET)
	public String getMeterReadingHistroryTableView(Model model) {
		return "asset/modals/meter-reading-history-modal";

	}

	@RequestMapping(value = "/asseteventhistoryview", method = RequestMethod.GET)
	public String getEventHistoryTableView(Model model) {
		return "asset/modals/asset-event-history-modal";
	}
	@RequestMapping(value = "/assetimportview", method = RequestMethod.GET)
	public String getAssetImportView(Model model) throws Exception {
		model.addAttribute("businesses", businessService.findAll());
		return "asset/modals/asset-import-modal";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, AssetCategoryType type, RedirectAttributes ra) {
		try {
			setCommonData(model, type, new AssetDTO());
			return "asset/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/asset/index";
		}
	}
	
	@RequestMapping(value = "/machine/add", method = RequestMethod.GET)
	public String addMachineForm(Model model, AssetCategoryType type, RedirectAttributes ra) {
		try {
			setCommonData(model, type, new AssetDTO());
			return "asset/machine/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/asset/machine/";
		}
	}

	@RequestMapping(value = "/facility/add", method = RequestMethod.GET)
	public String addFacilityForm(Model model, AssetCategoryType type, RedirectAttributes ra) {
		try {
			setCommonData(model, type, new AssetDTO());
			return "asset/facility/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/asset/facility/";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			AssetDTO asset = assetService.findById(id);
			setCommonData(model, asset.getAssetCategoryType(), asset);
			return "asset/add-view";
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/asset/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) { 
		delete(id, ra);
		return "redirect:/asset/index";
	}
	
	@RequestMapping(value = "/machine/save", method = RequestMethod.POST)
	public String machineSave(@ModelAttribute("asset") @Valid AssetDTO asset, @RequestParam("assetImage") MultipartFile image, @ModelAttribute("type") AssetCategoryType type, Model model) throws Exception {	
		saveOrUpdate(asset, image, type, model);
		return "asset/machine/add-view";
	}

	@RequestMapping(value = "/facility/save", method = RequestMethod.POST)
	public String facilitySave(@ModelAttribute("asset") @Valid AssetDTO asset, @RequestParam("assetImage") MultipartFile image, @ModelAttribute("type") AssetCategoryType type, Model model) throws Exception {
		saveOrUpdate(asset, image, type, model);
		return "asset/facility/add-view";
	}

	private void saveOrUpdate(AssetDTO asset, MultipartFile image, AssetCategoryType type, Model model)
			throws Exception {
		AssetResult result = assetService.save(asset, image);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			asset = assetService.findById(asset.getId());
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, type, asset);
	}

	@RequestMapping(value = "/getsites", method = RequestMethod.GET)
	public @ResponseBody List<AssetDTO> selectParent(Integer id, Model model) throws Exception {
		List<AssetDTO> assetDTOs = assetService.findSiteByBusinessId(id, AssetCategoryType.LOCATIONS_OR_FACILITIES);
		return assetDTOs;
	}

	@RequestMapping(value = "/selectBusiness/{assetId}", method = RequestMethod.GET)
	public @ResponseBody List<AssetDTO> selectParent(@PathVariable("assetId") Integer assetId) throws Exception {
		List<AssetDTO> assetDTOs = assetService.findAllSiteByBusiness(assetId);
		return assetDTOs;
	}

	@RequestMapping(value = "/upload-file", method = RequestMethod.POST)
	public @ResponseBody List<String>  uploadFile(@RequestParam("fileData") MultipartFile fileData, @RequestParam("fileRefId")String refId) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(fileData.getContentType());
		list.add(assetService.assetFileUpload(fileData,refId));
		return list;
	}

	@RequestMapping(value = "/download-file", method = RequestMethod.GET)
	public void  downloadFile(@RequestParam("fileId")Integer id, HttpServletResponse response) throws Exception {
		assetService.assetFileDownload(id,response);
	}
	@RequestMapping(value = "/download-qr", method = RequestMethod.GET)
	public void  downloadQR(@RequestParam("id")Integer id, HttpServletResponse response) throws Exception {
		assetService.assetQRDownload(id,response);
	}
	
	@RequestMapping(value = "/import-assets", method = RequestMethod.POST)
	public void  importAssets(@RequestParam("fileData")MultipartFile file,@RequestParam("bussinessId")Integer bussinessId, HttpServletResponse response) throws Exception {
		assetService.importBulkAssets(file,bussinessId);
	}

	private void setCommonData(Model model, AssetCategoryType type, AssetDTO asset) {
		model.addAttribute("asset", asset);
		model.addAttribute("type", type);
		model.addAttribute("categories", assetCategoryService.findByAssetCategoyType(type));
		model.addAttribute("meterReadingUnits", meterReadingUnitsService.findAllMeterReadings());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("suppliers", supplierService.findAllSupplierByUserLevel());
		model.addAttribute("businessTypes", businessTypeService.findAll());
		model.addAttribute("sites", assetService.findSiteByBusinessId(asset.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
		model.addAttribute("currencies", currencyService.findAll());
		model.addAttribute("countryList", countryService.findAll());
		model.addAttribute("assetBrand", assetBrandService.findAll());
		model.addAttribute("assetModel", assetModelService.findByBrandId(asset.getBrand()));

	}

}
