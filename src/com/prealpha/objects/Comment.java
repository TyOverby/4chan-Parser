package com.prealpha.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jdom.Element;

import com.prealpha.htmlparser.Helper;

public class Comment
{
	public final String name;
	public final String trip;
	public final String replyTitle;
	public final String email;
	public final Date timeStamp;
	public final int number;
	public final Image image;
	public final ArrayList<String> bodyText;
	
	public final SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/y(E)H:s");
	
	public Comment(String name, String trip,String replyTitle, String email, String sDate, int number, Image i,ArrayList<String> bodyText) throws ParseException
	{
		this.name = name;
		this.trip = trip;
		this.replyTitle = replyTitle;
		this.email = email;
		this.number = number;
		this.image = i;
		this.bodyText = bodyText;
		this.timeStamp = dateFormat.parse(sDate.trim());
	}	
	
	public static Comment commentParser(Element element) throws ParseException
	{
		//gets the name of the user.
		Element nameThing = Helper.getMatches(element, "span", "class", "commentpostername").get(0);
		String name = nameThing.getValue().trim();
				
		//gets the email entered
		String email = null;
		try
		{
			email = Helper.getMatches(element, "a", "class", "linkmail").get(0).getAttributeValue("href").replace("mailto:", "");
		}
		catch(Exception e){/*Do nothing, email is already null*/}	
		
		//tries to get the trip, but if it doesn't work, it defaults to null
		String trip = null;
			try 
			{
				trip = Helper.getMatches(element, "span", "class", "postertrip").get(0).getTextNormalize();
			}
			catch(Exception e){/*do nothing, trip is already set to null...*/}
			
		//gets the title if it has one
		String replyTitle = null;
			try
			{
				replyTitle = Helper.getMatches(element, "span", "class", "replytitle").get(0).getTextNormalize();
			}
			catch(Exception e){/*do nothing, replyTitle is already set to null...*/}
			
		//get the date posted
		String date = element.getTextNormalize().trim();
		
		//get the number of the post
		int number;
		//try to get the number from a normal comment
		try 
		{
			number = Integer.parseInt(element.getAttributeValue("id"));
		}
		//if it is the OP
		catch(Exception e)
		{
			Element numElement = Helper.getMatches(element, "a", null, null).get(2);
			number = Integer.parseInt(numElement.getAttributeValue("name"));
		}
		
		//try to get the image info if it exists
		Image image;
		try
		{
			Element fileInfo = Helper.getMatches(element, "span", "class", "filesize").get(0); 
			String url = Helper.getMatches(fileInfo, "a", null, null).get(0).getAttributeValue("href");
			String fileName = Helper.getMatches(fileInfo, "span", null, null).get(0).getAttributeValue("title");
			String data = fileInfo.getText();
			
			image = Image.imageParser(url,fileName,data);
		}
		catch(Exception e)
		{
			image = new Image();
		}
		
		//get the body text
		ArrayList<String> bodyText = null;
		try
		{
			bodyText = Helper.getFullText(Helper.getMatches(element, "blockquote", null, null).get(0));
			
		}
		catch(Exception e) {/*Do nothing, it is already null*/}
		
		//System.out.println(fileInfo);
		
		return new Comment(name,trip,replyTitle, email, date, number, image, bodyText);
	}
}
