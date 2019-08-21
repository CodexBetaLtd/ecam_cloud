package com.codex.ecam.service.report;

import java.io.OutputStream;
import java.util.List;

import com.codex.ecam.dto.BaseReportDTO;
import com.codex.ecam.dto.BaseReportFilterDTO;

public interface BaseReportService<DTO extends BaseReportDTO, FILTER extends BaseReportFilterDTO> {

    DTO findReport(Integer id);

    List<DTO> findReport(FILTER filter);

    void generatePDFReport(List<DTO> dtoList, String reportDir, String inputFilePath, OutputStream outputStream);

    void generateCSVReport(List<DTO> dtoList, String reportDir, String inputFilePath, OutputStream outputStream);
}
