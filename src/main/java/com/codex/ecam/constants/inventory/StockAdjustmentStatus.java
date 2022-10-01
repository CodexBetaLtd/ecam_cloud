package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum StockAdjustmentStatus {

    DRAFT(0, "Draft"),
    APPROVED(1, "Approved");


    private Integer id;
    private String name;

    StockAdjustmentStatus(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static StockAdjustmentStatus getStatusById(Integer id) {
        if (id == 0) {
            return DRAFT;
        } else if (id == 1) {
            return APPROVED;
        }
        return null;
    }

    public static List<StockAdjustmentStatus> getStatusList() {
        List<StockAdjustmentStatus> list = new ArrayList<StockAdjustmentStatus>();
        list.add(StockAdjustmentStatus.DRAFT);
        list.add(StockAdjustmentStatus.APPROVED);

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
