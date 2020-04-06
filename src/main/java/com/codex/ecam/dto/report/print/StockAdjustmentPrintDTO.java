package com.codex.ecam.dto.report.print;


import java.util.List;

import com.codex.ecam.dto.report.data.AODRepDTO;
import com.codex.ecam.dto.report.data.StockAdjustmentRepDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class StockAdjustmentPrintDTO implements JRDataSource {

	private int index = -1;
	private final List<StockAdjustmentRepDTO> list;

	public StockAdjustmentPrintDTO(final List<StockAdjustmentRepDTO> aodRepDTOs) {
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

		if("adjustmentCode".equals(fieldName)) {
			value = list.get(index).getAdjustmentCode();
		}
		else if("partName".equals(fieldName)){
			value = list.get(index).getPartName();
		}
		else if("stockNo".equals(fieldName)){
			value = list.get(index).getStockNo();
		}
		else if("description".equals(fieldName)){
			value = list.get(index).getDescription();
		}
		else if("date".equals(fieldName)){
			value = list.get(index).getDate();
		}
		else if("lastQuantity".equals(fieldName)){
			value = list.get(index).getLastQuantity();
		}
		else if("newQuantity".equals(fieldName)){
			value = list.get(index).getNewQuantity();
		}
		else if("statusName".equals(fieldName)){
			value = list.get(index).getStatusName();
		}


		return value;
	}
}
