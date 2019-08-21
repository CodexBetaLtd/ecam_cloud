package com.neolith.focus.controller.biz.warehouse;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neolith.focus.constants.AssetCategoryType;
import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.asset.AssetCategoryDTO;
import com.neolith.focus.dto.biz.warehouse.WareHouseDTO;
import com.neolith.focus.result.asset.WareHouseResult;
import com.neolith.focus.service.admin.api.CountryService;
import com.neolith.focus.service.asset.api.AssetCategoryService; 
import com.neolith.focus.service.asset.api.AssetService; 
import com.neolith.focus.service.biz.api.BusinessService;
import com.neolith.focus.service.biz.api.WareHouseService; 

@Controller
@RequestMapping(WarehouseController.REQUEST_MAPPING_URL)
public class WarehouseController {

	public static final String REQUEST_MAPPING_URL = "/warehouse";

	@Autowired
	private WareHouseService wareHouseService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private AssetService assetService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private AssetCategoryService assetCategoryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "biz/warehouse/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "biz/warehouse/home-view";
	}

	@RequestMapping(value = "/assetcategoryview", method = RequestMethod.GET)
	public  String parentAssetSelectView(Model model,AssetCategoryType type){
		model.addAttribute("type", type);
		return "biz/warehouse/modal/category/category-select-modal";
	}

	@RequestMapping(value = "/assetcategoryadd", method = RequestMethod.GET)
	public String categoryAdd(Model model, AssetCategoryType type) {
		model.addAttribute("type", type);
		model.addAttribute("sysCode", type.getId());
		model.addAttribute("assetCategory", new AssetCategoryDTO());
		model.addAttribute("assetCategoryParents", assetCategoryService.findByAssetCategoyType(type));
		return "biz/warehouse/modal/category/category-add-modal";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, AssetCategoryType type, RedirectAttributes ra) {
		try {
			setCommonData(model, new WareHouseDTO());
			return "biz/warehouse/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/warehouse/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			WareHouseDTO WearHouseDTO = wareHouseService.findById(id);;
			setCommonData(model, WearHouseDTO);
			return "biz/warehouse/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/warehouse/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) throws Exception {

		WareHouseResult result = wareHouseService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/warehouse/index";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("wearhouse") @Valid WareHouseDTO dto, Model model) throws Exception {

		WareHouseResult result = wareHouseService.save(dto);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, result.getDtoEntity());
		return "biz/warehouse/add-view";
	}

	private void setCommonData(Model model,WareHouseDTO dto) {
		model.addAttribute("warehouse", dto);
		model.addAttribute("type", AssetCategoryType.WAREHOUSE);
		model.addAttribute("countryList", countryService.findAll());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findSiteByBusinessId(dto.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
	}
}
