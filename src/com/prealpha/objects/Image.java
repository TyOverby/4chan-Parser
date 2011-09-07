package com.prealpha.objects;

public class Image
{
	public final String url;
	public final String fileName;
	public final String size;
	public final int width;
	public final int height;
	public final boolean isImage;
	
	public Image(String url, String fileName, String size,int width, int height)
	{
		this.url = url;
		this.fileName = fileName;
		this.size = size;
		this.width = width;
		this.height = height;
		this.isImage = true;
	}
	
	public String toString()
	{
		return "\turl: " + this.url+"\n\tfileName: "+this.fileName+"\n\tsize:"+this.size+"\n\tdims: "+this.width+"x"+this.height;
	}
}
