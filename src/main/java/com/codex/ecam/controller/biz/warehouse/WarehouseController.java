package com.codex.ecam.controller.biz.warehouse;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.dto.biz.warehouse.WareHouseDTO;
import com.codex.ecam.result.admin.UserResult;
import com.codex.ecam.result.asset.WareHouseResult;
import com.codex.ecam.service.admin.api.CountryService;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.biz.api.WareHouseService;

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
	public  String parentAssetCategorySelectView(Model model,AssetCategoryType type){
		model.addAttribute("type", type);
		return "biz/warehouse/modal/category/category-select-modal";
	}

	@RequestMapping(value = "/assetview", method = RequestMethod.GET)
	public  String parentAssetSelectView(Model model){
		return "biz/warehouse/modal/warehouse-select-modal";
	}

	@RequestMapping(value = "/view/modal/countries", method = RequestMethod.GET)
	public String getCountryModalView(Model model, @RequestParam(name = "title", defaultValue = "Country(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/countries";
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
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/warehouse/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			final WareHouseDTO WearHouseDTO = wareHouseService.findById(id);;
			setCommonData(model, WearHouseDTO);
			return "biz/warehouse/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/warehouse/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) throws Exception {

		final WareHouseResult result = wareHouseService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/warehouse/index";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("wearhouse") @Valid WareHouseDTO dto, Model model) throws Exception {

		final WareHouseResult result = wareHouseService.save(dto);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, result.getDtoEntity());
		return "biz/warehouse/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final WareHouseResult result = wareHouseService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Warehouse already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "biz/warehouse/home-view";
	}

	private void setCommonData(Model model,WareHouseDTO dto) {
		model.addAttribute("warehouse", dto);
		model.addAttribute("type", AssetCategoryType.WAREHOUSE);
		model.addAttribute("countryList", countryService.findAll());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findSiteByBusinessId(dto.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
	}
}
