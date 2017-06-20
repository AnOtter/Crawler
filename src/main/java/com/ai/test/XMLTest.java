package com.ai.test;

import org.dom4j.Document;
import org.dom4j.io.XMLWriter;
import java.io.FileOutputStream;

import org.dom4j.*;

public class XMLTest {

	public static void main(String[] args) {
		Element rootNode=DocumentHelper.createElement("Root");
		Document doc=DocumentHelper.createDocument(rootNode);
		Element subNode=DocumentHelper.createElement("SubNode");
		subNode.setText("This is a sub node for xml");
		subNode.addAttribute("Type", "String");
		subNode.addAttribute("CreateDate", "20170620");
		rootNode.add(subNode);
		
		try {
			FileOutputStream outputStream=new FileOutputStream("D:\\new.xml");		
			
			XMLWriter writer =new XMLWriter();
			writer.setOutputStream(outputStream);
			writer.write(doc);
			writer.close();
			outputStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		
	}
	
	

}
