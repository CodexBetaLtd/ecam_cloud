package com.codex.ecam.controller.report.assetdepareciation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.filter.assetdepreciation.AssetDepreciationFilterDTO;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.report.assetdepreciation.api.AssetDepreciationReportService;


@Controller
@RequestMapping(AssetDepreciationReportController.REQUEST_MAPPING_URL)
public class AssetDepreciationReportController {
	public static final String REQUEST_MAPPING_URL = "report/asset-depreciation";

	private AssetDepreciationReportService assetDepreciationReportService;

	private BusinessService businessService;

	@Autowired
	public AssetDepreciationReportController(AssetDepreciationReportService assetDepreciationReportService,
			BusinessService businessService) {
		super();
		this.assetDepreciationReportService = assetDepreciationReportService;
		this.businessService = businessService;
	}


	@GetMapping(value = "/")
	public String index(Model model) {
		setCommonData(model);
		return "report/assetdepreciation/report-view";
	}

	@GetMapping(value = "/modal/view/asset-categories")
	public String getAssetCategoryModalView(Model model) {
		return "report/assetdepreciation/datatables/dt-asset-categories";
	}

	@GetMapping(value = "/modal/view/assets")
	public String getAssetModalView(Model model, @RequestParam(name = "title", required = false) String title) {
		model.addAttribute("title", title);
		return "report/assetdepreciation/datatables/dt-assets";
	}


	@PostMapping( value = "/print", params = "printPDF")
	public void printPDF(@Valid AssetDepreciationFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		assetDepreciationReportService.print(filterDTO, response, request, PrintType.PDF);
	}

	@PostMapping( value = "/print", params = "printCSV")
	public void printCSV(@Valid AssetDepreciationFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		assetDepreciationReportService.print(filterDTO, response, request, PrintType.CSV);
	}

	private void setCommonData(Model model) {
		model.addAttribute("filterDTO", new AssetDepreciationFilterDTO());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}
}
