package com.prealpha.foursearch.objects;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.prealpha.foursearch.main.ThreadParser;

public class Page {
	private final List<Thread> threadList;
	
	public Page(List<Thread> threadList){
		this.threadList = threadList;
	}
	
	public List<Thread> getThreadList(){
		return this.threadList;	
	}
	
	public static Page parsePage(URL url,String prepend) throws IOException{
		List<Thread> threadList = new ArrayList<Thread>();
		
		Document doc = Jsoup.connect(url.toString()).get();
		
		Elements links = doc.select("a");
		
		for(Element link:links){
			if(link.text().trim().equalsIgnoreCase("reply")){
				try{
					System.out.println(link.attr("href"));
					threadList.add(ThreadParser.parseThread(new URL(prepend+link.attr("href"))));
				}
				catch(SocketTimeoutException ste){
					System.out.println("skipping");
					continue;
				}
			}
		}
		
		return new Page(threadList);
	}
}
