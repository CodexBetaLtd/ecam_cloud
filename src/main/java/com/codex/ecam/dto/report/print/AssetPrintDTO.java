package com.codex.ecam.dto.report.print;


import java.util.List;

import com.codex.ecam.dto.report.data.asset.AssetRepDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class AssetPrintDTO implements JRDataSource {

	private int index = -1;
	private final List<AssetRepDTO> list;

	public AssetPrintDTO(final List<AssetRepDTO> assetRepDTOs) {
		this.list = assetRepDTOs;
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

		if("name".equals(fieldName)) {
			value = list.get(index).getName();
		}
		else if("code".equals(fieldName)){
			value = list.get(index).getCode();
		}
		else if("description".equals(fieldName)){
			value = list.get(index).getDescription();
		}
		else if("modelName".equals(fieldName)){
			value = list.get(index).getModelName();
		}
		else if("assetCategoryName".equals(fieldName)){
			value = list.get(index).getAssetCategoryName();
		}
		else if("parentAssetName".equals(fieldName)){
			value = list.get(index).getParentAssetName();
		}
		else if("businessAddress".equals(fieldName)){
			value = list.get(index).getAddress();
		}
		else if("businessName".equals(fieldName)){
			value = list.get(index).getBusinessName();
		}
		else if("assetPrice".equals(fieldName)){
			value = list.get(index).getAssetPrice();
		}
		else if("assetCount".equals(fieldName)){
			value = list.get(index).getAssetCount();
		}


		return value;
	}
}
