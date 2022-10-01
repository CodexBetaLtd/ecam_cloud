package com.codex.ecam.controller.inventory.issuenote;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.inventory.AODStatus;
import com.codex.ecam.dto.inventory.issuenote.IssueNoteDTO;
import com.codex.ecam.result.admin.UserResult;
import com.codex.ecam.result.inventory.IssueNoteResult;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.inventory.api.IssueNoteService;

@Controller
@RequestMapping(IssueNoteController.REQUEST_MAPPING_URL)
public class IssueNoteController {

	public static final String REQUEST_MAPPING_URL = "/issuenote";

	@Autowired
	private IssueNoteService issueNoteService;
	@Autowired
	private BusinessService businessService;
	@Autowired
	private AssetService assetService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "inventory/issuenote/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "inventory/issuenote/home-view";
	}

	/*********************************************************************
	 * Modal Views
	 *********************************************************************/
	@RequestMapping(value = "/issuedusermodalview", method = RequestMethod.GET)
	public String getUserView(Model model) {
		return "inventory/issuenote/modal/user-modal";
	}

	@RequestMapping(value = "/customermodalview", method = RequestMethod.GET)
	public String getCustomerView(Model model) {
		return "inventory/issuenote/modal/customer-modal";
	}


	@RequestMapping(value = "/issenoteitemview", method = RequestMethod.GET)
	public String getItemView(Model model) {
		return "inventory/issuenote/modal/item-modal";
	}

	@RequestMapping(value = "/partmodalview", method = RequestMethod.GET)
	public String getItemAssetView(Model model) {
		return "inventory/issuenote/modal/asset-modal";
	}

	@RequestMapping(value = "/stockmodalview", method = RequestMethod.GET)
	public String getStockView(Model model, Integer id) {
		return "inventory/issuenote/modal/stock-modal";
	}


	/*********************************************************************
	 * CRUD Ops
	 *********************************************************************/

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, issueNoteService.newIssueNote().getDtoEntity());
			return "inventory/issuenote/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/issuenote/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("issuenote") IssueNoteDTO dto, Model model) throws Exception {
		IssueNoteResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = issueNoteService.update(dto);
		} else {
			result = issueNoteService.save(dto);
		}
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, result.getDtoEntity());
		return "inventory/issuenote/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, issueNoteService.findById(id).getDtoEntity());
			return "inventory/issuenote/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/issuenote/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		try {
			final IssueNoteResult result = issueNoteService.delete(id);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				ra.addFlashAttribute("error", result.getErrorList());
			} else {
				ra.addFlashAttribute("success", result.getMsgList());
			}
		} catch (final Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/issuenote/index";
	}

	@RequestMapping(value = "/statusChange", method = RequestMethod.GET)
	public String aodStatusChange(Integer id, AODStatus aodStatus, Model model, RedirectAttributes ra) throws Exception {
		IssueNoteResult result = null;
		if (id != null && id > 0) {
			result = issueNoteService.statusChange(id, aodStatus);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList());
			} else {
				model.addAttribute("success", result.getMsgList());
			}
		} else {
			ra.addFlashAttribute("error", "Please Save/Select the AOD first");
		}
		setCommonData(model, result.getDtoEntity());
		return "inventory/issuenote/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final IssueNoteResult result = issueNoteService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Issue Note already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "inventory/issuenote/home-view";
	}

	private void setCommonData(Model model, IssueNoteDTO dto) {
		model.addAttribute("issuenote", dto);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findSiteByBusinessId(dto.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
	}

}
