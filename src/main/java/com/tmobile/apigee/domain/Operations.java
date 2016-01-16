package com.tmobile.apigee.domain;

public class Operations {
	
	String operation;
	String wsdlName;
	String type;
	
	public Operations(String operation, String wsdlName, String type) {
		
		this.operation = operation;
		this.wsdlName = wsdlName;
		this.type = type;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getWsdlName() {
		return wsdlName;
	}

	public void setWsdlName(String wsdlName) {
		this.wsdlName = wsdlName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
	

}
