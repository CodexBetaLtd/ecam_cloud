package com.codex.ecam.controller.admin.assetcategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.TaskType;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.result.admin.AssetCategoryResult;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.service.biz.api.BusinessService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(AssetCategoryController.REQUEST_MAPPING_URL)
public class AssetCategoryController {

	public static final String REQUEST_MAPPING_URL = "/assetCategory";

	@Autowired
	private AssetCategoryService assetCategoryService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "admin/assetcategory/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "admin/assetcategory/home-view";
	}

	@RequestMapping(value = "/cmmssettings", method = RequestMethod.GET)
	public String cmmsSetting(Model model) {
		return "admin/cmmssetting/cmms-settings";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new AssetCategoryDTO());
			return "admin/assetcategory/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/assetCategory/index";
		}
	}

	@RequestMapping(value = "/task-add-modal-view", method = RequestMethod.GET)
	public String getTaskView(Model model) {
		model.addAttribute("taskTypes", TaskType.getTaskTypes());
		return "admin/assetcategory/modals/task-add-modal";
	}

	@RequestMapping(value = "/selectParent/{assetCategoryType}", method = RequestMethod.GET)
	public @ResponseBody
	List<AssetCategoryDTO> selectParent(@PathVariable("assetCategoryType") AssetCategoryType type) {
		final List<AssetCategoryDTO> assetCategories = assetCategoryService.findByAssetCategoyType(type);
		return assetCategories;
	}

	@RequestMapping(value = "/edit", method = {RequestMethod.POST, RequestMethod.GET})
	public String findByIDAssetCategory(Integer id, Model model, RedirectAttributes ra) {
		try {
			final AssetCategoryDTO assetCategoryDTO = assetCategoryService.findById(id);
			setCommonData(model, assetCategoryDTO);
			return "admin/assetcategory/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/assetCategory/index";
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.GET})
	public String deleteAssetCategory(Integer id, Model model) {

		final AssetCategoryResult result = assetCategoryService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		return "admin/assetcategory/home-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("assetCategory") @Valid AssetCategoryDTO assetCategory, Model model, String module) {

		AssetCategoryResult result;

		if (assetCategory.getId() != null && assetCategory.getId() > 0) {
			result = assetCategoryService.update(assetCategory);
		} else {
			result = assetCategoryService.save(assetCategory);
		}

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, assetCategory);
		if (module != null && module.equals("asset")) {
			return "asset/modals/category/asset-category-add-modal";
		} else {
			return "admin/assetcategory/add-view";
		}
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final AssetCategoryResult result = assetCategoryService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Asset Category already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/assetcategory/home-view";
	}

	private void setCommonData(Model model, AssetCategoryDTO dto) {
		model.addAttribute("assetCategory", dto);
		model.addAttribute("systemcodes", AssetCategoryType.getAssetCategoryTypes());
		model.addAttribute("assetCategoryParents", assetCategoryService.findByAssetCategoyType(dto.getType()));
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}
}
