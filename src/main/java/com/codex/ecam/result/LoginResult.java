package com.codex.ecam.result;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.constants.ResultStatus;

public class LoginResult {

	private ResultStatus status = ResultStatus.SUCCESS;

	private List<String> errorList = new ArrayList<String>();
	private List<String> msgList = new ArrayList<String>();
	private List<Integer> integerList = new ArrayList<Integer>();

    private String error = "";
    private String msg = "";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
	
	public void addToMessageList(String message){
		getMsgList().add(message);
	}
	
	public void addToErrorList(String message){
		getErrorList().add(message);
	}

	public List<Integer> getIntegerList() {
		return integerList;
	}

	public void setIntegerList(List<Integer> integerList) {
		this.integerList = integerList;
	}
	
	public void addToInteger(Integer integer){
		getIntegerList().add(integer);
	}

	public ResultStatus getStatus() {
		return status;
	}

	public void setStatus(ResultStatus status) {
		this.status = status;
	}

	public List<String> getErrorList() {
		return errorList;
	}
	
	public void setResultStatusSuccess(){
		this.status = ResultStatus.SUCCESS;
	}
	
	public void setResultStatusError(){
		this.status = ResultStatus.ERROR;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public List<String> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<String> msgList) {
		this.msgList = msgList;
	}

}
