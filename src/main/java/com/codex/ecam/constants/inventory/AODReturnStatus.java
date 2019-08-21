package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum AODReturnStatus {

    DRAFT(0, "Draft"),
    APPROVED(1, "Approved"),
    CANCEL(2, "Cancel");


    private Integer id;
    private String name;

    AODReturnStatus(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<AODReturnStatus> getAODReturnStatus() {
        List<AODReturnStatus> list = new ArrayList<>();
        list.add(AODReturnStatus.DRAFT);
        list.add(AODReturnStatus.APPROVED);
        list.add(AODReturnStatus.CANCEL);
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
