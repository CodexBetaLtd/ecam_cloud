package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum MRNStatus {

    DRAFT(0, "Draft"),
    REQUETED(1, "Requested"),
    APPROVED(2, "Approved");


    private Integer id;
    private String name;

    MRNStatus(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static MRNStatus getAODStatusById(Integer id) {
        if (id == 0) {
            return DRAFT;
        } else if (id == 1) {
            return APPROVED;
        }
        return null;
    }

    public static List<MRNStatus> getAODStatusList() {
        List<MRNStatus> list = new ArrayList<MRNStatus>();
        list.add(MRNStatus.DRAFT);
        list.add(MRNStatus.REQUETED);
        list.add(MRNStatus.APPROVED);

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
