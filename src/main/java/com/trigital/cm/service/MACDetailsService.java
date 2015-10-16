package com.trigital.cm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trigital.cm.domain.MACDetails;

@Service
public class MACDetailsService {
	
	private static final Logger log = LoggerFactory.getLogger(MACDetailsService.class);
	
	@Autowired
	ExecuteShellComand esc;
    
    public MACDetailsService(){
    	
    }
    

    public MACDetails getMACDetails(String cmIp,String macAddress) {
    	MACDetails macDetails = new MACDetails();
    	String result = esc.shellCommandExecuter();
    	
    	String[] lineSeparators = result.split("\n");
    	//System.out.println("length "+lineSeparators.length);
    	
    	/*for (String string : lineSeparators) {
			System.out.println("log "+string);
		}*/
    	
    	String details = lineSeparators[3];
    	String[] listOfDetails = details.split("\\s+");
    	
    	macDetails.setMac_Address(listOfDetails[0]);
    	macDetails.setIp_Address(listOfDetails[1]);
    	macDetails.setI_f(listOfDetails[2]);
    	macDetails.setMac_state(listOfDetails[3]);
    	macDetails.setPrim_Sid(listOfDetails[4]);
    	macDetails.setRxpwr(listOfDetails[5]);
    	macDetails.setTiming_Offset(listOfDetails[6]);
    	macDetails.setNum_CPE(listOfDetails[7]);
    	macDetails.setDIP(listOfDetails[8]);
        return macDetails;
    }

}
