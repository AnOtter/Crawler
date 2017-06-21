package com.ai.xml;

import java.io.File;
import java.io.FileOutputStream;

import javax.print.Doc;
import javax.sql.rowset.spi.XmlReader;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import static com.ai.util.DateTime.*;


/**
 * @author OTTER
 * @version 20170620
 * @描述 xml文件操作类
 */
public class XMLDocument {
	
	private String rootNodeName="";
	private Document doc=null;
	
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
	
	public void appendNode(Element node) {
		doc.getRootElement().add(node);		
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
	
	public void loadFromFile(String filePath) throws Exception{
		File xmlFile=new File(filePath);
		SAXReader reader =new SAXReader();	    
		doc=reader.read(xmlFile);
	}

	
}
