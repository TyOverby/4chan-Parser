package com.prealpha.foursearch.objects;

import java.util.ArrayList;
import java.util.List;

public class CommentText {
	public static interface TextElement {}
	

	private final List<TextElement> texts = new ArrayList<TextElement>();
	public void addTextElement(TextElement te) {
		this.texts.add(te);
	}
	public List<TextElement> getTexts() {
		return this.texts;
	}
	
	public static class Text implements TextElement {
		public final String text;

		public Text(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}
	}
	
	public static class QuoteLink extends Text {
		public QuoteLink(String text) {
			super(text);
		}

		@Override
		public String toString() {
			return this.text;
		}
	}

	public static class Quote extends Text {
		public Quote(String text) {
			super(text);
		}
	}

	public static class Spoiler extends Text {
		public Spoiler(String text) {
			super(text);
		}
	}

	public static class BlankLine implements TextElement {
		@Override
		public String toString() {
			return "";
		}
	}

}
