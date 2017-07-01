package com.ai.crawl;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.ai.util.FileOperator.*;

/**
 * @描述 解析获取的html文件
 * @author OTTER
 * @since 201704430
 */
public class Parser {
	private String filePath;
	private String orignalURL;
	private String fileContent;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Parser(String filePath) {
		this.filePath = filePath;
		this.orignalURL = "";
	}

	/**
	 * @描述 获取页面中包含相对地址在内的链接列表
	 * @return List of subLink
	 * @author OTTER
	 * @since 20170430
	 */
	public List<String> getHTMLURLs() throws Exception {
		List<String> htmlList = new LinkedList<>();
		String matchPattern = "(i?)https?://[-a-zA-Z0-9_+/?%=&#\\.]+(html|htm)";
		htmlList = getMatchResult(matchPattern);
		if (!orignalURL.equals("")) {
			URL url = new URL(orignalURL);
			String protocol = url.getProtocol();
			String authority = url.getAuthority();
			int port = url.getPort();

			List<String> innerHTMLLinks = getInnerHTML();
			for (String string : innerHTMLLinks) {
				String innerLink = string.substring(6);
				if (!innerLink.startsWith("/"))
					innerLink = "/" + innerLink;
				if (port == -1)
					htmlList.add(protocol + "://" + authority + innerLink);
				else
					htmlList.add(protocol + "://" + authority + ":" + String.valueOf(port) + innerLink);
			}
		}
		return htmlList;
	}

	public String getOrignalURL() {
		return orignalURL;
	}

	public void setOrignalURL(String orignalURL) {
		this.orignalURL = orignalURL;
	}

	/**
	 * @描述 获取页面中的内联地址
	 * @return list of inner links
	 * @since 20170430
	 * @author OTTER
	 */
	private List<String> getInnerHTML() {
		String matchPattern = "(i?)href=(\"|\')[-a-zA-Z0-9_+/?%=&#\\.]+(/|html|htm)";
		return getMatchResult(matchPattern);
	}

	public List<String> getImageURLs() {
		String matchPattern = "(i?)https?://[-a-zA-Z0-9_+/?%=&#\\.]+\\.(jpg|gif|png|bmp)";
		return getMatchResult(matchPattern);
	}

	/**
	 * @描述 根据给定的匹配模式返回符合正则表达式的内容列表
	 * @param pattern
	 * @return List of matched String
	 * @author OTTER
	 * @since 2170430
	 */
	private List<String> getMatchResult(String pattern) {
		List<String> urlList = new LinkedList<>();
		if (fileContent == null)
			fileContent = readFile();
		if (fileContent != null) {
			Pattern matchPattern = Pattern.compile(pattern);
			Matcher matcher = matchPattern.matcher(fileContent);
			while (matcher.find()) {
				urlList.add(matcher.group());
			}
		}
		return urlList;
	}

	private List<String> getTitles() {
		String matchPattern = "(i?)<title.+title>";
		return getMatchResult(matchPattern);
	}

	public String getPageTitle() {
		List<String> titles = getTitles();
		if (titles.size() > 0) {
			String pageTitle = titles.get(0);
			int gtIndex = pageTitle.indexOf(">");
			int ltIndex = pageTitle.lastIndexOf("<");
			if (ltIndex > gtIndex)
				return pageTitle.substring(gtIndex + 1, ltIndex);
		}
		return "";
	}

	/**
	 * @描述 获取文件内容
	 * @return content string of the file
	 * @author OTTER
	 * @since 20170430
	 */
	private String readFile() {
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				FileInputStream fileInputStream = new FileInputStream(file);
				int fileLength = fileInputStream.available();
				if (fileLength > 0) {
					byte[] bytes = new byte[fileLength];
					fileInputStream.read(bytes);
					fileInputStream.close();
					if (bytes.length > 4) {
						if (getEncoding(bytes).equals("UTF-8"))
							return new String(bytes, Charset.forName("UTF-8"));
						else
							return new String(bytes);
					}
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
