package com.codex.ecam.dto.report.print;


import java.util.List;

import com.codex.ecam.dto.report.data.aodReturn.AODReturnRepDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class AODReturnPrintDTO implements JRDataSource {

	private int index = -1;
	private final List<AODReturnRepDTO> list;

	public AODReturnPrintDTO(final List<AODReturnRepDTO> aodRepDTOs) {
		this.list = aodRepDTOs;
	}

	@Override
	public boolean next() throws JRException {
		index++;
		return index < list.size();
	}

	@Override
	public Object getFieldValue(final JRField field) throws JRException {

		Object value = null;
		final String fieldName = field.getName();

		if("aodReturnNo".equals(fieldName)) {
			value = list.get(index).getAodReturnNo();
		}
		else if("aodReturnRefNo".equals(fieldName)){
			value = list.get(index).getAodReturnRefNo();
		}
		else if("aodReturnDate".equals(fieldName)){
			value = list.get(index).getAodReturnDate();
		}
		else if("aodReturnStatus".equals(fieldName)){
			value = list.get(index).getAodReturnStatus();
		}

		return value;
	}
}
