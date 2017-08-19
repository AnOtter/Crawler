package com.ai.demos;

import java.net.URL;

public class App {

	public static void main(String[] args) {		
		testURL("http://qzs.qq.com/");
	}
	
	public static void testURL(String urlAddress){
		try {
			System.out.println("URL:"+urlAddress);
			URL url=new URL(urlAddress);
			String filename=url.getFile();
			String authority=url.getAuthority();
			System.out.println("File:"+filename);			
			System.out.println("Authority:"+authority);
			System.out.println("");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
