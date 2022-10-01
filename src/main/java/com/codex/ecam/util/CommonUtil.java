package com.codex.ecam.util;

import org.apache.commons.lang.StringUtils;

import java.util.Calendar;

public class CommonUtil {

    public static void appendString(StringBuilder strBuilder, String val, String prefix) {
        if (val != null && !val.isEmpty()) {
            if (strBuilder.length() > 0) {
                strBuilder.append(", ");
            }
            strBuilder.append(val);
        }
    }

    public static String setNextCode(String codePrefix, String nextNo) {
        Integer yearSuffix = Calendar.getInstance().get(Calendar.YEAR);
        String nextCode = codePrefix.concat("/").concat(yearSuffix.toString()).concat("/").concat(StringUtils.leftPad(nextNo, 8, '0'));
        return nextCode;
    }

    public static String setNextEstimate(String codePrefix, String nextNo, Integer year) {
        /*  Eg No : EP/00001/A/2017 */

        String nextCode = codePrefix.concat("/").concat(StringUtils.leftPad(nextNo, 5, '0')).concat("/").concat("A").concat("/").concat(year.toString());
        return nextCode;
    }

    public static double round(Double value, int places) {
        if (value == null) return 0.00;

        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
