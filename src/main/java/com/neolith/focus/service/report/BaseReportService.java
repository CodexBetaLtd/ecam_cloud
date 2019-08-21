package com.neolith.focus.service.report;

import com.neolith.focus.dto.BaseReportDTO;
import com.neolith.focus.dto.BaseReportFilterDTO;

import java.io.OutputStream;
import java.util.List;

public interface BaseReportService<DTO extends BaseReportDTO, FILTER extends BaseReportFilterDTO> {

    DTO findReport(Integer id);

    List<DTO> findReport(FILTER filter);

    void generatePDFReport(List<DTO> dtoList, String reportDir, String inputFilePath, OutputStream outputStream);

    void generateCSVReport(List<DTO> dtoList, String reportDir, String inputFilePath, OutputStream outputStream);
}
