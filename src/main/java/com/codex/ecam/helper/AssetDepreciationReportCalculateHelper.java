package com.codex.ecam.helper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codex.ecam.dto.report.data.assetdepreciation.AssetDepreciationRepDTO;

public class AssetDepreciationReportCalculateHelper {

	private static final Logger logger = LoggerFactory.getLogger(AssetDepreciationReportCalculateHelper.class);

	public static void calculate(Date fromDate, Date toDate, AssetDepreciationRepDTO dto) {

		try {
			if (dto.getUsefulLife() != null && dto.getUnitCost() != null && dto.getQuantity() != null) {

				double usefullLifeInDays = (dto.getUsefulLife().doubleValue() / 12) * 365;
				double dayCost = ( dto.getUnitCost().doubleValue() * dto.getQuantity().doubleValue() ) /  usefullLifeInDays;

				calculateDepreciationForTheYear(dto, dayCost, fromDate, toDate);
				calculateAccumulatedDepreciation(dto, dayCost, toDate);
				calculateYearEndNetValue(dto);

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured when depreciation calculate for machine {} due to, {}", dto.getCode(), e.getMessage());
		}
	}

	private static void calculateYearEndNetValue(AssetDepreciationRepDTO dto) {
		dto.setYearEndNetBookValue( dto.getTotalCost().subtract( dto.getAccumulatedDepreciation()));
	}

	private static void calculateAccumulatedDepreciation(AssetDepreciationRepDTO dto, double dayCost, Date toDate) {

		long diff = TimeUnit.DAYS.convert(toDate.getTime() - dto.getDateOfPurchase().getTime(), TimeUnit.MILLISECONDS) + 1;
		dto.setAccumulatedDepreciation(BigDecimal.valueOf(dayCost * diff));

	}

	private static void calculateDepreciationForTheYear(AssetDepreciationRepDTO dto, double dayCost, Date fromDate, Date toDate) {


		long diff = TimeUnit.DAYS.convert(toDate.getTime() - fromDate.getTime(), TimeUnit.MILLISECONDS) + 1;

		dto.setDepreciationForTheYearValue(BigDecimal.valueOf(dayCost * diff));

	}

}
