package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum MRNReturnStatus {

    DRAFT(0, "Draft"),
    REQUETED(1, "Requested"),
    APPROVED(2, "Approved");


    private Integer id;
    private String name;

    MRNReturnStatus(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static MRNReturnStatus getAODStatusById(Integer id) {
        if (id == 0) {
            return DRAFT;
        } else if (id == 1) {
            return APPROVED;
        }
        return null;
    }

    public static List<MRNReturnStatus> getAODStatusList() {
        List<MRNReturnStatus> list = new ArrayList<MRNReturnStatus>();
        list.add(MRNReturnStatus.DRAFT);
        list.add(MRNReturnStatus.REQUETED);
        list.add(MRNReturnStatus.APPROVED);

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
