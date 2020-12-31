package com.codex.ecam.dto.report.print;


import java.util.List;

import com.codex.ecam.dto.report.data.StockAgeRepDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class StockAgePrintDTO implements JRDataSource {

	private int index = -1;
	private final List<StockAgeRepDTO> list;

	public StockAgePrintDTO(final List<StockAgeRepDTO> aodRepDTOs) {
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

	if("stockAgeStockNo".equals(fieldName)) {
			value = list.get(index).getStockAgeStockNo();
		}
		else if("stockAgePartCode".equals(fieldName)){
			value = list.get(index).getStockAgePartCode();
		}
		else if("stockAgePartDescription".equals(fieldName)){
			value = list.get(index).getStockAgePartDescription();
		}
		else if("stockAgeRemainQty".equals(fieldName)){
			value = list.get(index).getStockAgeRemainQty();
		}
		else if("stockAgeStockQty".equals(fieldName)){
			value = list.get(index).getStockAgeStockQty();
		}
		else if("stockAgeTotalAmount".equals(fieldName)){
			value = list.get(index).getStockAgeTotalAmount();
		}
		else if("stockAgePartUnitPrice".equals(fieldName)){
			value = list.get(index).getStockAgePartUnitPrice();
		}
		else if("stockAgeYears".equals(fieldName)){
			value = list.get(index).getStockAgeYears();
		}
		else if("stockAgeMonths".equals(fieldName)){
			value = list.get(index).getStockAgeMonths();
		}
		else if("stockAgeDays".equals(fieldName)){
			value = list.get(index).getStockAgeDays();
		}
		else if("stockAgeCreatedDate".equals(fieldName)){
			value = list.get(index).getStockAgeCreatedDate();
		}
		else if("stockAgeDaysAge".equals(fieldName)){
			value = list.get(index).getStockAgeDaysAge();
		}


		return value;
	}
}
