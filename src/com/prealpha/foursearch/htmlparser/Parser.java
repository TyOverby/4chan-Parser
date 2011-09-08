package com.prealpha.foursearch.htmlparser;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.prealpha.foursearch.objects.Comment;
import com.prealpha.foursearch.objects.CommentText;
import com.prealpha.foursearch.objects.Image;
import com.prealpha.foursearch.objects.Thread;

public class Parser {
	private static final Pattern sizePattern = Pattern.compile("(\\d*[0-9](|.\\d*[0-9]|,\\d*[0-9])? (KB|MB))");
	private static final Pattern dimPattern = Pattern.compile("(\\d*[0-9](x\\d*[0-9]|,\\d*[0-9]))");
	
	public static Thread parseThread(URL url) throws IOException {
		Thread thread = new Thread();
		
		Document doc = Jsoup.connect(url.toString()).get();
		
		Element container = doc.select("form[name=delform]").first();
		Elements tables = container.select("table");
		// remove invalid tables
		tables.remove(tables.last());
		
		for (Element table : tables) {
			thread.addComment(parseComment(table));
		}
		
		return thread;
	}
	
	public static Comment parseComment(Element table) {
		String posterName;
		String trip;
		String replyTitle;
		String email;
		String dateS;
		int postNumber;
		Image i = null;
		CommentText bodyText = null;
		
		//System.out.println(table.toString());
		
		// Name of the poster
		posterName = table.select("span.commentpostername").first().text();
		
		// Users trip
		try {
			trip = table.select("span.postertrip").first().text();
		} catch (NullPointerException npe) {
			trip = null;
		}
		
		// Reply title
		try {
			replyTitle = table.select("blockquote").first().text();
		} catch (NullPointerException npe) {
			replyTitle = null;
		}
		
		// User email
		try {
			email = table.select("a.linkmail").first().attr("href").replace("mailto:", "");
		} catch (NullPointerException npe) {
			email = null;
		}
		
		// DateString
		dateS = table.select("td.reply").first().ownText();
		
		// Post number
		postNumber = Integer.parseInt(table.select("td.reply").first().id());
		
		// Image
		if (!table.select("span.filesize").isEmpty()) {
			i = parseImage(table.select("img").first(), table.select("span.filesize").first());
		}
		
		// Body text
		if (!(table.select("blockquote").first().text().trim().length() == 0)) {
			Element blockquote = table.select("blockquote").first();
			bodyText = parseCommentText(blockquote);
		}
		
		return new Comment(posterName, trip, replyTitle, email, dateS, postNumber, i, bodyText);
	}
	
	public static Image parseImage(Element imgThumb, Element filesize) {
		String url;
		String fileName;
		String data;
		
		url = filesize.select("a[target=_blank]").first().attr("href");
		fileName = filesize.select("a[target=_blank]").first().text();
		data = filesize.text();
		
		return parseImageData(url, fileName, data);
	}
	
	public static Image parseImageData(String url, String fileName, String data) {
		// Match the file size
		Matcher sizePat = sizePattern.matcher(data);
		sizePat.find();
		String size = sizePat.group(1);
		// Match the dimensions
		Matcher dimPat = dimPattern.matcher(data);
		dimPat.find();
		String dim = dimPat.group(1);
		String[] dims = dim.split("x");
		
		return new Image(url, fileName, size, Integer.parseInt(dims[0]), Integer.parseInt(dims[1]));
	}
	
	public static CommentText parseCommentText(Element blockquote) {
		CommentText toReturn = new CommentText();
		String text = blockquote.html();
		String[] lines = text.split("\n");
		
		for (String s : lines) {
			s = s.trim();
			if (s.equals("<br />")){
				toReturn.addTextElement(new CommentText.BlankLine());
			}
			else if(s.startsWith("<font")){
				if(s.contains("quotelink")){
					toReturn.addTextElement(new CommentText.QuoteLink(extractInt(extractText(s).replace("&gt;&gt;",""))));
				}
				else if(s.contains("spoiler")){
					toReturn.addTextElement(new CommentText.Spoiler(extractText(s)));
				}
				else{
					toReturn.addTextElement(new CommentText.Quote(extractText(s).replace("&gt;","")));
				}
			}
			else{
				toReturn.addTextElement(new CommentText.Text(s.replace("<br />", "")));
			}
		}
		
		return toReturn;
	}
	
	private static int extractInt(String s){
		s = s.trim();
		String toReturn = "";
		
		for(int i=0;i<s.length();i++){
			if(Character.isDigit(s.charAt(i))){
				toReturn += s.charAt(i);
			}
			else{
				return Integer.parseInt(toReturn);
			}
		}
		return Integer.parseInt(toReturn);
	}
	
	private static String extractText(String line){
		String toReturn = "";
		boolean status = false;
		
		for(int i=0;i<line.length();i++){
			if(line.charAt(i)=='<'){
				status = false;
			}
			else if(line.charAt(i)=='>'){
				status = true;
			}else{
				if(status){
					toReturn += line.charAt(i);
				}
			}
		}
		
		return toReturn;
	}
}
