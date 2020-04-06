package com.codex.ecam.dto.report.print;


import java.util.List;

import com.codex.ecam.dto.report.data.ReceiptOrderRepDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ReceiptOrderPrintDTO implements JRDataSource {

	private int index = -1;
	private final List<ReceiptOrderRepDTO> list;

	public ReceiptOrderPrintDTO(final List<ReceiptOrderRepDTO> receiptOrderRepDTOs) {
		this.list = receiptOrderRepDTOs;
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

		if("code".equals(fieldName)) {
			value = list.get(index).getCode();
		}
		else if("orderNo".equals(fieldName)){
			value = list.get(index).getOrderNo();
		}
		else if("dateOrdered".equals(fieldName)){
			value = list.get(index).getDateOrdered();
		}
		else if("dateReceived".equals(fieldName)){
			value = list.get(index).getDateReceived();
		}


		return value;
	}
}
