package com.neolith.focus.controller.admin.cmmssettings;

import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.admin.MeterReadingUnitDTO;
import com.neolith.focus.result.admin.MeterReadingUnitResult;
import com.neolith.focus.service.admin.api.MeterReadingUnitService;
import com.neolith.focus.service.biz.api.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping(MeterReadingUnitController.REQUEST_MAPPING_URL)
public class MeterReadingUnitController {

	public static final String REQUEST_MAPPING_URL = "/meterreadingunits";

	@Autowired
	private MeterReadingUnitService meterReadingUnitService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "asset/assetcategory/home-view";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new MeterReadingUnitDTO());
		return "admin/cmmssetting/lookuptable/meterreadingunits/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByIDMeterReadingUnits(Integer id, Model model) {
		try {
			MeterReadingUnitDTO meterReadingUnitsDTO = meterReadingUnitService.findById(id);
			setCommonData(model, meterReadingUnitsDTO);
			return "admin/cmmssetting/lookuptable/meterreadingunits/add-view";
		} catch (Exception e) {
            model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
            return "redirect:/";
        }
	}


	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteMeterReadingUnit(Integer id, Model model, RedirectAttributes ra) {
		MeterReadingUnitResult result = meterReadingUnitService.delete(id);
		if(result.getStatus().equals(ResultStatus.ERROR)){
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
        setCommonData(model, new MeterReadingUnitDTO());
		return "admin/cmmssetting/lookuptable/meterreadingunits/add-view";
	}


	@RequestMapping(value = "/save", method = {RequestMethod.POST, RequestMethod.GET})
	public String saveOrUpdate(@ModelAttribute("meterReadingUnit") MeterReadingUnitDTO meterReadingUnit, Model model) throws Exception {

		MeterReadingUnitResult result = meterReadingUnitService.save(meterReadingUnit);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, meterReadingUnit);

		return "admin/cmmssetting/lookuptable/meterreadingunits/add-view";
	}

	private void setCommonData(Model model, MeterReadingUnitDTO meterReadingUnit) {
		model.addAttribute("meterReadingUnit", meterReadingUnit);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
