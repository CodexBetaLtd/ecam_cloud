package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum MRNStatus {

    DRAFT(0, "Draft"),
    REQUETED(1, "Requested"),
    APPROVED(2, "Approved"),
    REJECTED(3, "Rejected");


    private Integer id;
    private String name;

    MRNStatus(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static MRNStatus getAODStatusById(Integer id) {
     MRNStatus mrnStatus=MRNStatus.DRAFT;
        if (id == 0) {
        	mrnStatus= DRAFT;
        } else if (id == 1) {
        	mrnStatus=REQUETED;
        } else if (id == 2) {
        	mrnStatus= APPROVED;
        } else if (id == 3) {
        	mrnStatus= REJECTED;
        }
        return mrnStatus;
    }

    public static List<MRNStatus> getAODStatusList() {
        List<MRNStatus> list = new ArrayList<MRNStatus>();
        list.add(MRNStatus.DRAFT);
        list.add(MRNStatus.REQUETED);
        list.add(MRNStatus.APPROVED);
        list.add(MRNStatus.REJECTED);

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
