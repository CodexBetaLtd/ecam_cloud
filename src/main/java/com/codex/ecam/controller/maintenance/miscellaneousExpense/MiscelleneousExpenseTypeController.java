package com.codex.ecam.controller.maintenance.miscellaneousExpense;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.codex.ecam.result.admin.MiscellaneousExpenseTypeResult;
import com.codex.ecam.service.admin.api.MiscellaneousExpenseTypeService;
import com.codex.ecam.service.biz.api.BusinessService;

@Controller
@RequestMapping(MiscelleneousExpenseTypeController.REQUEST_MAPPING_URL)
public class MiscelleneousExpenseTypeController {

	public static final String REQUEST_MAPPING_URL = "/miscellaneousexpensetype";

	@Autowired
	private MiscellaneousExpenseTypeService miscellaneousExpenseTypeService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new MiscellaneousExpenseTypeDTO());
		return "admin/cmmssetting/lookuptable/miscellaneousexpensetype/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByIDCertification(Integer id, Model model) {
		try {
			MiscellaneousExpenseTypeDTO miscellaneousExpenseTypeDTO = miscellaneousExpenseTypeService.findById(id);
			setCommonData(model, miscellaneousExpenseTypeDTO);
			return "admin/cmmssetting/lookuptable/miscellaneousexpensetype/add-view";
		} catch (Exception e) {
            model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
            return "redirect:/cmmssettings/";
        }
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteCertification(Integer id, Model model, RedirectAttributes ra) {

		MiscellaneousExpenseTypeResult result = miscellaneousExpenseTypeService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, new MiscellaneousExpenseTypeDTO());

		return "admin/cmmssetting/lookuptable/miscellaneousexpensetype/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("certification") MiscellaneousExpenseTypeDTO miscellaneousExpenseTypeDTO, Model model) throws Exception {

		MiscellaneousExpenseTypeResult result = miscellaneousExpenseTypeService.save(miscellaneousExpenseTypeDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
		
		setCommonData(model, result.getDtoEntity());
		
		return "admin/cmmssetting/lookuptable/miscellaneousexpensetype/add-view";
	}

	private void setCommonData(Model model, MiscellaneousExpenseTypeDTO miscellaneousExpenseTypeDTO) {
		model.addAttribute("miscellaneousExpenseType", miscellaneousExpenseTypeDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
