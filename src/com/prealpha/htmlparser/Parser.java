package com.prealpha.htmlparser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.prealpha.objects.Comment;
import com.prealpha.objects.Image;
import com.prealpha.objects.Thread;

public class Parser {
	private static final Pattern sizePattern = Pattern.compile("(\\d*[0-9](|.\\d*[0-9]|,\\d*[0-9])? (KB|MB))");
	private static final Pattern dimPattern = Pattern.compile("(\\d*[0-9](x\\d*[0-9]|,\\d*[0-9]))");
	
	public static Thread parseThread(URL url) throws IOException{
		Thread thread = new Thread();
		
		Document doc = Jsoup.connect(url.toString()).get();

		Element container = doc.select("form[name=delform]").first();
		Elements tables = container.select("table");
		//remove invalid tables
		tables.remove(tables.last());
		
		for(Element table:tables){
			thread.addComment(parseComment(table));
		}
		
		return thread;
	}
	
	public static Comment parseComment(Element table){
		String posterName;
		String trip;
		String replyTitle;
		String email;
		String dateS;
		int postNumber;
		Image i = null;
		List<String> bodyText = null;
		
		System.out.println(table.toString());
		
		// Name of the poster
		posterName = table.select("span.commentpostername").first().text();
		
		// Users trip
		try{
			trip = table.select("span.postertrip").first().text();
		}catch(NullPointerException npe){
			trip = null;
		}
		
		// Reply title
		try{
			replyTitle = table.select("blockquote").first().text();
		}catch(NullPointerException npe){
			replyTitle = null;
		}
		
		// User email
		try{
			email = table.select("a.linkmail").first().attr("href").replace("mailto:", "");
		}catch(NullPointerException npe){
			email = null;
		}
		
		// DateString
		dateS = table.select("td.reply").first().ownText();
		System.err.println("===="+dateS);
		
		// Post number
		postNumber = Integer.parseInt(table.select("td.reply").first().id());
		
		// Image
		if(!table.select("span.filesize").isEmpty()){
			i = parseImage(table.select("img").first(),table.select("span.filesize").first());
		}
		
		// Body text
		if(!(table.select("blockquote").first().text().trim().length()==0)){
			Element textElement = table.select("blockquote").first();
			String text = textElement.html();
			System.err.println(text);
		}
		
		return new Comment(posterName,trip,replyTitle,email,dateS,postNumber,i,bodyText);
	}
	
	public static Image parseImage(Element imgThumb,Element filesize){
		String url;
		String fileName;
		String data;
		
		url = filesize.select("a[target=_blank]").first().attr("href");
		fileName = filesize.select("a[target=_blank]").first().text();
		data = filesize.text();
		
		return parseImageData(url,fileName,data);
	}
	
	public static Image parseImageData(String url,String fileName, String data)
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
