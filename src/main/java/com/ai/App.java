package com.ai;
import com.ai.xml.XMLDocument;

public class App {

	public static void main(String[] args) {
//		XMLDocument document=new XMLDocument("Books");
//		document.addNode("Java", "English");
//		document.addNode("C", "Chinese");
//		document.addNode("Roby", "Japanese");
//		Element newNode=document.createNode("BookStore");
//		newNode.setText("XinHua");
//		newNode.addAttribute("Address", "深圳市南山区后海路1001号");
//		document.appendNode(newNode);
//		document.save("D:\\Books.xml");
		try {
			XMLDocument document=new XMLDocument();
			document.loadFromFile("D:\\Book.xml");
			document.addNode("NewNode2", "jiangxu");
			document.save("d:\\book.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
