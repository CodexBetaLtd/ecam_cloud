package com.codex.ecam.constants;

public enum SMMeterReadingType {

    EVERY(1, "Every"),
    WHEN(2, "When");

    private Integer id;
    private String name;

    SMMeterReadingType(Integer id, String name) {
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
