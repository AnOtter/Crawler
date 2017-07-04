package com.ai.crawl;

import com.ai.util.Log;
import static com.ai.crawl.GlobalVariants.*;

public class App {
	private static Log log = Log.getLog(App.class.getName());

	public static void main(String[] args) {
		try {
			log.debug("main begin");
			String localSaveDir = "";
			
			if (args.length == 1)
				localSaveDir = args[0];
			if (!localSaveDir.equals("")) {
				localSaveDirectory=localSaveDir;
				crawl();
			}
			log.debug("main end");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	public static void crawl() {
		log.info("LocalSaveDirectory:" + localSaveDirectory);
		if (localSaveDirectory.equals("")) {
			log.error("Illegal input parameter");
			return;
		}
		FetchManager fetchManager = FetchManager.getInstance();
		fetchManager.run();
	}

}
