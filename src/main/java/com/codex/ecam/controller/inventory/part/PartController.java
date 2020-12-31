package com.codex.ecam.controller.inventory.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.WarrantyType;
import com.codex.ecam.constants.WarrantyUsageTermType;
import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.constants.inventory.PartUsageType;
import com.codex.ecam.dto.admin.AssetBrandDTO;
import com.codex.ecam.dto.admin.AssetModelDTO;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.dto.biz.part.PartDTO;
import com.codex.ecam.result.inventory.PartResult;
import com.codex.ecam.service.admin.api.MeterReadingUnitService;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.service.biz.api.BusinessGroupService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.inventory.api.PartService;
import com.codex.ecam.service.log.api.StockLogService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(PartController.REQUEST_MAPPING_URL)
public class PartController {

	public static final String REQUEST_MAPPING_URL = "/part";

	@Autowired
	private PartService partService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private BusinessGroupService businessGropService;

	@Autowired
	private MeterReadingUnitService meterReadingUnitService;

    @Autowired
    private StockLogService stockLogService;

	@Autowired
	private AssetCategoryService assetCategoryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "inventory/part/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "inventory/part/home-view";
	}

	@RequestMapping(value = "/user-select-modal-view", method = RequestMethod.GET)
	public String getUserSelectView(Model model) {
		return "inventory/part/modal/user-select-modal";
	}
	
	@RequestMapping(value = "/part-category-select-view", method = RequestMethod.GET)
	public String getCaregorySelectView(Model model) {
		return "inventory/part/modal/category/part-category-select-modal";
	}

	@RequestMapping(value = "/asset-category-add-view", method = RequestMethod.GET)
	public String categoryAdd(Model model, AssetCategoryType type) {
		model.addAttribute("type", type);
		model.addAttribute("sysCode", type.getId());
		model.addAttribute("assetCategory", new AssetCategoryDTO());
		model.addAttribute("assetCategoryParents", assetCategoryService.findByAssetCategoyType(type));
		return "inventory/part/modal/category/part-category-add-modal";
	}

	@RequestMapping(value = "/warranty-add-view", method = RequestMethod.GET)
	public String warrantyAddView(Model model) {
		model.addAttribute("warrantyTypes", WarrantyType.getWarrantyTypes());
		model.addAttribute("warrantyUsageTermTypes", WarrantyUsageTermType.getWarrantyUsageTermTypes());
		model.addAttribute("providers", businessService.findAllActualBusinessByLevel());
		model.addAttribute("meterReadingUnits", meterReadingUnitService.findAllMeterReadings());
		return "inventory/part/modal/warranty-add-modal";
	}

	@RequestMapping(value = "/asset-select-view", method = RequestMethod.GET)
	public String getAssetSelectView(Model model) {
		return "inventory/part/modal/asset-select-modal";
	}
	
	@RequestMapping(value = "/stock-add-view", method = RequestMethod.GET)
	public String getStockAddView(Model model) {
		return "inventory/part/modal/stock-add-modal";
	}

	@RequestMapping(value = "/warehouse-select-view", method = RequestMethod.GET)
	public String getStockWarehouseAddView(Model model) {
		return "inventory/part/modal/warehouse-select-modal";
	}

	@RequestMapping(value = "/business-select-view", method = RequestMethod.GET)
	public String businessDataTableView(Model model) {
		return "inventory/part/modal/business-select-modal";
	}

	@RequestMapping(value = "/site-select-view", method = RequestMethod.GET)
	public String siteDataTableView(Model model) {
		return "inventory/part/modal/site-select-modal";
	}

	@RequestMapping(value = "/brand-select-view", method = RequestMethod.GET)
	public String brandView(Model model) {
		return "inventory/part/modal/brand/brand-select-modal";
	}

	@RequestMapping(value = "/brand-add-view", method = RequestMethod.GET)
	public String brandAddView(Model model) {
		model.addAttribute("assetBrand", new AssetBrandDTO());
		return "inventory/part/modal/brand/brand-add-modal";
	}

	@RequestMapping(value = "/model-select-view", method = RequestMethod.GET)
	public String modelView(Model model) {
		return "inventory/part/modal/model/model-select-modal";
	}
	
	@RequestMapping(value = "/model-add-view", method = RequestMethod.GET)
	public String modelAddView(Model model) {
		model.addAttribute("assetModel", new AssetModelDTO());
		return "inventory/part/modal/model/model-add-modal";
	}
	
	@RequestMapping(value = "/notification-add-modal-view", method = RequestMethod.GET)
	public String notificationAddView(Model model) { 
		return "inventory/part/modal/notification-add-modal";
	}

	@RequestMapping(value = "/stock-notification-add-modal-view", method = RequestMethod.GET)
	public String stockNotificationAddView(Model model) { 
		return "inventory/part/modal/stock-notification-add-modal";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, AssetCategoryType type, RedirectAttributes ra) {
		try {
			setCommonData(model, new PartDTO());
			return "inventory/part/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/part/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			PartDTO dto = partService.findById(id);
			setCommonData(model, dto);
			return "inventory/part/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/part/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		PartResult result = partService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/part/index";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("part") @Valid PartDTO part, @RequestParam("partImage") MultipartFile image, Model model) throws Exception {

		PartResult result = partService.save(part, image);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, result.getDtoEntity());
		return "inventory/part/add-view";
	}

    private void setCommonData(Model model, PartDTO part) throws Exception {
        model.addAttribute("part", part);
        model.addAttribute("businessGroups", businessGropService.findAllByLevel());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("ledgerDTOs", stockLogService.findAllStockByPartId(part.getId()));
		model.addAttribute("partTypes", PartType.getPartTypes());
        model.addAttribute("usageTypes", PartUsageType.getPartUsageTypes());
    }

}
