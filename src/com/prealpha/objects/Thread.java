package com.prealpha.objects;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;

import com.prealpha.htmlparser.Helper;

public class Thread
{
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	
	public ArrayList<Comment> getComments()
	{
		return this.comments;
	}
	
	public void addComment(Comment c)
	{
		this.comments.add(c);
	}
	
	
	public static Thread parseThread(URL page) throws IOException
	{
		Thread thread = new Thread();
		Document dom = Helper.parse(page);
		
		ArrayList<Element> comments = Helper.getMatches(dom, "td", "class", "reply");
		
		thread.addComment(Comment.commentParser(Helper.getMatches(dom, "form", "name", "delform").get(0)));
		
		for(Element element:comments)
		{
			try
			{
				Comment newComment = Comment.commentParser(element);
				
				//System.out.println(newComment.bodyText);
				
				thread.addComment(newComment);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return thread;
	}
}
