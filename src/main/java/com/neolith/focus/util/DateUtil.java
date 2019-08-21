package com.neolith.focus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat COMMON_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static String getCommonDateString(Date date) {
        if (date != null) {
            return COMMON_DATE_FORMAT.format(date);
        } else {
            return "";
        }
    }

    public static String getSimpleDateString(Date date) {
        if (date != null) {
            return SIMPLE_DATE_FORMAT.format(date);
        } else {
            return "";
        }
    }
    
    public static Date getDateObj(String date) {
    	try {
    		return SIMPLE_DATE_FORMAT.parse(date);
    	} catch (ParseException e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    public static Date getDateTimeObj(String date) {
        try {
			return COMMON_DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
