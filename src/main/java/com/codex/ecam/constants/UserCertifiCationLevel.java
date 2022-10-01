package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum UserCertifiCationLevel {

    ELEMENTARY(0, "Elementary User"),
    INTERMEDIATE(1, "Intermediate User"),
    ADVANCED(2, "Advanced User"),
    TRAINEE(3, "Trainee User");

    private Integer id;
    private String name;

    private UserCertifiCationLevel(Integer id, String name) {
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

    public static List<UserCertifiCationLevel> getUserCertifiCationLevelList() {
        List<UserCertifiCationLevel> list = new ArrayList<UserCertifiCationLevel>();
        list.add(UserCertifiCationLevel.ELEMENTARY);
        list.add(UserCertifiCationLevel.INTERMEDIATE);
        list.add(UserCertifiCationLevel.ADVANCED);
        list.add(UserCertifiCationLevel.TRAINEE);


        return list;
    }
}
