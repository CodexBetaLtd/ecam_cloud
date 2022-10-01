package com.codex.ecam.controller.report.stockadjusment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.inventory.StockAdjustmentStatus;
import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.filter.StockAdjustmentFilterDTO;
import com.codex.ecam.service.report.stockadjustment.api.StockAdjustmentReportService;


@Controller
@RequestMapping(StockAdjustmentReportController.REQUEST_MAPPING_URL)
public class StockAdjustmentReportController {
	public static final String REQUEST_MAPPING_URL = "report/stockadjustment";
	
	@Autowired
	private StockAdjustmentReportService adjustmentReportService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		setCommonData(model);
		return "report/stockAdjustment/report-view";
	}
	
	@RequestMapping(value = "/partView", method = RequestMethod.GET)
	public String getPartView(Model model) {
		return "report/modal/tbl-modal-item";
		
	}
	@RequestMapping(value = "/stockView", method = RequestMethod.GET)
	public String getStockView(Model model) {
		return "report/modal/tbl-modal-stock";

	}

	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printPDF")
	public void printPDF(@Valid StockAdjustmentFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		adjustmentReportService.print(filterDTO, response, request, PrintType.PDF);
	}
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printCSV")
	public void printCSV(@Valid StockAdjustmentFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		adjustmentReportService.print(filterDTO, response, request, PrintType.CSV);
	}

	private void setCommonData(Model model) {
		model.addAttribute("filterDTO", new StockAdjustmentFilterDTO());
		model.addAttribute("statusList", StockAdjustmentStatus.getStatusList());
	}
}
