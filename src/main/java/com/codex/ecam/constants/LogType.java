package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum LogType {

	CREATE(0, "Creation Log"),
	UPDATE(1, "Update Log"),
	REMOVE(2, "Remove Log");

	private Integer id;
	private String name;

	private LogType(Integer id, String name) {
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

	public static List<LogType> getLogTypeList() {
		List<LogType> list = new ArrayList<LogType>();
		list.add(LogType.CREATE);
		list.add(LogType.UPDATE);
		list.add(LogType.REMOVE);
		return list;
	}

}
