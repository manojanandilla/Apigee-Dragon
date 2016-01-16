package com.tmobile.apigee.domain;

import java.util.List;

public class WSDL {
	
	private String wsdlName;
	private String wsdlVersion;
	private String operation;
	private String type;
	
	public WSDL(String wsdlName, String wsdlVersion, String operation,String type) {
		
		this.wsdlName = wsdlName;
		this.wsdlVersion = wsdlVersion;
		this.operation = operation;
		this.type = type;
	}

	public String getWsdlName() {
		return wsdlName;
	}

	public void setWsdlName(String wsdlName) {
		this.wsdlName = wsdlName;
	}

	public String getWsdlVersion() {
		return wsdlVersion;
	}

	public void setWsdlVersion(String wsdlVersion) {
		this.wsdlVersion = wsdlVersion;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
