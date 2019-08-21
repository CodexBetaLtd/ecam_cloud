package com.codex.ecam.constants.util;

public enum Days {

	SUNDAY(1, "Sunday"),
	MONDAY(2, "Monday"),
	TUESDAY(3, "Tuesday"),
	WEDNESDAY(4, "Wednesday"),
	THURSDAY(5, "Thursday"),
	FRIDAY(6, "Friday"),
	SATURDAY(7, "Saturday");

    private Integer id;
    private String name;

    Days(Integer id, String name){
        setId(id);
        setName(name);
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
