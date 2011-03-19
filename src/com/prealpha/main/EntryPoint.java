package com.prealpha.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import com.prealpha.objects.Comment;
import com.prealpha.objects.Thread;;

public class EntryPoint
{

	public static void main(String[] args) throws MalformedURLException, IOException
	{
		Thread thread =  Thread.parseThread(new URL("file://localhost/Users/ty/Documents/workspace/4chan%20parser/Testing%20HTML/thread.html"));
		
		for(Comment c: thread.getComments())
		{
			print(c.number);
			printT("Date: "+c.sDate);
			if(!c.name.equals("Anonymous"))
			{
				printT("Name: "+c.name);
			}
			if(c.trip != null)
			{
				printT("Trip: "+c.trip);
			}
			if(c.bodyText != null)
			{
				printT("==================");
				for(String s:c.bodyText)
				{
					System.out.println("\t"+s);
				}
			}
		}
		
		//Thread.parseThread(new URL("http://boards.4chan.org/a/res/47148171"));
	}
	public static void print(Object o)
	{
		System.out.println(o.toString());
	}
	public static void printT(Object o)
	{
		System.out.println("\t"+o.toString());
	}
}
