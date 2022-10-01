package com.codex.ecam.constants;

public enum TAXType {

    FIX_VALUE(0, "Fix Value"),
    PERCENTAGE(1, "Percentage");

    private Integer id;
    private String name;

    TAXType(Integer id, String name) {
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
