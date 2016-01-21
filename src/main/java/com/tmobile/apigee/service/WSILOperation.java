package com.tmobile.apigee.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.xmlbeans.XmlException;
import org.springframework.stereotype.Service;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.support.SoapUIException;

@Service
public class WSILOperation {
	
	Map<String, ArrayList<String>> map = null;
	
	// Get List of wsdl Names
	public Set<String> getListOfWSDLS(){
		map = this.getOperationNames("wsdls");
		return  map.keySet();
		
	}
	
	// Get List of operations in a wsdl
	public List<String> getOperations(String wsilName){
		
		return  map.get(wsilName);
		
	}
	
	
	public HashMap<String,ArrayList<String>> getOperationNames(String path) {
		HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		try {
			WsdlProject project = new WsdlProject();
			File file = new File(path);
			File [] files = file.listFiles();
			
			for(File fileName : files) {
				WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, fileName.getAbsolutePath());
				for(WsdlInterface wsdl : wsdls) {
					ArrayList<String> list = new ArrayList<String>();
					map.put(fileName.getName(), list);
					for (Operation operation : wsdl.getOperationList()) {
						WsdlOperation op = (WsdlOperation) operation;
						list.add(op.getName());					
					}
				}				
			}
			
			
			//project = null;
			project.release();
			//project.release();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SoapUIException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/*public static void main(String[] args) {
		WSILOperation wn = new WSILOperation();
		HashMap<String,ArrayList<String>> map = wn.getOperationNames("wsdls");
		for(String wsdlName : map.keySet()) {
			ArrayList<String> list = map.get(wsdlName);
			for(String operation : list) {
				System.out.println(wsdlName +"\t"+operation);
			}
			
		}
		System.exit(-1);
	}*/

}
