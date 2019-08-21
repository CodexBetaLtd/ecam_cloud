package com.neolith.focus.constants;

public enum SMTriggerFireCondition {

    ALL_TRIGGERS_FIRE(0, "All Triggers Fire"),
    ANY_TRIGGERS_FIRE(1, "Any Triggers Fire");

    private Integer id;
    private String name;

    SMTriggerFireCondition(Integer id, String name) {
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
