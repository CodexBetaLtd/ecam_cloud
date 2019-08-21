package com.codex.ecam.dto.maintenance.scheduledmaintenance;

import java.util.Date;

import com.codex.ecam.constants.MeterReadingLogicType;
import com.codex.ecam.constants.SMMeterReadingType;
import com.codex.ecam.constants.SMTimeScheduleOccurenceType;
import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.constants.util.Months;
import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.util.DateUtil;

public class ScheduledMaintenanceTriggerDTO extends BaseDTO {

	private Integer id;
	private Integer index;

	private Integer assetId;
	private String assetName;
	private String summary;
	private SMTriggerType triggerType;
	private Integer scheduledMaintenanceId;
	private String scheduleDescription;

	private Double everyValue;
	private Boolean scheduleIsFixed = true;
	private Date lastTriggeredDate;

	// time trigger properties
	private SMTimeScheduleOccurenceType ttOccurenceType;
	private Boolean ttWeeklyInSunday;
	private Boolean ttWeeklyInMonday;
	private Boolean ttWeeklyInTuesday;
	private Boolean ttWeeklyInWednesday;
	private Boolean ttWeeklyInThursday;
	private Boolean ttWeeklyInFriday;
	private Boolean ttWeeklyInSaturday;
	private Integer ttDayOfMonth;
	private Months ttMonth;
	private String ttMonthName;
	private Date ttEndDate;
	private Date ttStartDate;
	private Boolean ttCreateWOOnStartDate;
	private Boolean ttNoEndDate;

	private String scheduledDate;

	// meter reading trigger properties
	private Integer mrtAssetMeterReadingId;
	private SMMeterReadingType mrtType;
	private MeterReadingLogicType mrtLogicType;
	private String mrtLogicTypeName;
	private Double mrtStartMeterReading;
	private Double mrtEndMeterReading;
	private Boolean mrtNoEndReading;
	private Double mrtNextMeterReading;
	private Double mrtConditionValue;
	private Double mrtEveryValue;

	// event trigger properties
	private Integer etAssetEventTypeAssetId;
	private String etAssetEventTypeAssetName;

	private String nextTrigger;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public SMTriggerType getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(SMTriggerType triggerType) {
		this.triggerType = triggerType;
	}

	public Integer getScheduledMaintenanceId() {
		return scheduledMaintenanceId;
	}

	public void setScheduledMaintenanceId(Integer scheduledMaintenanceId) {
		this.scheduledMaintenanceId = scheduledMaintenanceId;
	}

	public String getScheduleDescription() {
		return scheduleDescription;
	}

	public void setScheduleDescription(String scheduleDescription) {
		this.scheduleDescription = scheduleDescription;
	}

	public Double getEveryValue() {
		return everyValue;
	}

	public void setEveryValue(Double everyValue) {
		this.everyValue = everyValue;
	}

	public Boolean getScheduleIsFixed() {
		return scheduleIsFixed;
	}

	public void setScheduleIsFixed(Boolean scheduleIsFixed) {
		this.scheduleIsFixed = scheduleIsFixed;
	}

	public Date getLastTriggeredDate() {
		return lastTriggeredDate;
	}

	public void setLastTriggeredDate(Date lastTriggeredDate) {
		this.lastTriggeredDate = lastTriggeredDate;
	}

	public SMTimeScheduleOccurenceType getTtOccurenceType() {
		return ttOccurenceType;
	}

	public void setTtOccurenceType(SMTimeScheduleOccurenceType ttOccurenceType) {
		this.ttOccurenceType = ttOccurenceType;
	}

	public Boolean getTtWeeklyInSunday() {
		return ttWeeklyInSunday;
	}

	public void setTtWeeklyInSunday(Boolean ttWeeklyInSunday) {
		this.ttWeeklyInSunday = ttWeeklyInSunday;
	}

	public Boolean getTtWeeklyInMonday() {
		return ttWeeklyInMonday;
	}

	public void setTtWeeklyInMonday(Boolean ttWeeklyInMonday) {
		this.ttWeeklyInMonday = ttWeeklyInMonday;
	}

	public Boolean getTtWeeklyInTuesday() {
		return ttWeeklyInTuesday;
	}

	public void setTtWeeklyInTuesday(Boolean ttWeeklyInTuesday) {
		this.ttWeeklyInTuesday = ttWeeklyInTuesday;
	}

	public Boolean getTtWeeklyInWednesday() {
		return ttWeeklyInWednesday;
	}

	public void setTtWeeklyInWednesday(Boolean ttWeeklyInWednesday) {
		this.ttWeeklyInWednesday = ttWeeklyInWednesday;
	}

	public Boolean getTtWeeklyInThursday() {
		return ttWeeklyInThursday;
	}

	public void setTtWeeklyInThursday(Boolean ttWeeklyInThursday) {
		this.ttWeeklyInThursday = ttWeeklyInThursday;
	}

	public Boolean getTtWeeklyInFriday() {
		return ttWeeklyInFriday;
	}

