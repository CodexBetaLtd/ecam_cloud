package com.codex.ecam.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.codex.ecam.dto.BaseReportDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class ReportUtil_0 {

    private static ReportUtil_0 instance = null;

    private ReportUtil_0() {
    }

    public static ReportUtil_0 getInstance() {
        if (instance == null) {
            instance = new ReportUtil_0();
        }
        return instance;
    }

    public JasperPrint generatePDF(List<? extends BaseReportDTO> dtoList, String reportDir, Map<String, Object> params, String inputFilePath, OutputStream outputStream) {
        try {
            params.put("SUBREPORT_DIR", reportDir);
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(dtoList);
            InputStream jasperStream = new FileInputStream(reportDir.concat(inputFilePath).concat(".jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(jasperStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrDataSource);
          //  JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            return jasperPrint;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
    }

    public void generateCSV(List<? extends BaseReportDTO> dtoList, String reportDir, Map<String, Object> params, String inputFilePath, OutputStream outputStream) {
        try {
            params.put("SUBREPORT_DIR", reportDir);
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(dtoList);
            InputStream jasperStream = new FileInputStream(reportDir.concat(inputFilePath).concat(".jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(jasperStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrDataSource);

            ExporterInput input = new SimpleExporterInput(jasperPrint);
            SimpleWriterExporterOutput output = new SimpleWriterExporterOutput(outputStream);

            JRCsvExporter cvsExporter = new JRCsvExporter();
            cvsExporter.setExporterInput(input);
            cvsExporter.setExporterOutput(output);
            cvsExporter.exportReport();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
