package com.ai.crawl;

import com.ai.util.Log;

public class App {
	private static Log log =Log.getLog(App.class.getName());
	
	public static void main(String[] args) {
		try {			
			log.debug("main begin");
			String localSaveDir="d:\\webpages";
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
