package com.codex.ecam.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.model.maintenance.CalendarEvent;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;

public class SchedulingUtil {

	private static final Integer DEFAULT_SCHEDULING_YEAR_RANGE = 1;

	public static CalendarEvent getNextCalenderEvent(ScheduledMaintenanceTrigger trigger) {
		return getNextCalenderEvent(trigger, trigger.getTtStartDate() == null ? new Date() : trigger.getTtStartDate());
	}

	public static CalendarEvent getNextCalenderEvent(ScheduledMaintenanceTrigger trigger, Date startDate) {

		Calendar calStart = createStartDate(startDate);
		Calendar calEnd = createEndDate(trigger, calStart);

		if ((trigger != null) && trigger.getTriggerType().equals(SMTriggerType.TIME_TRIGGER)) {

			switch (trigger.getTtOccurenceType()) {

			case HOURLY:
				return getHourlyNextCalendarEvent(calStart, trigger, calEnd);

			case DAILY:
				return getDailyNextCalendarEvent(calStart, trigger, calEnd);

			case WEEKLY:
				return getWeeklyNextCalendarEvent(calStart, trigger, calEnd);

			case MONTHLY:
				return getMonthlyNextCalendarEvent(calStart, trigger, calEnd);

			case YEARLY:
				return getYearlyNextCalendarEvent(calStart, trigger, calEnd);

			default:
				return null;

			}
		}

		return null;
	}

	private static CalendarEvent getHourlyNextCalendarEvent(Calendar calStart, ScheduledMaintenanceTrigger trigger, Calendar calEnd) {
		calStart.add(Calendar.HOUR, trigger.getConditionValue().intValue());
		return getNextCalendarEvent(trigger, calStart, calEnd);
	}

	private static CalendarEvent getDailyNextCalendarEvent(Calendar calStart, ScheduledMaintenanceTrigger trigger, Calendar calEnd) {
		calStart.add(Calendar.DATE, trigger.getConditionValue().intValue());
		return getNextCalendarEvent(trigger, calStart, calEnd);
	}

	private static CalendarEvent getWeeklyCalenderEventByDay(ScheduledMaintenanceTrigger trigger, Calendar calStart, int day, Calendar calEnd) {
		calStart.set(Calendar.DATE, day * 7);
		calStart.add(Calendar.WEEK_OF_MONTH, trigger.getConditionValue().intValue());
		return getNextCalendarEvent(trigger, calStart, calEnd);
	}

	private static CalendarEvent getMonthlyNextCalendarEvent(Calendar calStart, ScheduledMaintenanceTrigger trigger, Calendar calEnd) {
		calStart.set(Calendar.DAY_OF_MONTH, trigger.getTtDayOfMonth());
		calStart.add(Calendar.MONTH, trigger.getConditionValue().intValue());
		return getNextCalendarEvent(trigger, calStart, calEnd);
	}

	private static CalendarEvent getYearlyNextCalendarEvent(Calendar calStart, ScheduledMaintenanceTrigger trigger, Calendar calEnd) {
		calStart.set(Calendar.DAY_OF_MONTH, trigger.getTtDayOfMonth());
		setMonthOfYear(calStart, trigger);
		calStart.add(Calendar.YEAR, trigger.getConditionValue().intValue());
		return getNextCalendarEvent(trigger, calStart, calEnd);
	}

	private static CalendarEvent getWeeklyNextCalendarEvent(Calendar calStart, ScheduledMaintenanceTrigger trigger, Calendar calEnd) {

		CalendarEvent calEvent = null;

		if((trigger.getTtInSunday() != null) && trigger.getTtInSunday()){

			CalendarEvent event = getWeeklyCalenderEventByDay(trigger, calStart, Calendar.SUNDAY, calEnd);
			if ((calEvent == null) || ( (event != null) && event.getScheduledDate().before(calEvent.getScheduledDate()) ) ) {
				calEvent = event;
			}
		}

		if((trigger.getTtInMonday() != null) && trigger.getTtInMonday()){

			CalendarEvent event = getWeeklyCalenderEventByDay(trigger, calStart, Calendar.MONDAY, calEnd);
			if ((calEvent == null) || ( (event != null) && event.getScheduledDate().before(calEvent.getScheduledDate()) ) ) {
				calEvent = event;
			}
		}

		if((trigger.getTtInTuesday() != null) && trigger.getTtInTuesday()){

			CalendarEvent event = getWeeklyCalenderEventByDay(trigger, calStart, Calendar.TUESDAY, calEnd);
			if ((calEvent == null) || ( (event != null) && event.getScheduledDate().before(calEvent.getScheduledDate()) ) ) {
				calEvent = event;
			}
		}

		if((trigger.getTtInWednesday() != null) && trigger.getTtInWednesday()){

			CalendarEvent event = getWeeklyCalenderEventByDay(trigger, calStart, Calendar.WEDNESDAY, calEnd);
			if ((calEvent == null) || ( (event != null) && event.getScheduledDate().before(calEvent.getScheduledDate()) ) ) {
				calEvent = event;
			}
		}

		if((trigger.getTtInThursday() != null) && trigger.getTtInThursday()){

			CalendarEvent event = getWeeklyCalenderEventByDay(trigger, calStart, Calendar.THURSDAY, calEnd);
			if ((calEvent == null) || ( (event != null) && event.getScheduledDate().before(calEvent.getScheduledDate()) ) ) {
				calEvent = event;
			}
		}

		if((trigger.getTtInFriday() != null) && trigger.getTtInFriday()){

			CalendarEvent event = getWeeklyCalenderEventByDay(trigger, calStart, Calendar.FRIDAY, calEnd);
			if ((calEvent == null) || ( (event != null) && event.getScheduledDate().before(calEvent.getScheduledDate()) ) ) {
				calEvent = event;
			}
		}

		if((trigger.getTtInSaturday() != null) && trigger.getTtInSaturday()){

			CalendarEvent event = getWeeklyCalenderEventByDay(trigger, calStart, Calendar.SATURDAY, calEnd);
			if ((calEvent == null) || ( (event != null) && event.getScheduledDate().before(calEvent.getScheduledDate()) ) ) {
				calEvent = event;
			}
		}

		return calEvent;

	}

