package com.ai.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class FileOperator {
	/**
	 * @描述 创建对应的本地文件
	 * @param localFile
	 * @throws Exception
	 */
	public static void createLocalFile(File localFile) throws Exception {
		if (!localFile.exists()) {
			File parent = localFile.getParentFile();
			if (!parent.exists())
				parent.mkdirs();
			if (parent.exists()) {
				localFile.createNewFile();
			}
		}
	}

	/**
	 * @描述 根据字节数组的前三字节值计算编码格式
	 * @param bytes
	 * @return Encoding of the input bytes
	 * @author OTTER
	 * @since 20170430
	 */
	public static String getEncoding(byte[] bytes) {
		String encoding = "";
		if (bytes.length > 3) {
			String byte0 = String.format("%03d", bytes[0]);
			String byte1 = String.format("%03d", bytes[1]);
			String byte2 = String.format("%03d", bytes[2]);

			String header = byte0 + byte1 + byte2;
			switch (header) {
			case "010013010":
			case "032032060":
			case "010060033":
			case "010032032":
			case "013010013":
			case "009060033":
			case "010010032":
			case "010010060":
			case "009032032":
			case "010060104":
			case "060063120":
			case "060104116":
			case "010010010":
			case "-17-69-65":
				encoding = "UTF-8";
			}
		}
		return encoding;
	}

	public static String readContent(String filePath,Charset charset) {
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			int fileLength = fileInputStream.available();
			byte[] bytes = new byte[fileLength];
			fileInputStream.read(bytes);
			fileInputStream.close();
			return new String(bytes,charset);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String readContent(String filePath){
		return readContent(filePath, Charset.defaultCharset());
	}

	public static void writeContent(String filePath, String content, Boolean append, Boolean replaceExistFile) {
		try {
			File file = new File(filePath);
			if ((!replaceExistFile) && file.exists())
				return;
			createLocalFile(file);
			FileOutputStream fileOutputStream = new FileOutputStream(file, append);
			byte[] bytes = content.getBytes();
			fileOutputStream.write(bytes);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @描述 获取网页地址对应的本地路径
	 * @param pageURL
	 *            页面url
	 * @param localSaveDirectory
	 *            本地保存目录
	 * @return localFile 本地保存文件路径
	 * @author OTTER
	 * @since 20170430
	 */
	public static String getLocalSaveFile(String localSaveDirectory, String pageURL){
		try {
			if(localSaveDirectory.equals(""))
				return "";
			URL url = new URL(pageURL);
			String authority = url.getAuthority();
			String[] dir = authority.split("\\.");
			String domain = "";
			if (dir.length > 1) {
				for (int i = dir.length - 1; i >= 0; i--) {
					domain += dir[i] + "/";
				}
			}
			if (domain.equals("")) {
				domain = authority;
			}
			String path = url.getPath();
			if (path.equals("") || path.endsWith("/"))
				path += "index.html";
			return Paths.get(localSaveDirectory, domain, path).toFile().toString();
		} catch (Exception e) {
			return "";
		}
		
	}

}
