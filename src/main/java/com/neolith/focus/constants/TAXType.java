package com.neolith.focus.constants;

import java.util.ArrayList;
import java.util.List;

public enum TAXType {

    NBT(0, "NBT"),
    VAT(1, "VAT"),
    NBT_SERVICE(2, "NBT Service");

    private Integer id;
    private String name;

    TAXType(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<TAXType> getTAXTypes() {
        List<TAXType> list = new ArrayList<TAXType>();
        list.add(TAXType.NBT);
        list.add(TAXType.VAT);
        list.add(TAXType.NBT_SERVICE);
        return list;
    }

    public static List<TAXType> getTypesWithoutService() {
        List<TAXType> list = new ArrayList<TAXType>();
        list.add(TAXType.NBT);
        list.add(TAXType.VAT);
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
