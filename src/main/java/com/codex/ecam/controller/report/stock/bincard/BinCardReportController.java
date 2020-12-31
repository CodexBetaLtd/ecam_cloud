package com.codex.ecam.controller.report.stock.bincard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.inventory.AODStatus;
import com.codex.ecam.constants.inventory.AODType;
import com.codex.ecam.dto.report.filter.BinCardFilterDTO;
import com.codex.ecam.service.report.stock.bincard.api.BinCardReportService;


@Controller
@RequestMapping(BinCardReportController.REQUEST_MAPPING_URL)
public class BinCardReportController {
	public static final String REQUEST_MAPPING_URL = "report/bincard";
	
	@Autowired
	private BinCardReportService binCardReportService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		setCommonData(model);
		return "report/stock/binCard/report-view";
	}
	

	@RequestMapping(value = "/partView", method = RequestMethod.GET)
	public String getPartView(Model model) {
		return "report/stock/binCard/modal/asset-modal";
		
	}
	@RequestMapping(value = "/stockView", method = RequestMethod.GET)
	public String getStockView(Model model) {
		return "report/stock/binCard/modal/stock-modal";

	}
	
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printPDF")
	public void printPDF(@Valid BinCardFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		binCardReportService.print(filterDTO, response, request, "pdf");
	}
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printCSV")
	public void printCSV(@Valid BinCardFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		binCardReportService.print(filterDTO, response, request, "csv");
	}

	private void setCommonData(Model model) {
		model.addAttribute("filterDTO", new BinCardFilterDTO());
		model.addAttribute("aodTypes", AODType.getAllAODTypes());
		model.addAttribute("aodStatusList", AODStatus.getAODStatusList());
	}
}
