package com.ai.crawl;

import static com.ai.util.Log.*;
import static com.ai.crawl.GlobalVariants.*;

public class App {
		public static void main(String[] args) {
		try {
			//TODO 使用spring自动将初始目录加载进来
			
			logDebug("main begin");
			String localSaveDir = "";			
			if (args.length == 1)
				localSaveDir = args[0];
			if (!localSaveDir.equals("")) {
				localSaveDirectory=localSaveDir;
				crawl();
			}
			logDebug("main end");
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void crawl() {
		logInfo("LocalSaveDirectory:" + localSaveDirectory);
		if (localSaveDirectory.equals("")) {
			logError("Illegal input parameter");
			return;
		}
		FetchManager fetchManager = FetchManager.getInstance();
		fetchManager.run();
	}

}
