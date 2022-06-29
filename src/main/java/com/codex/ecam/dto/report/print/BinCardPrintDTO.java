package com.codex.ecam.dto.report.print;


import java.util.List;

import com.codex.ecam.dto.report.data.BinCardRepDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class BinCardPrintDTO implements JRDataSource {

	private int index = -1;
	private final List<BinCardRepDTO> list;

	public BinCardPrintDTO(final List<BinCardRepDTO> aodRepDTOs) {
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

	if("binCardStockNo".equals(fieldName)) {
			value = list.get(index).getBinCardStockNo();
		}
		else if("binCardPartCode".equals(fieldName)){
			value = list.get(index).getBinCardPartCode();
		}
		else if("binCardStockDescription".equals(fieldName)){
			value = list.get(index).getBinCardStockDescription();
		}
		else if("binCardStockQty".equals(fieldName)){
			value = list.get(index).getBinCardStockQty();
		}
		else if("binCardAfterQty".equals(fieldName)){
			value = list.get(index).getBinCardAfterQty();
		}
		else if("binCardBeforeQty".equals(fieldName)){
			value = list.get(index).getBinCardBeforeQty();
		}
		else if("binCardBalance".equals(fieldName)){
			value = list.get(index).getBinCardBalance();
		}
		else if("binCardTotalCurrentQty".equals(fieldName)){
			value = list.get(index).getBinCardTotalCurrentQty();
		}
		else if("binCardDate".equals(fieldName)){
			value = list.get(index).getBinCardDate();
		}

		return value;
	}
}
