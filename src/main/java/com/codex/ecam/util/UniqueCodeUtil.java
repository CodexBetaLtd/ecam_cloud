package com.codex.ecam.util;

import org.apache.commons.lang.StringUtils;

import com.codex.ecam.constants.util.AffixList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class UniqueCodeUtil {
    
    public static String getNextCode(AffixList affix, String lastCode) {
        Integer yearSuffix = getCurrentYear();
        Integer nextNo=getNextNumber(affix,lastCode);
        String codePrefix=affix.getCode();
        String nextCode = codePrefix.concat("/").concat(yearSuffix.toString()).concat("/").concat(StringUtils.leftPad(nextNo.toString(), 6, '0'));
        return nextCode;
    }

    private static Integer getNextNumber(AffixList affix,String lastCode){
		Integer nextNo = 0;
		final List<String> codeList = Arrays.asList(lastCode.split("/"));
		if (!codeList.get(0).equalsIgnoreCase(affix.getCode())) {
			nextNo = 1;
		} else {
			nextNo = Integer.parseInt(codeList.get(2)) + 1;
		}
		return nextNo;
    }
    
    private static Integer getCurrentYear(){
    	return Calendar.getInstance().get(Calendar.YEAR);
    }



}
