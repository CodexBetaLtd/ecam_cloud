package com.codex.ecam.model.maintenance.scheduledmaintenance;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.codex.ecam.constants.MeterReadingLogicType;
import com.codex.ecam.constants.SMABCTriggerType;
import com.codex.ecam.constants.SMMeterReadingType;
import com.codex.ecam.constants.SMTimeScheduleOccurenceType;
import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.constants.util.Months;
import com.codex.ecam.listeners.scheduledmaintenance.ScheduledMaintenanceTriggerLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetEventTypeAsset;
import com.codex.ecam.model.asset.AssetMeterReading;
import com.codex.ecam.model.maintenance.CalendarEvent;

@Entity
@Table(name = "tbl_scheduled_maintenance_trigger")
@EntityListeners( ScheduledMaintenanceTriggerLogListener.class )
public class ScheduledMaintenanceTrigger extends BaseModel {

	private static final long serialVersionUID = 5979208581291994657L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "scheduled_trigger_s")
	@SequenceGenerator(name = "scheduled_trigger_s", sequenceName = "scheduled_trigger_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "scheduled_maintenance_id")
	@ManyToOne(targetEntity = ScheduledMaintenance.class,cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
	private ScheduledMaintenance scheduledMaintenance;

	@JoinColumn(name = "et_asset_event_type_asset_id")
	@ManyToOne(targetEntity = AssetEventTypeAsset.class, fetch = FetchType.LAZY)
	private AssetEventTypeAsset etAssetEventTypeAsset;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;
	
	@JoinColumn(name = "parent_trigger_id")
	@ManyToOne(targetEntity = ScheduledMaintenanceTrigger.class, fetch = FetchType.LAZY)
	private ScheduledMaintenanceTrigger parentTigger;

	@JoinColumn(name = "mrt_asset_meter_reading_id")
	@ManyToOne(targetEntity = AssetMeterReading.class, fetch = FetchType.LAZY)
	private AssetMeterReading mrtAssetMeterReading;

	@JoinColumn(name = "tt_next_calendar_event_id")
	@ManyToOne(targetEntity = CalendarEvent.class, fetch = FetchType.LAZY)
	private CalendarEvent ttNextCalenderEvent;

	@Column(name = "trigger_type_id")
	private SMTriggerType triggerType;

	@Column(name = "schedule_description")
	private String scheduleDescription;

	@Column(name = "tt_create_wo_on_start_date")
	private Boolean ttCreateWOOnStartDate;

	@Column(name = "tt_occurence_type_id")
	private SMTimeScheduleOccurenceType ttOccurenceType;

	@Column(name = "schedule_is_fixed")
	private Boolean scheduleIsFixed;

	@Column(name = "no_end_value")
	private Boolean noEndValue;

	@Column(name = "tt_in_sunday")
	private Boolean ttInSunday;

	@Column(name = "tt_in_monday")
	private Boolean ttInMonday;

	@Column(name = "tt_in_tuesday")
	private Boolean ttInTuesday;

	@Column(name = "tt_in_wednesday")
	private Boolean ttInWednesday;

	@Column(name = "tt_in_thursday")
	private Boolean ttInThursday;

	@Column(name = "tt_in_friday")
	private Boolean ttInFriday;

	@Column(name = "tt_in_saturday")
	private Boolean ttInSaturday;

	@Column(name = "tt_month_id")
	private Months ttMonth;

	@Column(name = "tt_day_of_month")
	private Integer ttDayOfMonth;

	@Column(name = "condition_value")
	private Double conditionValue;

	@Column(name = "tt_end_date")
	@Temporal(value = TemporalType.DATE)
	private Date ttEndDate;

	@Column(name = "tt_start_date")
	@Temporal(value = TemporalType.DATE)
	private Date ttStartDate;

	@Column(name = "mrt_type")
	private SMMeterReadingType mrtType;

	@Column(name = "mrt_next_meter_reading")
	private Double mrtNextMeterReading;
	
	@Column(name = "a_mrt_next_meter_reading")
	private Double amrtNextMeterReading;
	
	@Column(name = "b_mrt_next_meter_reading")
	private Double bmrtNextMeterReading;
	
	@Column(name = "c_mrt_next_meter_reading")
	private Double cmrtNextMeterReading;

	@Column(name = "mrt_start_meter_reading")
	private Double mrtStartMeterReading;

	@Column(name = "mrt_end_meter_reading")
	private Double mrtEndMeterReading;

	@Column(name = "mrt_logic_type_id")
	private MeterReadingLogicType mrtLogicType;
	
	@Column(name = "mrt_abc_type_id")
	private SMABCTriggerType smabcTriggerType;

	@Column(name = "last_triggered_date")
	private Date lastTriggeredDate;

	@OneToMany(mappedBy = "scheduledMaintenanceTrigger", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private List<CalendarEvent> calendarEvents;

	@OneToMany(mappedBy = "scheduledMaintenanceTrigger", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<ScheduledMaintenanceTask> scheduledMaintenanceTasks;

	@Transient
	private Integer triggerIndex;

	public Integer getTriggerIndex() {
		return triggerIndex;
	}

	public void setTriggerIndex(Integer triggerIndex) {
		this.triggerIndex = triggerIndex;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public ScheduledMaintenance getScheduledMaintenance() {
		return scheduledMaintenance;
	}

	public void setScheduledMaintenance(ScheduledMaintenance scheduledMaintenance) {
		this.scheduledMaintenance = scheduledMaintenance;
	}

	public AssetEventTypeAsset getEtAssetEventTypeAsset() {
		return etAssetEventTypeAsset;
	}

	public void setEtAssetEventTypeAsset(AssetEventTypeAsset etAssetEventTypeAsset) {
		this.etAssetEventTypeAsset = etAssetEventTypeAsset;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		firePropertyChange("asset", this.asset, asset);
		this.asset = asset;
	}

	public AssetMeterReading getMrtAssetMeterReading() {
		return mrtAssetMeterReading;
	}

	public void setMrtAssetMeterReading(AssetMeterReading mrtAssetMeterReading) {
		this.mrtAssetMeterReading = mrtAssetMeterReading;
	}

	public CalendarEvent getTtNextCalenderEvent() {
		return ttNextCalenderEvent;
	}

	public void setTtNextCalenderEvent(CalendarEvent ttNextCalenderEvent) {
		this.ttNextCalenderEvent = ttNextCalenderEvent;
	}

	public SMTriggerType getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(SMTriggerType triggerType) {
		firePropertyChange("triggerType", this.triggerType, triggerType);
		this.triggerType = triggerType;
	}

	public String getScheduleDescription() {
		return scheduleDescription;
	}

	public void setScheduleDescription(String scheduleDescription) {
		this.scheduleDescription = scheduleDescription;
	}

	public Boolean getTtCreateWOOnStartDate() {
		return ttCreateWOOnStartDate;
	}

	public void setTtCreateWOOnStartDate(Boolean ttCreateWOOnStartDate) {
		firePropertyChange("ttCreateWOOnStartDate", this.ttCreateWOOnStartDate, ttCreateWOOnStartDate);
		this.ttCreateWOOnStartDate = ttCreateWOOnStartDate;
	}

	public SMTimeScheduleOccurenceType getTtOccurenceType() {
		return ttOccurenceType;
	}

	public void setTtOccurenceType(SMTimeScheduleOccurenceType ttOccurenceType) {
		firePropertyChange("ttOccurenceType", this.ttOccurenceType, ttOccurenceType);
		this.ttOccurenceType = ttOccurenceType;
	}

	public Boolean getScheduleIsFixed() {
		return scheduleIsFixed;
	}

	public void setScheduleIsFixed(Boolean scheduleIsFixed) {
		this.scheduleIsFixed = scheduleIsFixed;
	}

	public Boolean getNoEndValue() {
		return noEndValue;
	}

	public void setNoEndValue(Boolean noEndValue) {
		firePropertyChange("noEndValue", this.noEndValue, noEndValue);
		this.noEndValue = noEndValue;
	}

	public Boolean getTtInSunday() {
		return ttInSunday;
	}

	public void setTtInSunday(Boolean ttInSunday) {
		firePropertyChange("ttInSunday", this.ttInSunday, ttInSunday);
		this.ttInSunday = ttInSunday;
	}

	public Boolean getTtInMonday() {
		return ttInMonday;
	}

	public void setTtInMonday(Boolean ttInMonday) {
		firePropertyChange("ttInMonday", this.ttInMonday, ttInMonday);
		this.ttInMonday = ttInMonday;
	}

	public Boolean getTtInTuesday() {
		return ttInTuesday;
	}

	public void setTtInTuesday(Boolean ttInTuesday) {
		firePropertyChange("ttInTuesday", this.ttInTuesday, ttInTuesday);
		this.ttInTuesday = ttInTuesday;
	}

	public Boolean getTtInWednesday() {
		return ttInWednesday;
	}

	public void setTtInWednesday(Boolean ttInWednesday) {
		firePropertyChange("ttInWednesday", this.ttInWednesday, ttInWednesday);
		this.ttInWednesday = ttInWednesday;
	}

	public Boolean getTtInThursday() {
		return ttInThursday;
	}

	public void setTtInThursday(Boolean ttInThursday) {
		firePropertyChange("ttInThursday", this.ttInThursday, ttInThursday);
		this.ttInThursday = ttInThursday;
	}

	public Boolean getTtInFriday() {
		return ttInFriday;
	}

	public void setTtInFriday(Boolean ttInFriday) {
		firePropertyChange("ttInFriday", this.ttInFriday, ttInFriday);
		this.ttInFriday = ttInFriday;
	}

	public Boolean getTtInSaturday() {
		return ttInSaturday;
	}

	public void setTtInSaturday(Boolean ttInSaturday) {
		firePropertyChange("ttInSaturday", this.ttInSaturday, ttInSaturday);
		this.ttInSaturday = ttInSaturday;
	}

	public Months getTtMonth() {
		return ttMonth;
	}

	public void setTtMonth(Months ttMonth) {
		firePropertyChange("ttMonth", this.ttMonth, ttMonth);
		this.ttMonth = ttMonth;
	}

	public Integer getTtDayOfMonth() {
		return ttDayOfMonth;
	}

	public void setTtDayOfMonth(Integer ttDayOfMonth) {
		firePropertyChange("ttDayOfMonth", this.ttDayOfMonth, ttDayOfMonth);
		this.ttDayOfMonth = ttDayOfMonth;
	}

	public Double getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(Double conditionValue) {
		firePropertyChange("conditionValue", this.conditionValue, conditionValue);
		this.conditionValue = conditionValue;
	}

	public Date getTtEndDate() {
		return ttEndDate;
	}

	public void setTtEndDate(Date ttEndDate) {
		firePropertyChange("ttEndDate", this.ttEndDate, ttEndDate);
		this.ttEndDate = ttEndDate;
	}

	public Date getTtStartDate() {
		return ttStartDate;
	}

	public void setTtStartDate(Date ttStartDate) {
		firePropertyChange("ttStartDate", this.ttStartDate, ttStartDate);
		this.ttStartDate = ttStartDate;
	}

	public Double getMrtNextMeterReading() {
		return mrtNextMeterReading;
	}

	public void setMrtNextMeterReading(Double mrtNextMeterReading) {
		this.mrtNextMeterReading = mrtNextMeterReading;
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

	public MeterReadingLogicType getMrtLogicType() {
		return mrtLogicType;
	}

	public void setMrtLogicType(MeterReadingLogicType mrtLogicType) {
		this.mrtLogicType = mrtLogicType;
	}

	public Date getLastTriggeredDate() {
		return lastTriggeredDate;
	}

	public void setLastTriggeredDate(Date lastTriggeredDate) {
		this.lastTriggeredDate = lastTriggeredDate;
	}

	public List<CalendarEvent> getCalendarEvents() {
		return calendarEvents;
	}

	public void setCalendarEvents(List<CalendarEvent> calendarEvents) {
		updateCollection("calendarEvents", calendarEvents);
	}

	public SMMeterReadingType getMrtType() {
		return mrtType;
	}

	public void setMrtType(SMMeterReadingType mrtType) {
		this.mrtType = mrtType;
	}

	public Set<ScheduledMaintenanceTask> getScheduledMaintenanceTasks() {
		return scheduledMaintenanceTasks;
	}

	public SMABCTriggerType getSmabcTriggerType() {
		return smabcTriggerType;
	}

	public void setSmabcTriggerType(SMABCTriggerType smabcTriggerType) {
		this.smabcTriggerType = smabcTriggerType;
	}
	
	

	public ScheduledMaintenanceTrigger getParentTigger() {
		return parentTigger;
	}

	public void setParentTigger(ScheduledMaintenanceTrigger parentTigger) {
		this.parentTigger = parentTigger;
	}

	public void setScheduledMaintenanceTasks(Set<ScheduledMaintenanceTask> scheduledMaintenanceTasks) {
		updateCollection("scheduledMaintenanceTasks", scheduledMaintenanceTasks);
	}

	public Double getAmrtNextMeterReading() {
		return amrtNextMeterReading;
	}

	public void setAmrtNextMeterReading(Double amrtNextMeterReading) {
		this.amrtNextMeterReading = amrtNextMeterReading;
	}

	public Double getBmrtNextMeterReading() {
		return bmrtNextMeterReading;
	}

	public void setBmrtNextMeterReading(Double bmrtNextMeterReading) {
		this.bmrtNextMeterReading = bmrtNextMeterReading;
	}

	public Double getCmrtNextMeterReading() {
		return cmrtNextMeterReading;
	}

	public void setCmrtNextMeterReading(Double cmrtNextMeterReading) {
		this.cmrtNextMeterReading = cmrtNextMeterReading;
	}

	
}
