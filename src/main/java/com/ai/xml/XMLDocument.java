package com.ai.xml;

import java.io.FileOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import static com.ai.util.DateTime.*;


/**
 * @author OTTER
 * @version 20170620
 * @描述 xml文件操作类
 */
public class XMLDocument {
	
	private String rootNodeName;
	private Document doc;
	
	private void createDocument(){
	  if(rootNodeName.equals(""))
		  rootNodeName="Root";	  
	  Element rootNode=DocumentHelper.createElement(rootNodeName);
	  rootNode.addAttribute("CreateTime",now());
	  doc =DocumentHelper.createDocument(rootNode);
	}
	
	public XMLDocument() {
		createDocument();
	}
	
	public XMLDocument(String rootNodeName){
		this.rootNodeName=rootNodeName;
		createDocument();
	}
	
	public Element createNode(String nodeName) {
		return DocumentHelper.createElement(nodeName);		
	}
	
	public void addNode(String nodeName,String nodeValue) {
		Element newNode=DocumentHelper.createElement(nodeName);
		newNode.setText(nodeValue);
		newNode.addAttribute("CreateTime", now());
		doc.getRootElement().add(newNode);		
	}
	
	public boolean save(String filePath){
		try {
			FileOutputStream outputStream=new FileOutputStream(filePath,false);		
			
			XMLWriter writer =new XMLWriter();
			writer.setOutputStream(outputStream);
			writer.write(doc);
			writer.close();
			outputStream.close();
		} catch (Exception e) {
			System.out.println("save exception:"+e.getMessage());
		}
		return true;
		
	}

	
}
