package com.reno.jsoupTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test1 {

	public static void main(String[] args) throws IOException {
		String path = "F:/fileSpace/zhihu.txt";
		String tempstr = null;
		StringBuffer sb = new StringBuffer();
		File file = new File(path);
		InputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((tempstr = br.readLine()) != null)
			sb.append(tempstr);
		
		Document doc = Jsoup.parse(sb.toString());
		Elements Lists=doc.select("div.List-item");
		
		
		
		System.out.println(Lists.size());
		
		for(Element el:Lists){
			getInfoFromElement(el);
			
		}
		
		
	}
	
	
	static void getInfoFromElement(Element ele){
		System.out.println(ele.select("a.UserLink-link").get(0));
		System.out.println("\n\n\n");
	}

}
