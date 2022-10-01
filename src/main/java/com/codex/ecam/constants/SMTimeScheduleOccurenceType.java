package com.codex.ecam.constants;

public enum SMTimeScheduleOccurenceType {
	
	HOURLY(1, "Hourly"),
	DAILY(2, "Daily"),
	WEEKLY(3, "Weekly"),
	MONTHLY(4, "Monthly"),
	YEARLY(5, "Yearly");

    private Integer id;
    private String name;

    SMTimeScheduleOccurenceType(Integer id, String name){
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
