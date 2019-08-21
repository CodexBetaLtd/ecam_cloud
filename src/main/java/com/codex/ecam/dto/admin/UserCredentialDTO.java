package com.codex.ecam.dto.admin;

import com.codex.ecam.dto.BaseDTO;

public class UserCredentialDTO extends BaseDTO {

    private Integer id;
    private Integer version;
    private Integer userId;
    private String userName;
	private String password;	
	
	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String passWord) {
		this.password = passWord;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	
}
