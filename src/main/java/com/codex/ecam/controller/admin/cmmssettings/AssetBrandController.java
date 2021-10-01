package com.codex.ecam.controller.admin.cmmssettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.AssetBrandDTO;
import com.codex.ecam.result.admin.AssetBrandResult;
import com.codex.ecam.service.admin.api.AssetBrandService;
import com.codex.ecam.service.biz.api.BusinessService;

import java.util.ArrayList;

@Controller
@RequestMapping(AssetBrandController.REQUEST_MAPPING_URL)
public class AssetBrandController {

	public static final String REQUEST_MAPPING_URL = "/assetbrand";

	@Autowired
	private AssetBrandService assetBrandService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model){
		setCommonData(model, new AssetBrandDTO());
		return "admin/cmmssetting/lookuptable/assetbrands/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Model model, Integer id){
		try {
			final AssetBrandDTO assetBrandDTO = assetBrandService.findById(id);
			setCommonData(model, assetBrandDTO);
			return "admin/cmmssetting/lookuptable/assetbrands/add-view";
		} catch (final Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "admin/cmmssetting/lookuptable/assetbrands/add-view";
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteForm(Model model, Integer id, RedirectAttributes ra){
		final AssetBrandResult result = assetBrandService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addAttribute("error", result.getErrorList());
		} else {
			ra.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, new AssetBrandDTO());
		return "admin/cmmssetting/lookuptable/assetbrands/add-view";

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("assetBrand") AssetBrandDTO assetBrandDTO,Model model, String module) throws Exception {

		final AssetBrandResult result = assetBrandService.save(assetBrandDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, assetBrandDTO);
		if(module != null && module.equals("asset")){
			return "asset/modals/brand/asset-brand-add-modal";
		} else {
			return "admin/cmmssetting/lookuptable/assetbrands/add-view";
		}

	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final AssetBrandResult result = assetBrandService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Asset Brand already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/lookuptable/assetbrands/home-view";
	}

	private void setCommonData(Model model, AssetBrandDTO assetBrandDTO) {
		model.addAttribute("assetBrand", assetBrandDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
