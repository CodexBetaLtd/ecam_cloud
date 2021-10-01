package com.codex.ecam.controller.admin.cmmssettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.AssetModelDTO;
import com.codex.ecam.result.admin.AssetModelResult;
import com.codex.ecam.service.admin.api.AssetBrandService;
import com.codex.ecam.service.admin.api.AssetModelService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(AssetModelController.REQUEST_MAPPING_URL)
public class AssetModelController {

	public static final String REQUEST_MAPPING_URL = "/assetmodel";

	@Autowired
	private AssetModelService assetModelService;

	@Autowired
	private AssetBrandService assetBrandService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model){
		setCommonData(model, new AssetModelDTO());
		return "admin/cmmssetting/lookuptable/assetmodels/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Model model, Integer id){
		try {
			final AssetModelDTO assetModelDTO = assetModelService.findById(id);
			setCommonData(model, assetModelDTO);
			return "admin/cmmssetting/lookuptable/assetmodels/add-view";
		} catch (final Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "redirect:/cmmssettings/";
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteForm(Model model, Integer id,  RedirectAttributes ra){
		final AssetModelResult result = assetModelService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addAttribute("error", result.getErrorList());
		} else {
			ra.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, new AssetModelDTO());
		return "admin/cmmssetting/lookuptable/assetmodels/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("assetModel") AssetModelDTO assetModelDTO, Model model, String module) throws Exception {

		final AssetModelResult result = assetModelService.save(assetModelDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, assetModelDTO);

		if(module != null && module.equals("asset")){
			return "asset/modals/model/asset-model-add-modal";
		} else {
			return "admin/cmmssetting/lookuptable/assetmodels/add-view";
		}

	}

	@RequestMapping(value = "/getmodels", method = RequestMethod.GET)
	public @ResponseBody List<AssetModelDTO> getModels(Model model, Integer id)throws Exception{
		final List<AssetModelDTO> assetModel = assetModelService.findByBrandId(id);
		return assetModel;
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final AssetModelResult result = assetModelService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Asset Model already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/lookuptable/assetmodels/home-view";
	}

	private void setCommonData(Model model, AssetModelDTO dto){
		model.addAttribute("assetModel", dto);
		model.addAttribute("assetBrand", assetBrandService.findAll());
	}
}
