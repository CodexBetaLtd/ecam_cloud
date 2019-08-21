package com.neolith.focus.controller.admin.cmmssettings;

import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.admin.AssetEventTypeDTO;
import com.neolith.focus.result.admin.AssetEventTypeResult;
import com.neolith.focus.service.admin.api.AssetEventTypeService;
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
@RequestMapping(AssetEventTypeController.REQUEST_MAPPING_URL)
public class AssetEventTypeController {

	public static final String REQUEST_MAPPING_URL = "/assetEventType";

	@Autowired
	private AssetEventTypeService assetEventTypeService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new AssetEventTypeDTO());
		return "admin/cmmssetting/lookuptable/asseteventtype/add-view";
	}

	@RequestMapping(value = "/edit",method= RequestMethod.GET)
	public  String  findByIDAssetEventType(Integer id,Model model) {
		try {
			AssetEventTypeDTO assetEventTypeDTO = assetEventTypeService.findById(id);
			setCommonData(model, assetEventTypeDTO);
			return "admin/cmmssetting/lookuptable/asseteventtype/add-view";
		} catch (Exception e) {
            model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
            return "redirect:/cmmssettings/";
        }

	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	public String deleteAssetEventType(Integer id, Model model, RedirectAttributes ra) {

		AssetEventTypeResult result = assetEventTypeService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());

		}else{
            model.addAttribute("success", result.getMsgList());
        }
        model.addAttribute("assetEventType", new AssetEventTypeDTO());
		return "admin/cmmssetting/lookuptable/asseteventtype/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("assetEventType") AssetEventTypeDTO assetEventTypeDTO, Model model) throws Exception {

		AssetEventTypeResult result = assetEventTypeService.save(assetEventTypeDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, assetEventTypeDTO);
		return "admin/cmmssetting/lookuptable/asseteventtype/add-view";

	}

	private void setCommonData(Model model, AssetEventTypeDTO assetEventTypeDTO) {
		model.addAttribute("assetEventType", assetEventTypeDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
