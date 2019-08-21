package com.codex.ecam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.codex.ecam.dto.BaseReportDTO;
import com.codex.ecam.dto.BaseReportFilterDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseReportController<DTO extends BaseReportDTO, REPFilterDTO extends BaseReportFilterDTO> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public abstract String generatePDF(REPFilterDTO repFilterDTO, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public abstract String generateCSV(REPFilterDTO repFilterDTO, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public abstract void getPDF(List<DTO> dtoList, String reportDir, String inputFilePath, OutputStream outputStream) throws Exception;

    public abstract void getCVS(List<DTO> dtoList, String reportDir, String inputFilePath, OutputStream outputStream) throws Exception;


    protected void toPDF(DTO dto, HttpServletRequest request, HttpServletResponse response, String inputFilePath, String fileName) throws Exception {
        List<DTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        toPDF(dtoList, request, response, inputFilePath, fileName);
    }

    @SuppressWarnings("deprecation")
    protected void toPDF(List<DTO> dtoList, HttpServletRequest request, HttpServletResponse response, String inputFilePath, String fileName) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=".concat(fileName).concat(".pdf"));
        String reportDir = request.getRealPath("").concat("/resources/report/");
        OutputStream outputStream = response.getOutputStream();
        try {
            getPDF(dtoList, reportDir, inputFilePath, outputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        outputStream.flush();
        outputStream.close();
    }

    protected void toCSV(DTO dto, HttpServletRequest request, HttpServletResponse response, String inputFilePath, String fileName) throws Exception {
        List<DTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        toCSV(dtoList, request, response, inputFilePath, fileName);
    }

    @SuppressWarnings("deprecation")
    protected void toCSV(List<DTO> dtoList, HttpServletRequest request, HttpServletResponse response, String inputFilePath, String fileName) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=".concat(fileName).concat(".csv"));

        String reportDir = request.getRealPath("").concat("/resources/reports/");
        OutputStream outputStream = response.getOutputStream();
        try {
            getCVS(dtoList, reportDir, inputFilePath, outputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        outputStream.flush();
        outputStream.close();
    }

}


