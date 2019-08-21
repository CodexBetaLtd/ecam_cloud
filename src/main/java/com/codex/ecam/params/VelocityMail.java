package com.codex.ecam.params;

import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;

public class VelocityMail {

	private String to;
	private String subject;
	Map<String, Object> model = new HashMap<String, Object>();
	private String objname;
	private String vmTemplate;
	
	/** Attachment **/
	private String fileName;
	private DataSource  attachmentFile;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getVmTemplate() {
		return vmTemplate;
	}
	public void setVmTemplate(String vmTemplate) {
		this.vmTemplate = vmTemplate;
	}
	public String getObjname() {
		return objname;
	}
	public void setObjname(String objname) {
		this.objname = objname;
	}
	public Map<String, Object> getModel() {
		return model;
	}
	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public DataSource getAttachmentFile() {
		return attachmentFile;
	}
	public void setAttachmentFile(DataSource attachmentFile) {
		this.attachmentFile = attachmentFile;
	}

	
}
