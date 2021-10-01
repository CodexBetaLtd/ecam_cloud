package com.codex.ecam.controller.biz.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.biz.business.BussinessTypeDTO;
import com.codex.ecam.result.admin.BusinessTypeResult;
import com.codex.ecam.result.admin.UserResult;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.biz.api.BusinessTypeService;

import java.util.ArrayList;

@Controller
@RequestMapping(BusinessTypeController.REQUEST_MAPPING_URL)
public class BusinessTypeController {

	public static final String REQUEST_MAPPING_URL = "/businessType";

	@Autowired
	private BusinessTypeService bussinessTypeService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new BussinessTypeDTO());
		return "admin/cmmssetting/lookuptable/bussinestype/add-view";
	}

	@RequestMapping(value = "/edit",method= RequestMethod.GET)
	public String findByIDBussinesType(Integer id, Model model) {
		try {
			final BussinessTypeDTO bussinessTypeDTO = bussinessTypeService.findById(id);
			setCommonData(model, bussinessTypeDTO);
			return "admin/cmmssetting/lookuptable/bussinestype/add-view";
		} catch (final Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "redirect:/cmmssettings/";
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	public String deleteBussinesType(Integer id, Model model) {

		final BusinessTypeResult result = bussinessTypeService.delete(id);

		if(result.getStatus().equals(ResultStatus.ERROR)){
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, new BussinessTypeDTO());
		return "admin/cmmssetting/lookuptable/bussinestype/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("bussinessType") BussinessTypeDTO BussinessTypeDTO, Model model) throws Exception {

		final BusinessTypeResult result = bussinessTypeService.save(BussinessTypeDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, new BussinessTypeDTO());
		return "admin/cmmssetting/lookuptable/bussinestype/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final BusinessTypeResult result = bussinessTypeService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Business Type already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/lookuptable/bussinestype/home-view";
	}

	private void setCommonData(Model model, BussinessTypeDTO BussinessTypeDTO) {
		model.addAttribute("bussinessType", BussinessTypeDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
