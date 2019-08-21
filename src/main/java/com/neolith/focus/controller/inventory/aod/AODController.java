package com.neolith.focus.controller.inventory.aod;

import com.neolith.focus.constants.AssetCategoryType;
import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.constants.inventory.AODStatus;
import com.neolith.focus.constants.inventory.AODType;
import com.neolith.focus.dto.inventory.aod.AODDTO;
import com.neolith.focus.result.inventory.AODResult;
import com.neolith.focus.service.asset.api.AssetService;
import com.neolith.focus.service.biz.api.BusinessService;
import com.neolith.focus.service.inventory.api.AODService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping(AODController.REQUEST_MAPPING_URL)
public class AODController {

	public static final String REQUEST_MAPPING_URL = "/aod";

	@Autowired
	private AODService aodService;
	@Autowired
	private BusinessService businessService;
	@Autowired
	private AssetService assetService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "inventory/aod/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "inventory/aod/home-view";
	}

	/*********************************************************************
	 * Modal Views
	 *********************************************************************/
    @RequestMapping(value = "/requestusermodalview", method = RequestMethod.GET)
    public String getUserView(Model model) {
        return "inventory/aod/modal/user-modal";
	}

    @RequestMapping(value = "/customermodalview", method = RequestMethod.GET)
    public String getCustomerView(Model model) {
        return "inventory/aod/modal/customer-modal";
	}

    @RequestMapping(value = "/workordermodalview", method = RequestMethod.GET)
    public String getPartView(Model model) {
        return "inventory/aod/modal/workorder-modal";
	}

	@RequestMapping(value = "/aodItemView", method = RequestMethod.GET)
	public String getItemView(Model model) {
		return "inventory/aod/modal/item-modal";
	}

    @RequestMapping(value = "/partmodalview", method = RequestMethod.GET)
    public String getItemAssetView(Model model) {
        return "inventory/aod/modal/asset-modal";
    }

    @RequestMapping(value = "/stockmodalview", method = RequestMethod.GET)
    public String getStockView(Model model, Integer id) {
        return "inventory/aod/modal/stock-modal";
    }


	/*********************************************************************
	 * CRUD Ops
	 *********************************************************************/

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, aodService.newAOD().getDtoEntity());
			return "inventory/aod/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/aod/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("aod") AODDTO dto, Model model) throws Exception {
		AODResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = aodService.update(dto);
		} else {
			result = aodService.save(dto);
		}
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, result.getDtoEntity());
		return "inventory/aod/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, aodService.findById(id).getDtoEntity());
			return "inventory/aod/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/aod/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		try {
			AODResult result = aodService.delete(id);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				ra.addFlashAttribute("error", result.getErrorList());
			} else {
				ra.addFlashAttribute("success", result.getMsgList());
			}
		} catch (Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/aod/index";
	}  

	@RequestMapping(value = "/statusChange", method = RequestMethod.GET)
	public String aodStatusChange(Integer id, AODStatus aodStatus, Model model, RedirectAttributes ra) throws Exception {
		AODResult result = null;
		if ((id != null) && (id > 0)) { 
			result = aodService.statusChange(id, aodStatus); 
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList());
			} else {
				model.addAttribute("success", result.getMsgList());
			} 
		} else {  
			ra.addFlashAttribute("error", "Please Save/Select the AOD first"); 
		}
		setCommonData(model, aodService.findById(id).getDtoEntity());
		return "inventory/aod/add-view";
	} 

	private void setCommonData(Model model, AODDTO dto) {
		model.addAttribute("aodTypes", AODType.getAODTypes());
		model.addAttribute("aod", dto);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findSiteByBusinessId(dto.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
	}

}
