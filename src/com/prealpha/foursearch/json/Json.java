package com.prealpha.foursearch.json;

import java.io.PrintStream;

import com.prealpha.foursearch.objects.Comment;
import com.prealpha.foursearch.objects.CommentText;
import com.prealpha.foursearch.objects.Thread;


public class Json {
	public static boolean threadToJson(Thread thread, PrintStream writer){
		Printer printer = new Printer(writer);
		
		try{
			printer.println("{", 0);
				printer.println("\"comment\":", 1);
				printer.println("[",1);
				for(Comment c:thread.getComments()){
					printer.println("{", 2);
						printer.println("\"number\": "+c.number+",", 3);
						if(c.name != null)
						{
						printer.println("\"name\": \""+c.name+"\",", 3);
						}
						if(c.trip != null)
						{
						printer.println("\"trip\": \""+c.trip+"\",",3);
						}
						if(c.email != null)
						{
						printer.println("\"email\": \""+c.email+"\",", 3);
						}
						if(c.image!=null)
						{
						printer.println("\"image\":",3);
						printer.println("{", 3);
							printer.println("\"name\": \""+c.image.fileName+"\",", 4);
							printer.println("\"url\": \""+c.image.url+"\",", 4);
							printer.println("\"thumb\": \""+c.image.thumbUrl+"\",",4);
							printer.println("\"size\": \""+c.image.size+"\",", 4);
							printer.println("\"width\": "+c.image.width+",", 4);
							printer.println("\"height\": "+c.image.height, 4);
						String comma = (c.bodyText != null && c.bodyText.getTexts().size()>0) ? ",":"";
						printer.println("}"+comma, 3);
						}
						if(c.bodyText != null && c.bodyText.getTexts().size()>0){
							printer.println("\"text\":", 3);
							printer.println("[", 3);
							for(CommentText.TextElement te: c.bodyText.getTexts()){
								String comma = (c.bodyText.getTexts().indexOf(te)!=c.bodyText.getTexts().size()-1) ? ",":"";
								String data = "";
								if(te instanceof CommentText.Quote||te instanceof CommentText.Spoiler||te instanceof CommentText.QuoteLink||te instanceof CommentText.Text){
									data = te.toString();
								}
								printer.println("{\"type\":\""+te.getClass().getSimpleName()+"\""+((data.length()>0)? ", \"data\":\""+data+"\"":"")+"}"+comma, 4);
							}
							printer.println("]", 3);
						}
					String comma = (thread.getComments().indexOf(c)!=thread.getComments().size()-1)? ",":"";
					printer.println("}"+comma, 2);
				}
				printer.println("]",1);

			printer.println("}",0);

			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	private static class Printer{
		private final PrintStream ps;
		public Printer(PrintStream ps){
			this.ps=ps;
		}
		
		public void println(Object o,int number){
			String toPrint = o.toString();
			for(int i=0;i<number;i++){
				toPrint = "\t"+toPrint;
			}
			this.ps.println(toPrint);
		}
	}
}