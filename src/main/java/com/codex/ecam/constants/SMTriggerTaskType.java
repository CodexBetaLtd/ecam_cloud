package com.codex.ecam.constants;

public enum SMTriggerTaskType {
	
	A_TASK(1, "A Labour Task"),
    B_TASK(2, "B Labour Task"),
    C_TASK(3, "C Labour Task");

    private Integer id;
    private String name;

    SMTriggerTaskType(Integer id, String name){
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
