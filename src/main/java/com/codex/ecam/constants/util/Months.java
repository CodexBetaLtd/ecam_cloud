package com.codex.ecam.constants.util;

import java.util.ArrayList;
import java.util.List;

public enum Months {
	
	JANUARY(1, "January"),
	FEBRUARY(2, "February"),
	MARCH(3, "March"),
    APRIL(4, "April"),
    MAY(5, "May"),
    JUNE(6, "June"),
    JULY(7, "July"),
	AUGUST(8, "August"),
	SEPTEMBER(9, "September"),
	OCTOBER(10, "October"),
	NOVEMBER(11, "November"),
	DECEMBER(12, "December");

    private Integer id;
    private String name;

    private Months(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<Months> getMonths() {
        List<Months> list = new ArrayList<Months>();

        list.add(JANUARY);
        list.add(FEBRUARY);
        list.add(MARCH);
        list.add(APRIL);
        list.add(MAY);
        list.add(JUNE);
        list.add(JULY);
        list.add(AUGUST);
        list.add(SEPTEMBER);
        list.add(OCTOBER);
        list.add(NOVEMBER);
        list.add(DECEMBER);

        return list;
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
}
