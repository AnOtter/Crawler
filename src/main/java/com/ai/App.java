package com.ai;
import com.ai.xml.XMLDocument;

public class App {

	public static void main(String[] args) {
		try {
			XMLDocument document=new XMLDocument();
			document.loadFromFile("D:\\Book.xml");
			document.clearNode();
			document.addNode("NewNode", "新的结点");
			document.addNode("NewNode", "如何学习java");
			document.saveToFile("d:\\book.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
