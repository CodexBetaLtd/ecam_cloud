package com.codex.ecam.dto.report.data.assetdepreciation;


import java.math.BigDecimal;
import java.util.Date;

import com.codex.ecam.dto.BaseReportDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetDepreciationRepDTO extends BaseReportDTO {

	private String code;
	private String name;
	private String assetCategoryName;
	private String mainLocationName;
	private String subLocation;
	private String subLocation2;
	private String department;
	private String description;
	private String brandName;
	private String modelName;
	private String size;
	private String serialNo;
	private String remarks;

	private Date dateOfPurchase;
	private Date fromDate;
	private Date toDate;

	private BigDecimal quantity;
	private BigDecimal unitCost;
	private BigDecimal totalCost;
	private BigDecimal usefulLife;
	private BigDecimal depreciationForTheYearValue;
	private BigDecimal yearEndNetBookValue;
	private BigDecimal accumulatedDepreciation;

}
