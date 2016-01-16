package com.tmobile.apigee.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmobile.apigee.domain.MACDetails;
import com.tmobile.apigee.domain.Operations;
import com.tmobile.apigee.domain.WSDL;

@Service
public class DMValidatorService {
	
	private static final Logger log = LoggerFactory.getLogger(DMValidatorService.class);
    
    public DMValidatorService(){
    	
    }
    
    public List<WSDL> getWSDLs(){
    	
    	List<WSDL> listOfWSDLs = new ArrayList<WSDL>();
    	
    	listOfWSDLs.add(new WSDL("CartWSIL", "9.7", null, null));
    	listOfWSDLs.add(new WSDL("PaymentWSIL", "9.7", null, null));
    	listOfWSDLs.add(new WSDL("PortWSIL", "9.7", null, null));
    	listOfWSDLs.add(new WSDL("OrderWSIL", "9.7", null, null));
    	
    	return listOfWSDLs;
    }
    
	public List<String> getOperations(String name) {
		System.out.println(name);
    	
    	List<String> listOfOperations = new ArrayList<String>();
    	
    	listOfOperations.add("updateCart");
    	listOfOperations.add("getCart");
    	listOfOperations.add("getShippingOptions");
    	
    	
    	return listOfOperations;
    }
    


}
