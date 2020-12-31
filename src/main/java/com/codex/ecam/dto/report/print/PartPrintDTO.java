package com.codex.ecam.dto.report.print;

import java.util.List;

import com.codex.ecam.dto.report.data.PartRepDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class PartPrintDTO implements JRDataSource {

	private int index = -1;
	private final List<PartRepDTO> parts;

	public PartPrintDTO(final List<PartRepDTO> partRepDTOs) {
		this.parts = partRepDTOs;
	}

	@Override
	public boolean next() throws JRException {
		index++;
		return index < parts.size();
	}

	@Override
	public Object getFieldValue(final JRField field) throws JRException {

		Object value = null;
		final String fieldName = field.getName();

		if("partReportItemCode".equals(fieldName)) {
			value = parts.get(index).getPartReportItemCode();
		}
		else if("partReportItemDescription".equals(fieldName)){
			value = parts.get(index).getPartReportItemDescription();
		}
		else if("partReportPartNo".equals(fieldName)){
			value = parts.get(index).getPartReportPartNo();
		}
		else if("partType".equals(fieldName)){
			value = parts.get(index).getPartType();
		}

		return value;
	}

}
