package com.codex.ecam.controller.inventory.mrnReturn;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.inventory.MRNReturnStatus;
import com.codex.ecam.dto.inventory.mrnReturn.MRNReturnDTO;
import com.codex.ecam.result.RestResult;
import com.codex.ecam.result.inventory.MRNReturnResult;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.inventory.api.MRNReturnService;

@Controller
@RequestMapping(MRNReturnController.REQUEST_MAPPING_URL)
public class MRNReturnController {

	public static final String REQUEST_MAPPING_URL = "/mrnReturn";

	@Autowired
	private MRNReturnService mrnReturnService;
	
	@Autowired
	private BusinessService businessService;
	@Autowired
	private AssetService assetService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "inventory/mrnReturn/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "inventory/mrnReturn/home-view";
	}

	/*********************************************************************
	 * Modal Views
	 *********************************************************************/
    @RequestMapping(value = "/requestusermodalview", method = RequestMethod.GET)
    public String getUserView(Model model) {
        return "inventory/mrnReturn/modal/user-modal";
	}

    @RequestMapping(value = "/mrnmodalview", method = RequestMethod.GET)
    public String getPartView(Model model) {
        return "inventory/mrnReturn/modal/mrn-modal";
	}

	@RequestMapping(value = "/mrnReturnItemView", method = RequestMethod.GET)
	public String getItemView(Model model) {
		return "inventory/mrnReturn/modal/item-modal";
	}

    @RequestMapping(value = "/mrnItemmodalview", method = RequestMethod.GET)
    public String getMRNItemView(Model model) {
        return "inventory/mrnReturn/modal/mrn-item-modal";
    }


	/*********************************************************************
	 * CRUD Ops
	 *********************************************************************/

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, mrnReturnService.newMRN().getDtoEntity());
			return "inventory/mrnReturn/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/mrnReturn/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("mrnReturn") MRNReturnDTO dto, Model model) throws Exception {
		MRNReturnResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = mrnReturnService.update(dto);
		} else {
			result = mrnReturnService.save(dto);
		}
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, result.getDtoEntity());
		return "inventory/mrnReturn/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, mrnReturnService.findById(id).getDtoEntity());
			return "inventory/mrnReturn/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/mrnReturn/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		try {
			MRNReturnResult result = mrnReturnService.delete(id);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				ra.addFlashAttribute("error", result.getErrorList());
			} else {
				ra.addFlashAttribute("success", result.getMsgList());
			}
		} catch (Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/mrnReturn/index";
	}  

	@RequestMapping(value = "/statusChange", method = RequestMethod.GET)
	public String mrnStatusChange(Integer id, MRNReturnStatus mrnReturnStatus, Model model, RedirectAttributes ra) throws Exception {
		MRNReturnResult result = null;
		if ((id != null) && (id > 0)) { 
		result = mrnReturnService.statusChange(id, mrnReturnStatus); 
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList());
			} else {
				model.addAttribute("success", result.getMsgList());
			} 
		} else {  
			ra.addFlashAttribute("error", "Please Save/Select the MRN Return first"); 
		}
		setCommonData(model, mrnReturnService.findById(id).getDtoEntity());
		return "inventory/mrnReturn/add-view";
	} 
	
	@RequestMapping(value = "/code-by-business", method = RequestMethod.GET)
	public @ResponseBody RestResult<String> codeByBusiness(Integer businessId) {
		RestResult<String> result = new RestResult<>();
		result.setData(mrnReturnService.getNextCode(businessId).toString());

		return result ;
	}

	private void setCommonData(Model model, MRNReturnDTO dto) {
		model.addAttribute("mrnReturn", dto);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findSiteByBusinessId(dto.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
	}

}
