package com.ai.crawl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static com.ai.util.FileOperator.*;
import static com.ai.util.DateTime.*;
import static com.ai.crawl.GlobalVariants.*; 
import com.ai.util.Log;
import com.ai.xml.XMLDocument;

/**
 * 描述 获取管理类，执行页面获取任务
 * 
 * @author OTTER
 * @version 1.0
 * @since 20170430
 */
public class FetchManager {
	private static Log log = Log.getLog(FetchManager.class.getName());

	private LinkedList<String> fetchList;
	private TreeMap<String, Date> fetchedList;

	private List<String> allowList;
	private List<String> urlList;

	private int maxThreadCount;

	private XMLDocument doc;

	private static FetchManager fetchManager;

	private FetchManager() {
		fetchList = new LinkedList<>();
		fetchedList = new TreeMap<>();
		allowList = new LinkedList<String>();
		urlList = new LinkedList<>();
		maxThreadCount = 50;
		doc = new XMLDocument();
		loadAllowList();
		loadSeedURLList();
		try {
			File xmlFile = new File(localSaveDirectory + "\\Crawl.xml");
			if (xmlFile.exists())
				doc.loadFromFile(localSaveDirectory + "\\Crawl.xml");
		} catch (Exception e) {

		}
	}

	private void loadAllowList() {
		log.debug("loadAllowList Begin");
		String fileContent = readContent(localSaveDirectory + "\\config\\allow.list");
		if (!fileContent.equals("")) {
			String[] urlList = fileContent.split("\n");
			for (String url : urlList) {
				url = url.trim();
				allowList.add(url);
			}
		}
		log.debug("allowList count:" + allowList.size());
	}

	private void loadSeedURLList() {
		log.debug("loadSeedURLList Begin");
		String fileContent = readContent(localSaveDirectory + "\\config\\urls.list");
		if (!fileContent.equals("")) {
			String[] liStrings = fileContent.split("\n");
			for (String string : liStrings) {
				urlList.add(string);
			}
		}
		log.debug("loadSeedURLList count:" + urlList.size());
	}

	public static FetchManager getInstance() {
		if (fetchManager == null)
			fetchManager = new FetchManager();
		return fetchManager;
	}

	/**
	 * @描述 执行获取操作
	 * @since 20170430
	 * @author OTTER
	 */
	public void run() {
		try {
			log.debug("FetchManager run begin");
			if (!localSaveDirectory.equals("")) {
				InitializeFetchList();

				int fetchTimes = 0;
				ExecutorService executor = Executors.newFixedThreadPool(maxThreadCount);
				List<Future<?>> futures = new ArrayList<>();
				while (fetchList.size() > 0) {
					for (int i = 0; i < futures.size(); i++) {
						if (futures.get(i).isDone())
							futures.remove(i);
					}
					if (futures.size() < maxThreadCount + 5) {
						String nextFetchURL = fetchList.pollFirst();
						Fetcher fetcher = new Fetcher(fetchList, fetchedList, allowList);
						fetcher.setFetchURL(nextFetchURL);
						fetcher.setDocment(doc);
						Future<?> future = executor.submit(fetcher);
						futures.add(future);
					}
					fetchTimes++;
					if ((fetchTimes % 1000) == 200) {
						saveFetchList();
						doc.saveToFile(localSaveDirectory + "\\Crawl" + today() + ".xml");
						fetchTimes = 0;
					}
				}
				try {
					Thread.currentThread();
					Thread.sleep(5000);
				} catch (Exception e) {

				}
				saveFetchList();
				log.debug("FetchManager run end");
			} else {
				log.error("fetchURL or localSaveDirectory not given");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("FetchManager run Error:" + e.getMessage());
		}
	}

	/**
	 * @描述 从存储目录的列表存档文件初始化获取别表 并将fetchUrl加入获取列表
	 * @author OTTER
	 * @since 20170430
	 */
	private void InitializeFetchList() {
		try {
			File fetchingListFile = new File(localSaveDirectory + "\\Fetch.List");
			if (fetchingListFile.exists()) {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fetchingListFile));
				fetchList = (LinkedList<String>) objectInputStream.readObject();
				objectInputStream.close();
			}
			File fetchedListFile = new File(localSaveDirectory + "\\Fetched.List");
			if (fetchedListFile.exists()) {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fetchedListFile));
				fetchedList = (TreeMap<String, Date>) objectInputStream.readObject();
				objectInputStream.close();
			}
			for (String url : urlList) {
				if (!fetchList.contains(url))
					fetchList.add(url);
			}
			log.debug("InitializeFetchList end");
			log.debug("fetchList Count:" + fetchList.size());
			log.debug("fetchedList Count:" + fetchedList.size());
		} catch (Exception e) {
			log.error("FetchManager InitializeFetchList Error:" + e.getMessage());
		}
	}

	/**
	 * @描述 将获取列表保存到本地文件
	 * @author OTTER
	 * @since 20170430
	 */
	private void saveFetchList() {
		try {
			File fetchingListFile = new File(localSaveDirectory + "\\Fetch.List");
			if (fetchingListFile.exists()) {
				fetchingListFile.delete();
			}

			ObjectOutputStream saveStream = null;
			synchronized (fetchList) {
				saveStream = new ObjectOutputStream(new FileOutputStream(fetchingListFile));
				saveStream.writeObject(fetchList);
				saveStream.close();
			}

			File fetchedListFile = new File(localSaveDirectory + "\\Fetched.List");
			if (fetchedListFile.exists()) {
				fetchedListFile.delete();
			}
			synchronized (fetchedList) {
				saveStream = new ObjectOutputStream(new FileOutputStream(fetchedListFile));
				saveStream.writeObject(fetchedList);
				saveStream.close();
			}

		} catch (Exception e) {
			log.error("FetchManage saveFetchList error:" + e.getMessage());
		}
	}

	protected void finalize() {
		saveFetchList();
	}

	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}
}
