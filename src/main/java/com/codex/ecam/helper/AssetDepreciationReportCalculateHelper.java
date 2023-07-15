package com.codex.ecam.helper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.LongUnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codex.ecam.dto.report.data.assetdepreciation.AssetDepreciationRepDTO;

public class AssetDepreciationReportCalculateHelper {

	private AssetDepreciationReportCalculateHelper(){}

	private static AssetDepreciationReportCalculateHelper instance = null;

	public static  AssetDepreciationReportCalculateHelper getInstance() {
		if (instance == null) {
			instance = new AssetDepreciationReportCalculateHelper();
		}
		return instance;
	}

	private static final Logger logger = LoggerFactory.getLogger(AssetDepreciationReportCalculateHelper.class);

	public void calculate(Date fromDate, Date toDate, AssetDepreciationRepDTO dto) {

		try {
			if (dto.getUsefulLife() != null && dto.getUnitCost() != null && dto.getQuantity() != null) {

				double usefullLifeInDays = (dto.getUsefulLife().doubleValue()) * 365;
				double totalCost = dto.getUnitCost().doubleValue() * dto.getQuantity().doubleValue();

				dto.setTotalCost(BigDecimal.valueOf(totalCost));

				double dayCost = totalCost /  usefullLifeInDays;

				calculateDepreciationForTheYear(dto, dayCost, fromDate, toDate);
				calculateAccumulatedDepreciation(dto, dayCost, toDate);
				calculateYearEndNetValue( dto);

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured when depreciation calculate for machine {} due to, {}", dto.getCode(), e.getMessage());
		}
	}

	private void calculateDepreciationForTheYear(AssetDepreciationRepDTO dto, double dayCost, Date fromDate, Date toDate) {

		dto.setDepreciationForTheYearValue(BigDecimal.valueOf(dayCost * getDifferenceInDays(toDate, fromDate)));

	}

	private void calculateAccumulatedDepreciation(AssetDepreciationRepDTO dto, double dayCost, Date toDate) {

		dto.setAccumulatedDepreciation(BigDecimal.valueOf(dayCost * getDifferenceInDays(toDate, dto.getDateOfPurchase())));

	}

	private void calculateYearEndNetValue(AssetDepreciationRepDTO dto) {

		dto.setYearEndNetBookValue(dto.getTotalCost().subtract(dto.getAccumulatedDepreciation()));
	}

	private long getDifferenceInDays(Date maxDate, Date minDate) {

		LongUnaryOperator diffInDays = longTime -> TimeUnit.DAYS.convert(longTime, TimeUnit.MILLISECONDS) + 1;

		return diffInDays.applyAsLong(maxDate.getTime() - minDate.getTime());
	}

}
