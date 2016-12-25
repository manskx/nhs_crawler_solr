package com.manskx.nhscrawler.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.manskx.nhscrawler.resources.Configurations;
/**
 * 
 * @author mansk
 * 
 * This class is responsable for extracting data from html page using jsoup and css query selector
 */
public class ConditionsExtractor {
	private static ConditionsExtractor instance;
	private String html;

	Document htmlDoc;

	private ConditionsExtractor() {
	}
	// singleton instance
	public static ConditionsExtractor getInstance() {
		if (instance == null) {
			instance = new ConditionsExtractor();
		}
		return instance;
	}

	public String getContent() {
		return htmlDoc.select(Configurations.JSOUP_HTML_ContentID).text();

	}

	public String getTitle() {

		return htmlDoc.select("title").text();

	}

	public String getHeader() {
		Elements	headerElement =	htmlDoc.select(Configurations.JSOUP_HTML_HeaderID);
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
