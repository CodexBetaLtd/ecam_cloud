package com.codex.ecam.controller.inventory.bom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.inventory.bom.BOMGroupDTO;
import com.codex.ecam.result.inventory.BOMGroupResult;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.inventory.api.BOMGroupService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(BOMGroupController.REQUEST_MAPPING_URL)
public class BOMGroupController {

	public static final String REQUEST_MAPPING_URL = "/bomgroup";

	@Autowired
	private BOMGroupService bomGroupService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "inventory/bomgroup/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "inventory/bomgroup/home-view";
	}

	@RequestMapping(value = "/assetmodelview", method = RequestMethod.GET)
	public String getAssetTableView(Model model) {
		return "inventory/bomgroup/modal/asset-select-modal";
	}

	@RequestMapping(value = "/assetcategorymodelview", method = RequestMethod.GET)
	public String getAssetCategoryTableView(Model model) {
		return "inventory/bomgroup/modal/asset-category-select-modal";
	}

	@RequestMapping(value = "/partmodelview", method = RequestMethod.GET)
	public String getPartTableView(Model model) {
		return "inventory/bomgroup/modal/part-select-modal";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new BOMGroupDTO());
			return "inventory/bomgroup/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/bomgroup/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			final BOMGroupDTO bomGroup = bomGroupService.findById(id);
			setCommonData(model, bomGroup);
			return "inventory/bomgroup/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/bomgroup/index";
		}

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		final BOMGroupResult result = bomGroupService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/bomgroup/index";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("bomGroup") @Valid BOMGroupDTO bomGroup, Model model) {

		BOMGroupResult result;

		if (bomGroup.getId() != null && bomGroup.getId() > 0) {
			result = bomGroupService.update(bomGroup);
		} else {
			result = bomGroupService.save(bomGroup);
		}

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, bomGroup);
		return "inventory/bomgroup/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final BOMGroupResult result = bomGroupService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "BOM Group already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "inventory/bomgroup/home-view";
	}

	private void setCommonData(Model model, BOMGroupDTO bomGroup) {
		model.addAttribute("bomGroup", bomGroup);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}