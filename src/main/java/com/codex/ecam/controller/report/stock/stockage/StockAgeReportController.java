package com.codex.ecam.controller.report.stock.stockage;

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
import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.dto.report.filter.PartFilterDTO;
import com.codex.ecam.dto.report.filter.ReceiptOrderFilterDTO;
import com.codex.ecam.dto.report.filter.StockAgeFilterDTO;
import com.codex.ecam.service.report.aod.api.AODReportService;
import com.codex.ecam.service.report.part.api.PartReportService;
import com.codex.ecam.service.report.stock.stockage.api.StockAgeReportService;


@Controller
@RequestMapping(StockAgeReportController.REQUEST_MAPPING_URL)
public class StockAgeReportController {
	public static final String REQUEST_MAPPING_URL = "report/stockage";
	
	@Autowired
	private StockAgeReportService stockAgeReportService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		setCommonData(model);
		return "report/stock/stockAge/report-view";
	}
	

	@RequestMapping(value = "/partView", method = RequestMethod.GET)
	public String getPartView(Model model) {
		return "report/modal/tbl-modal-item";
		
	}
	
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printPDF")
	public void printPDF(@Valid StockAgeFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		stockAgeReportService.print(filterDTO, response, request, "pdf");
	}
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printCSV")
	public void printCSV(@Valid StockAgeFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		stockAgeReportService.print(filterDTO, response, request, "csv");
	}

	private void setCommonData(Model model) {
		model.addAttribute("filterDTO", new StockAgeFilterDTO());
		model.addAttribute("aodTypes", AODType.getAllAODTypes());
		model.addAttribute("aodStatusList", AODStatus.getAODStatusList());
	}
}
