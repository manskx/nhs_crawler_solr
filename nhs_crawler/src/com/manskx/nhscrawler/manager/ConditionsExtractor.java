package com.manskx.nhscrawler.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ConditionsExtractor {
	private static ConditionsExtractor instance;
	private String html;
	String ContentID = ".main-content.healthaz-content, .guidespanel";// || .guidespanel guidespanel
	String HeaderID = ".healthaz-header";
	Document htmlDoc;

	private ConditionsExtractor() {
	}

	public static ConditionsExtractor getInstance() {
		if (instance == null) {
			instance = new ConditionsExtractor();
		}
		return instance;
	}

	public String getContent() {
		return htmlDoc.select(ContentID).text();

	}

	public String getTitle() {

		return htmlDoc.select("title").text();

	}

	public String getHeader() {//healthaz-header clear
		Elements	headerElement =	htmlDoc.select(HeaderID);
		return headerElement.text();
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
		htmlDoc = Jsoup.parse(html);
	}

}
