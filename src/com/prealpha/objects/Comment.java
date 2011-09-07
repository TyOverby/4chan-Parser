package com.prealpha.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Comment
{
	public final String name;
	public final String trip;
	public final String replyTitle;
	public final String email;
	public final Date timeStamp;
	public final int number;
	public final Image image;
	public final List<String> bodyText;

	public final SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/y(E)H:s");

	public Comment(String name, String trip,String replyTitle, String email, String sDate, int number, Image i,List<String> bodyText){
		this.name = name;
		this.trip = trip;
		this.replyTitle = replyTitle;
		this.email = email;
		this.number = number;
		this.image = i;
		this.bodyText = bodyText;
		try{
			System.out.println(this.timeStamp);
			this.timeStamp = dateFormat.parse(sDate.trim());
		}
		catch(ParseException pe){
			pe.printStackTrace();
			throw new IllegalArgumentException();
		}
	}	

	public String toString(){
		String toReturn = this.number+"\n"+this.name+"\n"+this.email+"\n"+this.trip+"\n"+this.replyTitle+"\n"+this.timeStamp.toString()+"\n";
		for(String s:bodyText){
			toReturn+="\t"+s+"\n";
		}
		return toReturn;
	}
}