	public void setTtWeeklyInFriday(Boolean ttWeeklyInFriday) {
		this.ttWeeklyInFriday = ttWeeklyInFriday;
	}

	public Boolean getTtWeeklyInSaturday() {
		return ttWeeklyInSaturday;
	}

	public void setTtWeeklyInSaturday(Boolean ttWeeklyInSaturday) {
		this.ttWeeklyInSaturday = ttWeeklyInSaturday;
	}

	public Integer getTtDayOfMonth() {
		return ttDayOfMonth;
	}

	public void setTtDayOfMonth(Integer ttDayOfMonth) {
		this.ttDayOfMonth = ttDayOfMonth;
	}

	public Months getTtMonth() {
		return ttMonth;
	}

	public void setTtMonth(Months ttMonth) {
		this.ttMonth = ttMonth;
	}

	public String getTtEndDate() {
		return DateUtil.getSimpleDateString(ttEndDate);
	}

	public void setTtEndDate(Date ttEndDate) {
		this.ttEndDate = ttEndDate;
	}

	public String getTtStartDate() {
		return DateUtil.getSimpleDateString(ttStartDate);
	}

	public void setTtStartDate(Date ttStartDate) {
		this.ttStartDate = ttStartDate;
	}

	public Boolean getTtCreateWOOnStartDate() {
		return ttCreateWOOnStartDate;
	}

	public void setTtCreateWOOnStartDate(Boolean ttCreateWOOnStartDate) {
		this.ttCreateWOOnStartDate = ttCreateWOOnStartDate;
	}

	public Boolean getTtNoEndDate() {
		return ttNoEndDate;
	}

	public void setTtNoEndDate(Boolean ttNoEndDate) {
		this.ttNoEndDate = ttNoEndDate;
	}

	public Integer getMrtAssetMeterReadingId() {
		return mrtAssetMeterReadingId;
	}

	public void setMrtAssetMeterReadingId(Integer mrtAssetMeterReadingId) {
		this.mrtAssetMeterReadingId = mrtAssetMeterReadingId;
	}

	public MeterReadingLogicType getMrtLogicType() {
		return mrtLogicType;
	}

	public void setMrtLogicType(MeterReadingLogicType mrtLogicType) {
		this.mrtLogicType = mrtLogicType;
	}

	public Double getMrtStartMeterReading() {
		return mrtStartMeterReading;
	}

	public void setMrtStartMeterReading(Double mrtStartMeterReading) {
		this.mrtStartMeterReading = mrtStartMeterReading;
	}

	public Double getMrtEndMeterReading() {
		return mrtEndMeterReading;
	}

	public void setMrtEndMeterReading(Double mrtEndMeterReading) {
		this.mrtEndMeterReading = mrtEndMeterReading;
	}

	public Double getMrtNextMeterReading() {
		return mrtNextMeterReading;
	}

	public void setMrtNextMeterReading(Double mrtNextMeterReading) {
		this.mrtNextMeterReading = mrtNextMeterReading;
	}

	public Integer getEtAssetEventTypeAssetId() {
		return etAssetEventTypeAssetId;
	}

	public void setEtAssetEventTypeAssetId(Integer etAssetEventTypeAssetId) {
		this.etAssetEventTypeAssetId = etAssetEventTypeAssetId;
	}

	public Boolean getMrtNoEndReading() {
		return mrtNoEndReading;
	}

	public void setMrtNoEndReading(Boolean mrtNoEndReading) {
		this.mrtNoEndReading = mrtNoEndReading;
	}

	public SMMeterReadingType getMrtType() {
		return mrtType;
	}

	public void setMrtType(SMMeterReadingType mrtType) {
		this.mrtType = mrtType;
	}

	public Double getMrtConditionValue() {
		return mrtConditionValue;
	}

	public void setMrtConditionValue(Double mrtConditionValue) {
		this.mrtConditionValue = mrtConditionValue;
	}

	public Double getMrtEveryValue() {
		return mrtEveryValue;
	}

	public void setMrtEveryValue(Double mrtEveryValue) {
		this.mrtEveryValue = mrtEveryValue;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTtMonthName() {
		return ttMonthName;
	}

	public void setTtMonthName(String ttMonthName) {
		this.ttMonthName = ttMonthName;
	}

	public String getMrtLogicTypeName() {
		return mrtLogicTypeName;
	}

	public void setMrtLogicTypeName(String mrtLogicTypeName) {
		this.mrtLogicTypeName = mrtLogicTypeName;
	}

	public String getEtAssetEventTypeAssetName() {
		return etAssetEventTypeAssetName;
	}

	public void setEtAssetEventTypeAssetName(String etAssetEventTypeAssetName) {
		this.etAssetEventTypeAssetName = etAssetEventTypeAssetName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getNextTrigger() {
		return nextTrigger;
	}

	public void setNextTrigger(String nextTrigger) {
		this.nextTrigger = nextTrigger;
	}
}
