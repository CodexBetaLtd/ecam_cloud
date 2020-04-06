package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum AODType {

    WORKORDER(0, "Work Order"),
    OTHER(1, "Other"),
    ISSUE_NOTE(2, "Issue Note"),
	GRN_RETURN(3, "GRN Return");

    private Integer id;
    private String name;

    AODType(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<AODType> getAODTypes() {
    	List<AODType> list = new ArrayList<AODType>();
    	list.add(AODType.WORKORDER); 
    	list.add(AODType.GRN_RETURN);
    	list.add(AODType.OTHER);
    	return list;
    }
    
    public static List<AODType> getAllAODTypes() {
    	List<AODType> list = new ArrayList<AODType>();
    	list.add(AODType.ISSUE_NOTE);
    	list.add(AODType.WORKORDER); 
    	list.add(AODType.GRN_RETURN);
    	list.add(AODType.OTHER);
    	return list;
    }
    
    public static List<AODType> getIssueNoteTypes() {
        List<AODType> list = new ArrayList<AODType>();
        list.add(AODType.ISSUE_NOTE);
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
