package com.ai.oldcrawler;

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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import static com.ai.util.FileOperator.*;
import static com.ai.util.DateTime.*;
import static com.ai.util.Log.*;
import com.ai.xml.XMLDocument;

/**
 * 描述 获取管理类，执行页面获取任务
 * 
 * @author OTTER
 * @version 1.0
 * @since 20170430
 */
@Component
public class FetchManager implements ApplicationContextAware {
	private LinkedList<String> fetchList;
	private TreeMap<String, Date> fetchedList;

	private List<String> allowList;
	private List<String> urlList;

	private XMLDocument doc;
	@Resource
	private GlobalVariants globalVariants;

	private static FetchManager fetchManager;

	private FetchManager() {
		
	}

	private void loadAllowList() {
		logDebug("loadAllowList Begin");
		String fileContent = readContent(globalVariants.getLocalSaveDirectory() + "\\config\\allow.list");
		if (!fileContent.equals("")) {
			String[] urlList = fileContent.split("\n");
			for (String url : urlList) {
				url = url.trim();
				allowList.add(url);
			}
		}
		logInfo("AllowList Count:" + allowList.size());
	}

	private void loadSeedURLList() {
		logDebug("loadSeedURLList Begin");
		String fileContent = readContent(globalVariants.getLocalSaveDirectory() + "\\config\\urls.list");
		if (!fileContent.equals("")) {
			String[] liStrings = fileContent.split("\n");
			for (String string : liStrings) {
				urlList.add(string);
			}
		}
		logInfo("SeedURLList Count:" + urlList.size());
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
	@PostConstruct
	public void run() {
		try {
			logDebug("FetchManager run begin");
			if (!globalVariants.getLocalSaveDirectory().equals("")) {
				InitializeFetchList();

				int fetchTimes = 0;
				ExecutorService executor = Executors.newFixedThreadPool(globalVariants.getMaxThreadCount());
				List<Future<?>> futures = new ArrayList<>();
				while (fetchList.size() > 0) {
					for (int i = 0; i < futures.size(); i++) {
						if (futures.get(i).isDone())
							futures.remove(i);
					}
					if (futures.size() < globalVariants.getMaxThreadCount() + 5) {
						String nextFetchURL = fetchList.pollFirst();
						Fetcher fetcher = new Fetcher(fetchList, fetchedList, allowList,globalVariants.getLocalSaveDirectory());
						fetcher.setFetchURL(nextFetchURL);
						fetcher.setDocment(doc);
						Future<?> future = executor.submit(fetcher);
						futures.add(future);
					}
					fetchTimes++;
					if ((fetchTimes % 1000) == 200) {
						saveFetchList();
						doc.saveToFile(globalVariants.getLocalSaveDirectory() + "\\Crawl" + today() + ".html");
						fetchTimes = 0;
					}
				}
				try {
					Thread.currentThread();
					Thread.sleep(5000);
				} catch (Exception e) {

				}
				saveFetchList();
				logDebug("FetchManager run end");
			} else {
				logError("fetchURL or localSaveDirectory not given");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logError("FetchManager run Error:" + e.getMessage());
		}
	}

	/**
	 * @描述 从存储目录的列表存档文件初始化获取别表 并将fetchUrl加入获取列表
	 * @author OTTER
	 * @since 20170430
	 */
	@SuppressWarnings("unchecked")
	private void InitializeFetchList() {
		try {
			File fetchingListFile = new File(globalVariants.getLocalSaveDirectory() + "\\Fetch.List");
			if (fetchingListFile.exists()) {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fetchingListFile));
				fetchList = (LinkedList<String>) objectInputStream.readObject();
				objectInputStream.close();
			}
			File fetchedListFile = new File(globalVariants.getLocalSaveDirectory() + "\\Fetched.List");
			if (fetchedListFile.exists()) {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fetchedListFile));
				fetchedList = (TreeMap<String, Date>) objectInputStream.readObject();
				objectInputStream.close();
			}
			for (String url : urlList) {
				if (!fetchList.contains(url))
					fetchList.add(url);
			}
			logDebug("InitializeFetchList end");
			logInfo("fetchList Count:" + fetchList.size());
			logInfo("fetchedList Count:" + fetchedList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @描述 将获取列表保存到本地文件
	 * @author OTTER
	 * @since 20170430
	 */
	private void saveFetchList() {
		try {
			File fetchingListFile = new File(globalVariants.getLocalSaveDirectory() + "\\Fetch.List");
			if (fetchingListFile.exists()) {
				fetchingListFile.delete();
			}

			ObjectOutputStream saveStream = null;
			synchronized (fetchList) {
				saveStream = new ObjectOutputStream(new FileOutputStream(fetchingListFile));
				saveStream.writeObject(fetchList);
				saveStream.close();
			}

			File fetchedListFile = new File(globalVariants.getLocalSaveDirectory() + "\\Fetched.List");
			if (fetchedListFile.exists()) {
				fetchedListFile.delete();
			}
			synchronized (fetchedList) {
				saveStream = new ObjectOutputStream(new FileOutputStream(fetchedListFile));
				saveStream.writeObject(fetchedList);
				saveStream.close();
			}
		} catch (Exception e) {
			logError("FetchManage saveFetchList error:" + e.getMessage());
		}
	}

	protected void finalize() {
		saveFetchList();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		fetchList = new LinkedList<>();
		fetchedList = new TreeMap<>();
		allowList = new LinkedList<String>();
		urlList = new LinkedList<>();
		doc = new XMLDocument("html");
		loadAllowList();
		loadSeedURLList();
		try {
			File xmlFile = new File(globalVariants.getLocalSaveDirectory() + "\\Crawl" + today() + ".html");
			if (xmlFile.exists())
				doc.loadFromFile(globalVariants.getLocalSaveDirectory() +  "\\Crawl" + today() + ".html");
		} catch (Exception e) {

		}		
	}
}
