package com.prealpha.objects;

import org.jsoup.nodes.Element;

public class CommentText {
	public class Link{
		public final int number;
		public Link(int number){
			this.number=number;
		}
	}
	
	public class Text{
		public final String text;
		public Text(String text){
			this.text = text;
		}
	}
	
	public CommentText(Element blockquote){
		String fullText = blockquote.toString();
		
		for(Element e:blockquote.children()){
			if(!e.children().isEmpty()){
				// It is a link
			}
			else{
				// It is a quote
			}
		}
	}
}
