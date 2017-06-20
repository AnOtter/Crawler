package com.ai;
import com.ai.xml.XMLDocument;

public class App {

	public static void main(String[] args) {
		XMLDocument document=new XMLDocument("Books");
		document.addNode("Java", "English");
		document.addNode("C", "Chinese");
		document.addNode("Roby", "Japanese");
		document.save("D:\\Books.xml");
	}

}
