package com.prealpha.objects;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Image
{
	public final URL url;
	public final String fileName;
	public final String size;
	public final int width;
	public final int height;
	public final boolean isImage;
	
	private static final Pattern sizePattern = Pattern.compile("(\\d*[0-9](|.\\d*[0-9]|,\\d*[0-9])? (KB|MB))");
	private static final Pattern dimPattern = Pattern.compile("(\\d*[0-9](x\\d*[0-9]|,\\d*[0-9]))");
	
	public Image(String url, String fileName, String size,int width, int height) throws MalformedURLException
	{
		this.url = new URL(url);
		this.fileName = fileName;
		this.size = size;
		this.width = width;
		this.height = height;
		this.isImage = true;
	}
	
	public Image()
	{
		this.url = null;
		this.fileName = null;
		this.size = null;
		this.width = 0;
		this.height = 0;
		this.isImage = false;
		
	}
	
	public String toString()
	{
		return "\turl: " + this.url+"\n\tfileName: "+this.fileName+"\n\tsize:"+this.size+"\n\tdims: "+this.width+"x"+this.height;
	}
	
	public static Image imageParser(String url,String fileName, String data) throws NumberFormatException, MalformedURLException
	{	
		//Match the file size
		Matcher sizePat = sizePattern.matcher(data);
		sizePat.find();
		String size = sizePat.group(1);
		//Match the dimensions
		Matcher dimPat = dimPattern.matcher(data);
		dimPat.find();
		String dim = dimPat.group(1);
		String[] dims = dim.split("x");
		
		
		return new Image(url,fileName, size,Integer.parseInt(dims[0]),Integer.parseInt(dims[1]));
	}
}
