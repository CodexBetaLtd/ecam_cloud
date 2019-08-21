package com.neolith.focus.constants.user;

import java.util.ArrayList;
import java.util.List;

public enum Salutation {

    SIR(0, "Sir"),
    MADAM(1, "Madam");


    private Integer id;
    private String name;

    Salutation(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<Salutation> getSalutations() {
        List<Salutation> list = new ArrayList<Salutation>();
        list.add(Salutation.SIR);
        list.add(Salutation.MADAM);
        return list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
