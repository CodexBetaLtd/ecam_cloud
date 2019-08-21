package com.codex.ecam.mappers.maintenance.schedulemaintenance;

import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTriggerDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;
import com.codex.ecam.util.DateUtil;

public class ScheduledMaintenanceTriggerMapper extends GenericMapper<ScheduledMaintenanceTrigger, ScheduledMaintenanceTriggerDTO> {

	private static ScheduledMaintenanceTriggerMapper instance = null;

	private ScheduledMaintenanceTriggerMapper() {
	}

	public static ScheduledMaintenanceTriggerMapper getInstance() {
		if (instance == null) {
			instance = new ScheduledMaintenanceTriggerMapper();
		}
		return instance;
	}

	@Override
	public ScheduledMaintenanceTriggerDTO domainToDto(ScheduledMaintenanceTrigger domain) throws Exception {
		ScheduledMaintenanceTriggerDTO dto = new ScheduledMaintenanceTriggerDTO();
		dto.setId(domain.getId());
		dto.setTtCreateWOOnStartDate(domain.getTtCreateWOOnStartDate());
		dto.setScheduleIsFixed(domain.getScheduleIsFixed());
		dto.setTriggerType(domain.getTriggerType());
		dto.setScheduleDescription(domain.getScheduleDescription());
		dto.setTtOccurenceType(domain.getTtOccurenceType());
		dto.setScheduleDescription(domain.getScheduleDescription());
		dto.setEveryValue(domain.getConditionValue());
		dto.setTtDayOfMonth(domain.getTtDayOfMonth());
		dto.setTtEndDate(domain.getTtEndDate());
		dto.setTtStartDate(domain.getTtStartDate());
		dto.setLastTriggeredDate(domain.getLastTriggeredDate());
		dto.setMrtNextMeterReading(domain.getMrtNextMeterReading());
		dto.setMrtStartMeterReading(domain.getMrtStartMeterReading());
		dto.setMrtEndMeterReading(domain.getMrtEndMeterReading());
		dto.setMrtType(domain.getMrtType());
		dto.setTtWeeklyInSunday(domain.getTtInSunday());
		dto.setTtWeeklyInMonday(domain.getTtInMonday());
		dto.setTtWeeklyInTuesday(domain.getTtInTuesday());
		dto.setTtWeeklyInWednesday(domain.getTtInWednesday());
		dto.setTtWeeklyInThursday(domain.getTtInThursday());
		dto.setTtWeeklyInFriday(domain.getTtInFriday());
		dto.setTtWeeklyInSaturday(domain.getTtInSaturday());

		if (domain.getScheduledMaintenance() != null) {
			dto.setScheduledMaintenanceId(domain.getScheduledMaintenance().getId());
		}

		if (domain.getEtAssetEventTypeAsset() != null) {
			dto.setEtAssetEventTypeAssetId(domain.getEtAssetEventTypeAsset().getId());
			dto.setEtAssetEventTypeAssetName(domain.getEtAssetEventTypeAsset().getAssetEventType().getName());
		}

		if (domain.getAsset() != null) {
			dto.setAssetId(domain.getAsset().getId());
			dto.setAssetName(domain.getAsset().getName());
		}

		if (domain.getMrtAssetMeterReading() != null) {
			dto.setMrtAssetMeterReadingId(domain.getMrtAssetMeterReading().getId());
		}

		if (domain.getTtMonth() != null) {
			dto.setTtMonth(domain.getTtMonth());
			dto.setTtMonthName(domain.getTtMonth().getName());
		}

		if (domain.getMrtLogicType() != null) {
			dto.setMrtLogicType(domain.getMrtLogicType());
			dto.setMrtLogicTypeName(domain.getMrtLogicType().getName());
		}

		if ((domain.getTriggerType() != null) && domain.getTriggerType().equals(SMTriggerType.TIME_TRIGGER)) {
			dto.setTtNoEndDate(domain.getNoEndValue());
		} else if ((domain.getTriggerType() != null) && domain.getTriggerType().equals(SMTriggerType.METER_READING_TRIGGER)) {
			dto.setMrtNoEndReading(domain.getNoEndValue());
		}

		setNextTriggerDetail(dto, domain);

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setNextTriggerDetail(ScheduledMaintenanceTriggerDTO dto, ScheduledMaintenanceTrigger domain) {
		switch (domain.getTriggerType()) {

		case TIME_TRIGGER:
			if (domain.getTtNextCalenderEvent() != null) {
				dto.setNextTrigger(DateUtil.getCommonDateString(domain.getTtNextCalenderEvent().getScheduledDate()));
			}
			break;

		case METER_READING_TRIGGER:
			dto.setNextTrigger("" + domain.getMrtNextMeterReading() + " " + domain.getMrtAssetMeterReading().getMeterReadingUnit());
			break;

		default:
			break;
		}
	}

	@Override
	public void dtoToDomain(ScheduledMaintenanceTriggerDTO dto, ScheduledMaintenanceTrigger domain) throws Exception {
		domain.setId(dto.getId());
		domain.setTriggerType(dto.getTriggerType());
		domain.setScheduleDescription(dto.getScheduleDescription());
		domain.setTtCreateWOOnStartDate(dto.getTtCreateWOOnStartDate());
		domain.setScheduleIsFixed(dto.getScheduleIsFixed());
		domain.setTtOccurenceType(dto.getTtOccurenceType());
		domain.setTtMonth(dto.getTtMonth());
		domain.setConditionValue(dto.getEveryValue());
		domain.setTtDayOfMonth(dto.getTtDayOfMonth());
		domain.setTtEndDate(DateUtil.getDateObj(dto.getTtEndDate()));
		domain.setTtStartDate(DateUtil.getDateObj(dto.getTtStartDate()));
		domain.setLastTriggeredDate(dto.getLastTriggeredDate());
		domain.setMrtLogicType(dto.getMrtLogicType());
		domain.setMrtNextMeterReading(dto.getMrtNextMeterReading());
		domain.setMrtStartMeterReading(dto.getMrtStartMeterReading());
		domain.setMrtEndMeterReading(dto.getMrtEndMeterReading());
		domain.setMrtType(dto.getMrtType());
		domain.setTtInSunday(dto.getTtWeeklyInSunday());
		domain.setTtInMonday(dto.getTtWeeklyInMonday());
		domain.setTtInTuesday(dto.getTtWeeklyInTuesday());
		domain.setTtInWednesday(dto.getTtWeeklyInWednesday());
		domain.setTtInThursday(dto.getTtWeeklyInThursday());
		domain.setTtInFriday(dto.getTtWeeklyInFriday());
		domain.setTtInSaturday(dto.getTtWeeklyInSaturday());
		domain.setTriggerIndex(dto.getIndex());

		if (dto.getTtNoEndDate() != null) {
			domain.setNoEndValue(dto.getTtNoEndDate());
		} else if (dto.getMrtNoEndReading() != null) {
			domain.setNoEndValue(dto.getMrtNoEndReading());
		}

		setCommanDomainFields(dto, domain);
	}

	@Override
	public ScheduledMaintenanceTriggerDTO domainToDtoForDataTable(ScheduledMaintenanceTrigger domain) throws Exception {
		ScheduledMaintenanceTriggerDTO dto = new ScheduledMaintenanceTriggerDTO();
		dto.setId(domain.getId());
		dto.setTriggerType(domain.getTriggerType());
		dto.setScheduleDescription(domain.getScheduleDescription());
		dto.setLastTriggeredDate(domain.getLastTriggeredDate());

		if (domain.getScheduledMaintenance() != null) {
			dto.setScheduledMaintenanceId(domain.getScheduledMaintenance().getId());
		}

		if (domain.getAsset() != null) {
			dto.setAssetId(domain.getAsset().getId());
			dto.setAssetName(domain.getAsset().getName());
		}
		if (domain.getTtNextCalenderEvent() != null) {
			dto.setScheduledDate(DateUtil.getCommonDateString(domain.getTtNextCalenderEvent().getScheduledDate()));
		}

		return dto;
	}
}
