package com.codex.ecam.constants.util;

public enum PrintType {

	PDF(1, "pdf",".pdf","application/pdf"),
	CSV(2, "csv",".csv","text/csv");

    private Integer id;
    private String name;
    private String extention;
    private String contentType;



    private PrintType(Integer id, String name, String extention, String contentType) {
		this.id = id;
		this.name = name;
		this.extention = extention;
		this.contentType = contentType;
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

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
    
    
}
