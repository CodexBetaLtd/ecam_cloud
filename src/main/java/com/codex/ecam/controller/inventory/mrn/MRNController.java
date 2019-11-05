package com.codex.ecam.controller.inventory.mrn;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.inventory.MRNStatus;
import com.codex.ecam.constants.inventory.MRNType;
import com.codex.ecam.dto.inventory.mrn.MRNDTO;
import com.codex.ecam.result.inventory.MRNResult;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.inventory.api.AODService;
import com.codex.ecam.service.inventory.api.MRNService;

@Controller
@RequestMapping(MRNController.REQUEST_MAPPING_URL)
public class MRNController {

	public static final String REQUEST_MAPPING_URL = "/mrn";

	@Autowired
	private AODService aodService;
	@Autowired
	private MRNService mrnService;
	@Autowired
	private BusinessService businessService;
	@Autowired
	private AssetService assetService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "inventory/mrn/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "inventory/mrn/home-view";
	}

	/*********************************************************************
	 * Modal Views
	 *********************************************************************/
    @RequestMapping(value = "/requestusermodalview", method = RequestMethod.GET)
    public String getUserView(Model model) {
        return "inventory/mrn/modal/user-modal";
	}

    @RequestMapping(value = "/customermodalview", method = RequestMethod.GET)
    public String getCustomerView(Model model) {
        return "inventory/mrn/modal/customer-modal";
	}

    @RequestMapping(value = "/workordermodalview", method = RequestMethod.GET)
    public String getPartView(Model model) {
        return "inventory/mrn/modal/workorder-modal";
	}

	@RequestMapping(value = "/mrnItemView", method = RequestMethod.GET)
	public String getItemView(Model model) {
		return "inventory/mrn/modal/item-modal";
	}

    @RequestMapping(value = "/partmodalview", method = RequestMethod.GET)
    public String getItemAssetView(Model model) {
        return "inventory/mrn/modal/asset-modal";
    }

    @RequestMapping(value = "/stockmodalview", method = RequestMethod.GET)
    public String getStockView(Model model, Integer id) {
        return "inventory/mrn/modal/stock-modal";
    }


	/*********************************************************************
	 * CRUD Ops
	 *********************************************************************/

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, mrnService.newMRN().getDtoEntity());
			return "inventory/mrn/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/mrn/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("mrn") MRNDTO dto, Model model) throws Exception {
		MRNResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = mrnService.update(dto);
		} else {
			result = mrnService.save(dto);
		}
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, result.getDtoEntity());
		return "inventory/mrn/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, mrnService.findById(id).getDtoEntity());
			return "inventory/mrn/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/mrn/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		try {
			MRNResult result = mrnService.delete(id);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				ra.addFlashAttribute("error", result.getErrorList());
			} else {
				ra.addFlashAttribute("success", result.getMsgList());
			}
		} catch (Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/mrn/index";
	}  

	@RequestMapping(value = "/statusChange", method = RequestMethod.GET)
	public String mrnStatusChange(Integer id, MRNStatus mrnStatus, Model model, RedirectAttributes ra) throws Exception {
		MRNResult result = null;
		if ((id != null) && (id > 0)) { 
		result = mrnService.statusChange(id, mrnStatus); 
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList());
			} else {
				model.addAttribute("success", result.getMsgList());
			} 
		} else {  
			ra.addFlashAttribute("error", "Please Save/Select the MRN first"); 
		}
		setCommonData(model, mrnService.findById(id).getDtoEntity());
		return "inventory/mrn/add-view";
	} 

	private void setCommonData(Model model, MRNDTO dto) {
		model.addAttribute("mrnTypes", MRNType.getMRNTypes());
		model.addAttribute("mrn", dto);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findSiteByBusinessId(dto.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
	}

}