	private static Calendar createStartDate(Date startDate) {
		Calendar calStart = Calendar.getInstance();
		if (startDate != null) {
			setDateValueToCalender(calStart, startDate);
		}
		return calStart;
	}

	private static Calendar createEndDate(ScheduledMaintenanceTrigger trigger, Calendar calStart) {
		Calendar calEnd = Calendar.getInstance();

		if (trigger.getTtEndDate() != null) {
			setDateValueToCalender(calEnd, trigger.getTtEndDate());
		} else {
			calEnd.set(Calendar.YEAR, calStart.get(Calendar.YEAR));
			calEnd.set(Calendar.MONTH, calStart.get(Calendar.MONTH));
			calEnd.set(Calendar.DATE, calStart.get(Calendar.DATE));
			calEnd.add(Calendar.YEAR, DEFAULT_SCHEDULING_YEAR_RANGE);
		}

		return calEnd;
	}

	private static void setDateValueToCalender(Calendar cal, Date date) {

		LocalDate localDate;

		if (date instanceof java.sql.Date) {
			localDate = ((java.sql.Date) date).toLocalDate();
		} else {
			localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		cal.set(Calendar.YEAR, localDate.getYear());
		cal.set(Calendar.MONTH, localDate.getMonthValue() - 1);
		cal.set(Calendar.DATE, localDate.getDayOfMonth());
	}

	private static CalendarEvent getNextCalendarEvent(ScheduledMaintenanceTrigger trigger, Calendar calStart, Calendar calEnd) {
		if (trigger.getScheduleIsFixed()) {
			if ( calStart.before(calEnd)) {
				return createCalendarEvent(trigger, calStart.getTime());
			}
		}

		return null;
	}

	private static CalendarEvent createCalendarEvent(ScheduledMaintenanceTrigger trigger, Date scheduleDate) {
		CalendarEvent event = new CalendarEvent();
		event.setScheduledMaintenanceTrigger(trigger);
		event.setScheduledDate(scheduleDate);
		event.setIsDeleted(false);

		return event;
	}

	private static void setMonthOfYear(Calendar calStart, ScheduledMaintenanceTrigger trigger) {

		switch (trigger.getTtMonth()) {

		case JANUARY:
			calStart.set(Calendar.MONTH, Calendar.JANUARY);
			break;

		case FEBRUARY:
			calStart.set(Calendar.MONTH, Calendar.FEBRUARY);
			break;

		case MARCH:
			calStart.set(Calendar.MONTH, Calendar.MARCH);
			break;

		case APRIL:
			calStart.set(Calendar.MONTH, Calendar.APRIL);
			break;

		case MAY:
			calStart.set(Calendar.MONTH, Calendar.MAY);
			break;

		case JUNE:
			calStart.set(Calendar.MONTH, Calendar.JUNE);
			break;

		case JULY:
			calStart.set(Calendar.MONTH, Calendar.JULY);
			break;

		case AUGUST:
			calStart.set(Calendar.MONTH, Calendar.AUGUST);
			break;

		case SEPTEMBER:
			calStart.set(Calendar.MONTH, Calendar.SEPTEMBER);
			break;

		case OCTOBER:
			calStart.set(Calendar.MONTH, Calendar.OCTOBER);
			break;

		case NOVEMBER:
			calStart.set(Calendar.MONTH, Calendar.NOVEMBER);
			break;

		case DECEMBER:
			calStart.set(Calendar.MONTH, Calendar.DECEMBER);
			break;

		default:
			break;
		}
	}

}
