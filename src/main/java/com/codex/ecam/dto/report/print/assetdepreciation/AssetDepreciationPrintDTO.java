package com.codex.ecam.dto.report.print.assetdepreciation;


import java.text.SimpleDateFormat;
import java.util.List;

import com.codex.ecam.dto.report.data.assetdepreciation.AssetDepreciationRepDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class AssetDepreciationPrintDTO implements JRDataSource {

	private int index = -1;
	private final List<AssetDepreciationRepDTO> list;

	private SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM yyyy");

	public AssetDepreciationPrintDTO(final List<AssetDepreciationRepDTO> aodRepDTOs) {
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

		if("code".equals(fieldName)) {
			value = list.get(index).getCode();
		}
		else if("name".equals(fieldName)){
			value = list.get(index).getName();
		}
		else if("assetCategoryName".equals(fieldName)){
			value = list.get(index).getAssetCategoryName();
		}
		else if("mainLocationName".equals(fieldName)){
			value = list.get(index).getMainLocationName();
		}
		else if("subLocation".equals(fieldName)){
			value = list.get(index).getSubLocation();
		}
		else if("subLocation2".equals(fieldName)){
			value = list.get(index).getSubLocation2();
		}
		else if("department".equals(fieldName)){
			value = list.get(index).getDepartment();
		}
		else if("description".equals(fieldName)){
			value = list.get(index).getDescription();
		}
		else if("brandName".equals(fieldName)){
			value = list.get(index).getBrandName();
		}
		else if("modelName".equals(fieldName)){
			value = list.get(index).getModelName();
		}
		else if("size".equals(fieldName)){
			value = list.get(index).getSize();
		}
		else if("serialNo".equals(fieldName)){
			value = list.get(index).getSerialNo();
		}
		else if("remarks".equals(fieldName)){
			value = list.get(index).getRemarks();
		}
		else if("dateOfPurchase".equals(fieldName)){
			value = list.get(index).getDateOfPurchase();
		}
		else if("quantity".equals(fieldName)){
			value = list.get(index).getQuantity();
		}
		else if("unitCost".equals(fieldName)){
			value = list.get(index).getUnitCost();
		}
		else if("totalCost".equals(fieldName)){
			value = list.get(index).getTotalCost();
		}
		else if("usefulLife".equals(fieldName)){
			value = list.get(index).getUsefulLife();
		}
		else if("depreciationForTheYearValue".equals(fieldName)){
			value = list.get(index).getDepreciationForTheYearValue();
		}
		else if("yearEndNetBookValue".equals(fieldName)){
			value = list.get(index).getYearEndNetBookValue();
		}
		else if("accumulatedDepreciation".equals(fieldName)){
			value = list.get(index).getAccumulatedDepreciation();
		}
		else if("fromDate".equals(fieldName)){
			value = list.get(index).getFromDate();
		}
		else if("toDate".equals(fieldName)){
			value = list.get(index).getToDate();
		}
		else if("DEPRECIATION_FOR_THE_YEAR_COLUMN".equals(fieldName)){
			value =  "Depreciation for the year - "
					.concat(YEAR_FORMAT.format(list.get(index).getFromDate()))
					.concat(" -(").concat(DATE_FORMAT.format(list.get(index).getFromDate()))
					.concat( " to ").concat(DATE_FORMAT.format(list.get(index).getToDate()))
					.concat(")");
		}
		else if("ACCUMULATED_DEPRECIATION_COLUMN".equals(fieldName)){
			value =  "Accumulated Depreciation As of - ".concat(DATE_FORMAT.format(list.get(index).getToDate()));
		}
		else if("REPORT_PERIOD".equals(fieldName)){
			value =  DATE_FORMAT.format(list.get(index).getFromDate())
					.concat(" TO ")
					.concat(DATE_FORMAT.format(list.get(index).getToDate()));
		}
		return value;
	}
}
