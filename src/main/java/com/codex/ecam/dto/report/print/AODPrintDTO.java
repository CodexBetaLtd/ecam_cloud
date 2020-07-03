package com.codex.ecam.dto.report.print;


import java.util.List;

import com.codex.ecam.dto.report.data.aod.AODRepDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class AODPrintDTO implements JRDataSource {

	private int index = -1;
	private final List<AODRepDTO> list;

	public AODPrintDTO(final List<AODRepDTO> aodRepDTOs) {
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

		if("aodNo".equals(fieldName)) {
			value = list.get(index).getAodNo();
		}
		else if("customerName".equals(fieldName)){
			value = list.get(index).getCustomerName();
		}
		else if("customerNo".equals(fieldName)){
			value = list.get(index).getCustomerNo();
		}
		else if("date".equals(fieldName)){
			value = list.get(index).getDate();
		}
		else if("aodType".equals(fieldName)){
			value = list.get(index).getAodType();
		}
		else if("aodStatus".equals(fieldName)){
			value = list.get(index).getAodStatus();
		}


		return value;
	}
}
