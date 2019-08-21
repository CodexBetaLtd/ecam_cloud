package com.neolith.focus.controller.biz.business;

import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.biz.business.BussinessTypeDTO;
import com.neolith.focus.result.admin.BusinessTypeResult;
import com.neolith.focus.service.biz.api.BusinessService;
import com.neolith.focus.service.biz.api.BusinessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
            BussinessTypeDTO bussinessTypeDTO = bussinessTypeService.findById(id);
			setCommonData(model, bussinessTypeDTO);
			return "admin/cmmssetting/lookuptable/bussinestype/add-view";
		} catch (Exception e) {
            model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
            return "redirect:/cmmssettings/";
        }
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
    public String deleteBussinesType(Integer id, Model model) {

		BusinessTypeResult result = bussinessTypeService.delete(id);

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

		BusinessTypeResult result = bussinessTypeService.save(BussinessTypeDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, new BussinessTypeDTO());
		return "admin/cmmssetting/lookuptable/bussinestype/add-view";
	}

	private void setCommonData(Model model, BussinessTypeDTO BussinessTypeDTO) {
		model.addAttribute("bussinessType", BussinessTypeDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
