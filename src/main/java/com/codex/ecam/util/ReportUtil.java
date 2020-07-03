package com.codex.ecam.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codex.ecam.dto.BaseReportDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.CsvExporterConfiguration;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class ReportUtil {

	private static ReportUtil instance = null;

	private ReportUtil() {
	}

	public static ReportUtil getInstance() {
		if (instance == null) {
			instance = new ReportUtil();
		}
		return instance;
	}


	public void generatePDF(final JRDataSource dataSource, final InputStream jasperStream,
			final Map<String, Object> params, final OutputStream outputStream) {
		try {
			final JasperDesign jasperDesign = JRXmlLoader.load(jasperStream);
			final JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}
	public void generatePDF(List<?> dataList, final InputStream jasperStream,
			final Map<String, Object> params, final OutputStream outputStream) {
		 JRBeanCollectionDataSource beanColDataSource =new JRBeanCollectionDataSource(dataList);
		try {
			final JasperDesign jasperDesign = JRXmlLoader.load(jasperStream);
			final JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	public void generateCSV(final JRDataSource dataSource, final InputStream jsInputStream,
			final Map<String, Object> params, final OutputStream outputStream) {
		try {
			params.put(JRParameter.IS_IGNORE_PAGINATION, true);
			final JasperDesign jasperDesign = JRXmlLoader.load(jsInputStream);
			final JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
			exportCSV(outputStream, jasperPrint);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void exportCSV(final OutputStream outputStream, final JasperPrint jasperPrint) throws JRException {
		final ExporterInput input = new SimpleExporterInput(jasperPrint);
		final SimpleWriterExporterOutput output = new SimpleWriterExporterOutput(outputStream);

		final JRCsvExporter cvsExporter = new JRCsvExporter();
		cvsExporter.setExporterInput(input);
		cvsExporter.setExporterOutput(output);
		cvsExporter.exportReport();
	}


	
	public InputStream getInputStream(HttpServletRequest request,String pathToTemplate, String jasperFileName) throws FileNotFoundException {
		final String filePath = request.getServletContext().getRealPath("").concat(pathToTemplate).concat(jasperFileName);
		final InputStream jasperStream = new FileInputStream(filePath);
		return jasperStream;
	}

	public OutputStream getOutPutStram(HttpServletResponse response, String fileName, String contentType, String extension) throws IOException {
		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename=".concat( fileName ).concat(extension));

		final OutputStream outputStream = response.getOutputStream();
		return outputStream;
	}

}
