package com.neolith.focus.constants;

public enum SMTriggerType {
	
	TIME_TRIGGER(1, "Time Trigger"),
    METER_READING_TRIGGER(2, "Meter Reading Trigger"),
    EVENT_TRIGGER(3, "Event Trigger");

    private Integer id;
    private String name;

    SMTriggerType(Integer id, String name){
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
