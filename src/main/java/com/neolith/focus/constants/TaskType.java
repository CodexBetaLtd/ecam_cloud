package com.neolith.focus.constants;


import java.util.ArrayList;
import java.util.List;

public enum TaskType {

    GENERAL(0, "General"),
    METER_READING(1, "Meter Reading"),
    TIME_SCHEDULING(2, "Time Scheduling");

    private Integer id;
    private String name;

    public static List<TaskType> getTaskTypes() {
        List<TaskType> taskTypes = new ArrayList<>();
        taskTypes.add(GENERAL);
        taskTypes.add(METER_READING);
        taskTypes.add(TIME_SCHEDULING);
        return taskTypes;
    }

    TaskType(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static TaskType getTaskById(Integer id) {
        List<TaskType> taskTypes = getTaskTypes();
        for (TaskType type : taskTypes) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("The given number doesn't match any Tasks.");
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
