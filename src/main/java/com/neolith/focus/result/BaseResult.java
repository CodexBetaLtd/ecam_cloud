package com.neolith.focus.result;

import com.neolith.focus.constants.ResultStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseResult<DOMAIN, DTO> {

	private DOMAIN domainEntity = null;
	private DTO dtoEntity = null;
	private ResultStatus status = ResultStatus.SUCCESS;

	private List<String> errorList = new ArrayList<String>();
	private List<String> msgList = new ArrayList<String>();
	private List<Integer> integerList = new ArrayList<Integer>();

	public BaseResult(DOMAIN domain, DTO dto) {
		setDomainEntity(domain);
		setDtoEntity(dto);
	}
	
	public abstract void updateDtoIdAndVersion();

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

	public DOMAIN getDomainEntity() {
		return domainEntity;
	}

	public void setDomainEntity(DOMAIN domainEntity) {
		this.domainEntity = domainEntity;
	}

	public DTO getDtoEntity() {
		return dtoEntity;
	}

	public void setDtoEntity(DTO dtoEntity) {
		this.dtoEntity = dtoEntity;
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

}
