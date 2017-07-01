package com.ai.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileOperator {
	public static void writeContent(String filePath,String content,Boolean append,Boolean replaceExistFile){
		   try {		   
			   File file=new File(filePath);
			   if((!replaceExistFile) && file.exists())
				   return;
			   createLocalFile(file);
			   FileOutputStream fileOutputStream=new FileOutputStream(file,append);
			   byte[] bytes=content.getBytes();
			   fileOutputStream.write(bytes);
			   fileOutputStream.close();  	
		} catch (Exception e) {
			e.printStackTrace();
		}
	   }
	   
	   public static String readContent(String filePath) {
		   try {
			FileInputStream fileInputStream=new FileInputStream(filePath);
			int fileLength=fileInputStream.available();
			byte[] bytes=new byte[fileLength];
			fileInputStream.read(bytes);
			fileInputStream.close();
			return new String(bytes);
		} catch (Exception e) {
			return "";
		}		
	}
	   
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
	      public static String getEncoding(byte[] bytes){
	   	 String encoding="";
	   	 if(bytes.length>3)
	   	 {
	   		 String byte0=String.format("%03d", bytes[0]);
	   		 String byte1=String.format("%03d", bytes[1]);
	   		 String byte2=String.format("%03d", bytes[2]);
	   		 
	   		 String header=byte0+byte1+byte2;
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
	   				encoding="UTF-8";
	   		}		 
	   	 }
	   	 return encoding;
	      }
}
