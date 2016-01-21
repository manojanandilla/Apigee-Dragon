package com.tmobile.apigee.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xmlbeans.XmlException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.support.SoapUIException;
import com.tmobile.apigee.domain.WSDL;

@Service
public class DMValidator {
	
	static String tempFile ="temp/temp.xml";
	
	
	public String WSDLPath;
	public String operationName;
	public String dataMappingContent;
	public String type;

	public DMValidator() {
	}

	public DMValidator(String WSDLPath, String operationName,String type, String dataMappingContent) {
		
		this.WSDLPath = WSDLPath;
		this.operationName = operationName;
		this.dataMappingContent = readData();
		this.type = type;
	}
	//TEMP METHOD
	public String readData() {
		StringBuffer sb = new StringBuffer();
		try {			
			BufferedReader br = new BufferedReader(new FileReader("dmfiles/generateCartQuotation_res_dm.txt"));
			String line = null;
			while((line=br.readLine())!=null) {
				if(line.contains("\t")) {
					String[] lines = line.split("\t");
				    if(lines.length ==2)
					 sb.append(lines[0]+"\t"+lines[1]+"\n");
				    else 
				      sb.append(lines[0]+"\n");
					}
				}
			   br.close();
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	

	/**
	 * 
	 * @param args
	 */
	public List validateDM(WSDL wsdl) {
		
		DMValidator dmv = new DMValidator("wsdls/"+wsdl.getWsdlName(),wsdl.getOperation(),wsdl.getType(),wsdl.getxPath());
		List<String> validateResult = new ArrayList<String>();
		dmv.getSOAPData();
		
		ArrayList<String> dataList = dmv.getWSDLData();
		LinkedHashMap<String, String> dataMap = dmv.getDataMapping(wsdl.getxPath());
		LinkedHashMap<String, String> wsilMap = dmv.getWSILMapping(dataList);
		StringBuffer sb = new StringBuffer();
		
		sb.append("XPath\tDM Cordinality\tCSOM/WSIL Cordinality\n");
		
		for (String dmPath : dataMap.keySet()) {
			String dmCardinality =  dataMap.get(dmPath).trim();
			String wsilCardinality = dmv.getWSILCardinality(wsilMap, dmPath);
			if (dmCardinality == null
					|| dmCardinality.trim().length() == 0) {
				dmCardinality = "Not Found";
			}
			if(wsilCardinality == null ||
					wsilCardinality.trim().length() == 0) {
				wsilCardinality = "Not Found";
			}
			if((dmCardinality.equalsIgnoreCase("Not Found") || wsilCardinality.equalsIgnoreCase("Not Found")) ||
					!(dmCardinality.equalsIgnoreCase(wsilCardinality))){
				validateResult.add(dmPath+"\t"+dmCardinality+"\t"+wsilCardinality+"\n");
				sb.append(dmPath+"\t"+dmCardinality+"\t"+wsilCardinality+"\n");	
			}
					
		}
		System.out.println(sb.toString());
		return validateResult;
	}
	
	public String getWSILCardinality(LinkedHashMap<String, String> wsilMap, String dmPath) {
		String cardinality = null;
		for (String wsilMapPath : wsilMap.keySet()) { 
			if(wsilMapPath.endsWith(dmPath)) {				
				cardinality = wsilMap.get(wsilMapPath);
				break;
			} 
		}
		return cardinality;
	}
	

	/**
	 * Get SOAP Request and Response for given WSDL
	 * 
	 * @param operationName
	 * @param wsilPath
	 * @return
	 */
	private void getSOAPData() {
		String data = null;
		try {
			WsdlProject project = new WsdlProject();
			WsdlInterface[] wsdls = WsdlImporter.importWsdl(project,
					this.WSDLPath);
			WsdlInterface wsdl = wsdls[0];
			for (Operation operation : wsdl.getOperationList()) {
				WsdlOperation op = (WsdlOperation) operation;
				if (op.getName().equalsIgnoreCase(this.operationName)) {
					if (this.type.equalsIgnoreCase("request"))
						data = op.createRequest(true);
					else
						data = op.createResponse(true);

					break;
				}
			}
			File file = new File(tempFile);
			file.getParentFile().mkdirs();
			file.createNewFile();			
			FileWriter fw = new FileWriter(file);
			fw.write(data);
			fw.flush();
			fw.close();			
			project.release();			
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SoapUIException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get WSDL data
	 * 
	 * @param soapData
	 * @return
	 */
	public ArrayList<String> getWSDLData() {		
		StringBuffer sb = new StringBuffer();
		ArrayList<String> dataList = new ArrayList<String>();
		try {		
			// parse the document
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			docBuilderFactory.setIgnoringComments(false);
			docBuilderFactory.setNamespaceAware(false);
			DocumentBuilder docBuilder;
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(tempFile);
			NodeList list = doc.getChildNodes();
			// NodeList list = doc.getElementsByTagName(rootElement);

			for (int i = 0; i < list.getLength(); i++) {
				Node node = (Node) list.item(i);
				// reading comments here
				if (node.getNodeType() == Element.COMMENT_NODE) {
					String comments = getComments(node);
					if (comments != null && comments.trim().length() > 0) {
						sb.append(comments + "\t");
					}
				}

				// reading elements and its attributes
				if (node.getNodeType() == Element.ELEMENT_NODE) {
					String elementName = ((Element) node).getTagName();
					String path = getXPath(node, "");
					//System.out.println("readWSILData-> " + path);
					if (node.hasAttributes()) {
						ArrayList<String> atrrList = getAttributes(node);
						sb.append(path + "\n");
						for (String attr : atrrList) {
							// attribute setting up as 0/1 by default
							sb.append("0/1\t" + path + "/" + attr + "\n");
						}
					} else {
						sb.append(path + "/" + elementName + "\n");
					}
					getChilds(node, sb);
				}
			}

			// storing into List
			if (sb.toString() != null && sb.toString().length() > 1) {
				String[] lines = sb.toString().split("\n");
				for (String line : lines) {
					dataList.add(line);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException ed) {
			ed.printStackTrace();
		}
		return dataList;
	}

	/**
	 * 
	 * @param node
	 */
	public static void getChilds(Node node, StringBuffer sb) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node childNode = (Node) list.item(i);
			if (childNode.getNodeType() == Element.COMMENT_NODE) {
				String comment = getComments(childNode);
				if (comment != null && comment.trim().length() > 0) {
					sb.append(comment + "\t");
				}
			}

			if (childNode.getNodeType() == Element.ELEMENT_NODE) {
				if (childNode.hasAttributes()) {
					ArrayList<String> atrrList = getAttributes(childNode);
					String path = getXPath(childNode, "");
					sb.append(path + "\n");
					for (String attr : atrrList) {
						// attribute setting up as 0/1 by default
						sb.append("0/1\t" + path + "/" + attr + "\n");
					}
				} else {
					String path = getXPath(childNode, "");
					sb.append(path + "\n");
				}
				getChilds(childNode, sb);
			}
		}
	}

	public static String getXPath(Node node, String xpath) {
		if (node == null) {
			return "";
		}
		String elementName = "";
		if (node instanceof Element) {
			elementName = ((Element) node).getTagName();
		}
		Node parent = node.getParentNode();
		if (parent == null) {
			return xpath;
		}
		return getXPath(parent, "/" + elementName + xpath);
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public static ArrayList<String> getAttributes(Node node) {
		ArrayList<String> list = new ArrayList<String>();
		if (node.getNodeType() == Element.ELEMENT_NODE) {
			Element ele = (Element) node;
			NamedNodeMap map = ele.getAttributes();
			for (int j = 0; j < map.getLength(); j++) {
				Node n1 = map.item(j);
				list.add("@" + n1.getNodeName());
			}
		}
		return list;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public static String getComments(Node node) {
		Comment comment = (Comment) node;
		String comm = comment.getData();
		if (comm.contains("Optional")) {
			return "0/1";
		} else if (comm.contains("Zero or more repetitions")) {
			return "0/*";
		} else {
			return "";
		}		
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public LinkedHashMap<String, String> getDataMapping(String content) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String[] lines = content.split("\n");
		for (String line : lines) {
			if (line.trim().length() > 0) {
				line = line.trim();
				if (line.contains("\t")) {
					String[] lineArray = line.split("\t");
					String xpath = lineArray[0].replaceAll("\"", "").trim();
					if (lineArray.length > 1) {
						map.put(xpath, getCardinality(lineArray[1]));
					} else {
						map.put(xpath, "");
					}
				} else {
					map.put(line, "");
				}

			}
		}

		return map;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public LinkedHashMap<String, String> getWSILMapping(ArrayList<String> dataList) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (String line : dataList) {			
			if (line.trim().length() > 0) {
				line = line.trim();
				if (line.contains("\t")) {
					String[] lineArray = line.split("\t");
					if (lineArray.length > 1) {
						String xpath = lineArray[1].replaceAll("\"", "").trim();
						map.put(getModifyXPath(xpath), getCardinality(lineArray[0]));						
					}
				} else {
					map.put(getModifyXPath(line), "1/1");					
				}
			}
		}
		return map;
	}
	
	private String getModifyXPath(String xpath) {		
			StringBuffer sb = new StringBuffer();
			String [] paths = xpath.split("/");
			for(int i=0;i<paths.length;i++) {
				String path= paths[i];			
				if(path.contains(":")){
					sb.append(path.split(":")[1].trim());
				} else {
					sb.append(path.trim());
				}
				if(i<paths.length-1) {
					sb.append("/");
				}
			}
			return sb.toString();
	}
	

	/**
	 * 
	 * @param val
	 * @return
	 */
	public String getCardinality(String val) {
		val = val.replaceAll("\"", "").trim();
		if (val.startsWith("1") && val.endsWith("1")) {
			return "1/1";
		} else if (val.startsWith("0") && val.endsWith("1")) {
			return "0/1";
		} else if (val.startsWith("0") && val.endsWith("*")) {
			return "0/*";
		} else if (val.startsWith("1") && val.endsWith("*")) {
			return "1/*";
		} else {
			return val;
		}
	}

}
