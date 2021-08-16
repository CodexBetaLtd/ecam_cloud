package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum SMTriggerTypeMeterReading {

	T1_A1(1, "Type 1 A 1", SMABCTriggerType.TYPE_1, SMTriggerTaskType.A_TASK, 10000.0, 0), T1_A2(2, "Type 1 A 1",
			SMABCTriggerType.TYPE_1, SMTriggerTaskType.A_TASK, 35000.0, 2), T1_A3(3, "Type 1 A 1",
					SMABCTriggerType.TYPE_1, SMTriggerTaskType.A_TASK, 60000.0,
					4), T1_A4(4, "Type 1 A 1", SMABCTriggerType.TYPE_1, SMTriggerTaskType.A_TASK, 85000.0, 6),

	T1_B1(5, "Type 1 B 1", SMABCTriggerType.TYPE_1, SMTriggerTaskType.B_TASK, 25000.0, 1), T1_B2(6, "Type 1 B 2",
			SMABCTriggerType.TYPE_1, SMTriggerTaskType.B_TASK, 50000.0,
			3), T1_B3(6, "Type 1 B 3", SMABCTriggerType.TYPE_1, SMTriggerTaskType.B_TASK, 75000.0, 5),

	T1_C1(7, "Type 1 C 1", SMABCTriggerType.TYPE_1, SMTriggerTaskType.C_TASK, 100000.0, 7),

	T2_A1(8, "Type 2 A 1", SMABCTriggerType.TYPE_2, SMTriggerTaskType.A_TASK, 10000.0, 0), T2_A2(9, "Type 2 A 1",
			SMABCTriggerType.TYPE_2, SMTriggerTaskType.A_TASK, 20000.0,
			1), T2_A3(10, "Type 2 A 1", SMABCTriggerType.TYPE_2, SMTriggerTaskType.A_TASK, 30000.0, 2), T2_A4(11,
					"Type 2 A 1", SMABCTriggerType.TYPE_2, SMTriggerTaskType.A_TASK, 50000.0,
					4), T2_A5(12, "Type 2 A 1", SMABCTriggerType.TYPE_2, SMTriggerTaskType.A_TASK, 60000.0, 5), T2_A6(
							13, "Type 2 A 1", SMABCTriggerType.TYPE_2, SMTriggerTaskType.A_TASK, 70000.0, 6), T2_A7(14,
									"Type 2 A 1", SMABCTriggerType.TYPE_2, SMTriggerTaskType.A_TASK, 90000.0,
									8), T2_A8(15, "Type 2 A 1", SMABCTriggerType.TYPE_2, SMTriggerTaskType.A_TASK,
											110000.0, 10), T2_A9(16, "Type 2 A 1", SMABCTriggerType.TYPE_2,
													SMTriggerTaskType.A_TASK, 130000.0, 12), T2_A10(17, "Type 2 A 1",
															SMABCTriggerType.TYPE_2, SMTriggerTaskType.A_TASK, 140000.0,
															13), T2_A12(18, "Type 2 A 1", SMABCTriggerType.TYPE_2,
																	SMTriggerTaskType.A_TASK, 150000.0, 14), T2_A13(19,
																			"Type 2 A 1", SMABCTriggerType.TYPE_2,
																			SMTriggerTaskType.A_TASK, 170000.0,
																			16), T2_A14(20, "Type 2 A 1",
																					SMABCTriggerType.TYPE_2,
																					SMTriggerTaskType.A_TASK, 180000.0,
																					17), T2_A15(21, "Type 2 A 1",
																							SMABCTriggerType.TYPE_2,
																							SMTriggerTaskType.A_TASK,
																							190000.0, 18),

	T2_B1(22, "Type 2 B 1", SMABCTriggerType.TYPE_2, SMTriggerTaskType.B_TASK, 40000.0, 3), T2_B2(23, "Type 2 B 1",
			SMABCTriggerType.TYPE_2, SMTriggerTaskType.B_TASK, 80000.0, 7), T2_B3(24, "Type 2 B 1",
					SMABCTriggerType.TYPE_2, SMTriggerTaskType.B_TASK, 120000.0,
					11), T2_B4(25, "Type 2 B 2", SMABCTriggerType.TYPE_2, SMTriggerTaskType.B_TASK, 160000.0, 15),

	T2_C1(26, "Type 2 C 1", SMABCTriggerType.TYPE_2, SMTriggerTaskType.C_TASK, 100000.0, 9), T2_C2(27, "Type 2 C 2",
			SMABCTriggerType.TYPE_2, SMTriggerTaskType.C_TASK, 200000.0, 19);

	private Integer id;
	private String name;
	private SMABCTriggerType smabcTriggerType;
	private SMTriggerTaskType smTriggerTaskType;
	private Double meterReadingValue;
	Integer index;

	private SMTriggerTypeMeterReading(Integer id, String name, SMABCTriggerType smabcTriggerType,
			SMTriggerTaskType smTriggerTaskType, Double meterReadingValue, Integer index) {
		this.id = id;
		this.name = name;
		this.smabcTriggerType = smabcTriggerType;
		this.smTriggerTaskType = smTriggerTaskType;
		this.meterReadingValue = meterReadingValue;
		this.index = index;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public static List<SMTriggerTypeMeterReading> findTaskTypeByMeterReading(SMABCTriggerType smabcTriggerType) {
		List<SMTriggerTypeMeterReading> smTriggerTypeMeterReadings = new ArrayList<SMTriggerTypeMeterReading>();
		SMTriggerTypeMeterReading[] arry = SMTriggerTypeMeterReading.values();
		for (SMTriggerTypeMeterReading element : arry) {
			if (element.getSmabcTriggerType().equals(smabcTriggerType)) {
				smTriggerTypeMeterReadings.add(element);
			}
		}
		return smTriggerTypeMeterReadings;
	}

	public static SMTriggerTypeMeterReading processMeterReadingToCurrentTrigger(Double currentMeterReading,
			SMABCTriggerType smabcTriggerType) {
		List<SMTriggerTypeMeterReading> smTriggerTypeMeterReadings = findTaskTypeByMeterReading(smabcTriggerType);
		Double val = smabcTriggerType.getcValue();
		Double preVal = 0.0;
		Boolean isCor = Boolean.TRUE;
		while (isCor) {
			if (currentMeterReading > preVal && currentMeterReading <= val) {
				isCor = Boolean.FALSE;
				for (int i = 0; i < smTriggerTypeMeterReadings.size(); i++) {
					SMTriggerTypeMeterReading smTriggerTypeMeterReading = smTriggerTypeMeterReadings.get(i);
					if (currentMeterReading == preVal + smTriggerTypeMeterReading.meterReadingValue) {
						return smTriggerTypeMeterReading;
					}
				}

			}
			preVal = val;
			val = val + smabcTriggerType.getcValue();
		}
		return null;

	}

	public static Double processMeterReadingToCurrent(Double currentMeterReading, SMABCTriggerType smabcTriggerType) {
		List<SMTriggerTypeMeterReading> smTriggerTypeMeterReadings = findTaskTypeByMeterReading(smabcTriggerType);
		Double val = smabcTriggerType.getcValue();
		Double preVal = 0.0;
		Boolean isCor = Boolean.TRUE;
		while (isCor) {
			if (currentMeterReading > preVal && currentMeterReading <= val) {
				isCor = Boolean.FALSE;
				for (int i = 0; i < smTriggerTypeMeterReadings.size(); i++) {
					SMTriggerTypeMeterReading smTriggerTypeMeterReading = smTriggerTypeMeterReadings.get(i);
					if (currentMeterReading == preVal + smTriggerTypeMeterReading.meterReadingValue) {
						return preVal + smTriggerTypeMeterReading.meterReadingValue;
					}
				}

			}
			preVal = val;
			val = val + smabcTriggerType.getcValue();
		}
		return null;

	}

	public static SMTriggerTypeMeterReading findNextTriggerByIndex(
			SMTriggerTypeMeterReading smTriggerTypeMeterReading) {
		List<SMTriggerTypeMeterReading> arry = findTaskTypeByMeterReading(
				smTriggerTypeMeterReading.getSmabcTriggerType());
		for (SMTriggerTypeMeterReading element : arry) {
			if (element.getIndex().equals(smTriggerTypeMeterReading.getIndex() + 1)) {
				return element;

			} else if (smTriggerTypeMeterReading.equals(SMTriggerTypeMeterReading.T1_C1)) {
				return T1_A1;
			} else if (smTriggerTypeMeterReading.equals(SMTriggerTypeMeterReading.T2_C2)) {
				return T2_A1;
			}
		}

		return smTriggerTypeMeterReading;
	}

	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public SMABCTriggerType getSmabcTriggerType() {
		return smabcTriggerType;
	}

	public void setSmabcTriggerType(SMABCTriggerType smabcTriggerType) {
		this.smabcTriggerType = smabcTriggerType;
	}

	public SMTriggerTaskType getSmTriggerTaskType() {
		return smTriggerTaskType;
	}

	public void setSmTriggerTaskType(SMTriggerTaskType smTriggerTaskType) {
		this.smTriggerTaskType = smTriggerTaskType;
	}

	public Double getMeterReadingValue() {
		return meterReadingValue;
	}

	public void setMeterReadingValue(Double meterReadingValue) {
		this.meterReadingValue = meterReadingValue;
	}

}
