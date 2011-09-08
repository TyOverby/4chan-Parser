package com.prealpha.foursearch.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.prealpha.foursearch.objects.Comment;
import com.prealpha.foursearch.objects.CommentText;
import com.prealpha.foursearch.objects.CommentText.TextElement;
import com.prealpha.foursearch.objects.Page;
import com.prealpha.foursearch.objects.Thread;

public class EntryPoint
{

	public static void main(String[] args) throws MalformedURLException, IOException
	{
		//Thread thread =  Parser.parseThread(new URL("http://localhost:8000/thread.html"));
		//Thread thread =  ThreadParser.parseThread(new URL("http://boards.4chan.org/a/res/54417480"));
		Page page = Page.parsePage(new URL("http://boards.4chan.org/a/"),"http://boards.4chan.org/a/");
		
		printPage(page);
		
		//Json.threadToJson(thread, System.out);
		//Thread.parseThread(new URL("http://boards.4chan.org/a/res/47148171"));
	}
	
	public static void printPage(Page page){
		for(Thread t:page.getThreadList()){
			System.out.println("==================");
			for(TextElement te: t.getComments().get(0).bodyText.getTexts()){
				System.out.println(te.toString());
			}
		}
	}
	
	public static void printThread(Thread thread){
		for(Comment c: thread.getComments())
		{
			print(c.number);
			if(!c.name.equals("Anonymous"))
			{
				printT("Name: "+c.name);
			}
			if(c.trip != null)
			{
				printT("Trip: "+c.trip);
			}
			printT("Date: "+c.timeStamp.toString());
			if(c.bodyText != null)
			{
				printT("==================");
				for(CommentText.TextElement s:c.bodyText.getTexts())
				{
					printT(s.getClass().getName().split("\\$")[1]+": "+s.toString(),2);
				}
			}
			if(c.image!=null){
				printT("++++++++++++++++");
				printT(c.image.fileName,2);
				printT(c.image.thumbUrl,2);
				printT(c.image.url,2);
				printT("("+c.image.width+","+c.image.height+")",2);
				printT(c.image.size,2);
				
			}
		}
	}
	
	public static void print(Object o)
	{
		System.out.println(o.toString());
	}
	public static void printT(Object o)
	{
		printT(o,1);
	}
	public static void printT(Object o, int num){
		String toPrint = o.toString();
		for(int i=0;i<num;i++){
			toPrint = "\t"+toPrint;
		}
		
		System.out.println(toPrint);
	}
}
