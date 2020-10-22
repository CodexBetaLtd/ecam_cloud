package com.codex.ecam.controller.report.asset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.filter.AssetFilterDTO;
import com.codex.ecam.service.report.asset.api.AssetReportService;
import com.codex.ecam.util.AuthenticationUtil;


@Controller
@RequestMapping(AssetReportController.REQUEST_MAPPING_URL)
public class AssetReportController {
	public static final String REQUEST_MAPPING_URL = "report/asset";
	
	@Autowired
	private AssetReportService assetReportService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		setCommonData(model);
		return "report/asset/report-view";
	}
	
	@RequestMapping(value = "/locationview", method = RequestMethod.GET)
	public String locationView(Model model) {
		return "report/asset/asset-location-select-modal";
	}
	
	@RequestMapping(value = "/modelview", method = RequestMethod.GET)
	public String modelView(Model model) {
		return "report/asset/model-select-modal";
	}
	

	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printPDF")
	public void printPDF(@Valid AssetFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		assetReportService.print(filterDTO, response, request, PrintType.PDF);
	}
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printCSV")
	public void printCSV(@Valid AssetFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		assetReportService.print(filterDTO, response, request, PrintType.CSV);
	}

	@RequestMapping( value = "/printAod", method = RequestMethod.GET)
	public void printPDF(@Valid Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		assetReportService.printDoc(id, response, request);
	}
	
	private void setCommonData(Model model) {
		model.addAttribute("filterDTO", new AssetFilterDTO());
		model.addAttribute("currency", AuthenticationUtil.getLoginUserBusiness().getCurrency().getSymbol());
	}
}
