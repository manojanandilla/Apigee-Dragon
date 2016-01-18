package com.tmobile.apigee.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tmobile.apigee.domain.WSDL;

@Service
public class DMValidatorService {
	
	private static final Logger log = LoggerFactory.getLogger(DMValidatorService.class);
    
    public DMValidatorService(){
    	
    }
    
    public List<String> getWSDLs(){
    	
    	List<String> listOfWSDLs = new ArrayList<String>();
    	
    	listOfWSDLs.add("CartWSIL");
    	listOfWSDLs.add("PaymentWSIL");
    	listOfWSDLs.add("PortWSIL");
    	listOfWSDLs.add("OrderWSIL");
    	
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

	public String validateDM(WSDL wsdl) {
		
		return "abcdef asdfa asdfas asdfasdf ";
	}
    


}
