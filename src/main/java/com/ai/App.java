package com.ai;

import com.ai.xml.XMLDocument;
import com.ai.util.Log;
import com.ai.crawl.FetchManager;;

public class App {	
	private static Log log =Log.getLog(App.class.getName());
	
	public static void main(String[] args) {
		testXML();
		try {			
			log.debug("main begin");
			String localSaveDir="";
			if(args.length==1)
				localSaveDir=args[0];
			if(!localSaveDir.equals(""))
			  crawl(localSaveDir);
			log.debug("main end");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}	
	}
	
	public static void testXML(){
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
		
	public static void crawl(String localSaveDirectory){
			log.info("LocalSaveDirectory:" + localSaveDirectory);
			if (localSaveDirectory.equals("")) {
				log.error("Illegal input parameter");
				return;
			}
			FetchManager fetchManager = FetchManager.getInstance();
			fetchManager.setLocalSaveDirectory(localSaveDirectory);
			fetchManager.run();
		}

}
