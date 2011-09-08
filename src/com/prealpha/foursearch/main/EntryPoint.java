package com.prealpha.foursearch.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.prealpha.foursearch.htmlparser.Parser;
import com.prealpha.foursearch.objects.Comment;
import com.prealpha.foursearch.objects.CommentText;
import com.prealpha.foursearch.objects.Thread;

public class EntryPoint
{

	public static void main(String[] args) throws MalformedURLException, IOException
	{
		Thread thread =  Parser.parseThread(new URL("http://localhost:8000/thread.html"));
		
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
			if(c.bodyText != null)
			{
				printT("==================");
				for(CommentText.TextElement s:c.bodyText.getTexts())
				{
					System.out.println("\t"+s.getClass().getName().split("\\$")[1]+": "+s.toString());
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
