package com.prealpha.objects;

import java.util.ArrayList;

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
}
