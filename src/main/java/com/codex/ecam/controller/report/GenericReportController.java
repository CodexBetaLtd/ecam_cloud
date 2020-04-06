package com.codex.ecam.controller.report;


import org.springframework.ui.Model;

import com.codex.ecam.dto.BaseReportDTO;
import com.codex.ecam.dto.BaseReportFilterDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dev on 18/10/17.
 */
public interface GenericReportController<DTO extends BaseReportDTO, REPFilterDTO extends BaseReportFilterDTO> {

    public String generatePDF(REPFilterDTO repFilterDTO, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public String generateCSV(REPFilterDTO repFilterDTO, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
