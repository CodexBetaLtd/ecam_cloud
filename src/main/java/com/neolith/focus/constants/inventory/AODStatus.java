package com.neolith.focus.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum AODStatus {

    DRAFT(0, "Draft"),
    APPROVED(1, "Approved");


    private Integer id;
    private String name;

    AODStatus(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static AODStatus getAODStatusById(Integer id) {
        if (id == 0) {
            return DRAFT;
        } else if (id == 1) {
            return APPROVED;
        }
        return null;
    }

    public static List<AODStatus> getAODStatusList() {
        List<AODStatus> list = new ArrayList<AODStatus>();
        list.add(AODStatus.DRAFT);
        list.add(AODStatus.APPROVED);

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
