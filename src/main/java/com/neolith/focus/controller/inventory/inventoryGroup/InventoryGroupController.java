package com.neolith.focus.controller.inventory.inventoryGroup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neolith.focus.constants.AssetCategoryType;
import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.inventory.inventoryGroup.InventoryGroupDTO;
import com.neolith.focus.result.inventory.InventoryGroupResult;
import com.neolith.focus.service.asset.api.AssetService;
import com.neolith.focus.service.biz.api.BusinessService;
import com.neolith.focus.service.inventory.api.InventoryGroupService;

@Controller
@RequestMapping(InventoryGroupController.REQUEST_MAPPING_URL)
public class InventoryGroupController {

	public static final String REQUEST_MAPPING_URL = "/inventorygroup";

    @Autowired
    private InventoryGroupService inventoryGroupService;
    
	@Autowired
	private BusinessService businessService;
	@Autowired
	private AssetService assetService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "inventory/inventorygroup/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "inventory/inventorygroup/home-view";
	}

	/*********************************************************************
	 * Modal Views
	 *********************************************************************/
	@RequestMapping(value = "/partView", method = RequestMethod.GET)
	public String getPartSelectView(Model model) {
		return "inventory/inventorygroup/modal/part-select-modal";
	}


	/*********************************************************************
	 * CRUD Ops
	 *********************************************************************/

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, inventoryGroupService.newInventoryGroup().getDtoEntity());
			return "inventory/inventorygroup/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/inventorygroup/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("inventorygroup") InventoryGroupDTO dto, Model model) throws Exception {
		InventoryGroupResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = inventoryGroupService.update(dto);
		} else {
			result = inventoryGroupService.save(dto);
		}
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, result.getDtoEntity());
		return "inventory/inventorygroup/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, inventoryGroupService.findById(id).getDtoEntity());
			return "inventory/inventorygroup/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/inventorygroup/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		try {
			InventoryGroupResult result = inventoryGroupService.delete(id);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				ra.addFlashAttribute("error", result.getErrorList());
			} else {
				ra.addFlashAttribute("success", result.getMsgList());
			}
		} catch (Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/inventorygroup/index";
	}


	/*********************************************************************
	 * Common / Utility
	 *********************************************************************/

	private void setCommonData(Model model, InventoryGroupDTO dto) {
		model.addAttribute("inventorygroup", dto);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findSiteByBusinessId(dto.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
	}

}
