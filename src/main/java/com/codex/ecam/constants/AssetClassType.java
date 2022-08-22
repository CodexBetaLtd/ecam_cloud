package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum AssetClassType {

	FF(0, "FF"), 
	EE(1, "EE"), 
	CE(2, "CE"), 
	PM(3, "PM"), 
	VW(4, "VW"), 
	MV(5, "MV"), 
	KE(6, "KE");

	private Integer id;
	private String name;

	private AssetClassType(Integer id, String name) {
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
	
    public static List<AssetClassType> getAssetClassType() {
        List<AssetClassType> list = new ArrayList<AssetClassType>();
        list.add(AssetClassType.FF);
        list.add(AssetClassType.EE);
        list.add(AssetClassType.CE);
        list.add(AssetClassType.PM);
        list.add(AssetClassType.VW);
        list.add(AssetClassType.MV);
        list.add(AssetClassType.KE);

        return list;
    }

}
