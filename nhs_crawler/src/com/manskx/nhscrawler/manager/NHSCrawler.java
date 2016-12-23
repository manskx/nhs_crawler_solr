package com.manskx.nhscrawler.manager;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Set;
import java.util.regex.Pattern;
import com.shekhargulati.urlcleaner.UrlCleaner;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.Header;
import org.hibernate.Session;

import com.manskx.nhscrawler.database.Conditions;
import com.manskx.nhscrawler.persistence.HibernateUtil;
import com.manskx.nhscrawler.resources.ConditionsInsertion;
import com.manskx.nhscrawler.resources.Configurations;

public class NHSCrawler extends WebCrawler {
	private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		// Ignore the url if it has an extension that matches our defined set of
		// image extensions.
		if (IMAGE_EXTENSIONS.matcher(href).matches()) {
			return false;
		}

		// Only accept the url if it is in the "www.ics.uci.edu" domain and
		// protocol is "http".
		return href.startsWith(Configurations.URL_PREFIX);
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		URI uri	=	null;
		
		try {
			uri	=	new URI(url);
		} catch (URISyntaxException e) {
			NHSController.FaildFetchedURL();
			return;
		}
		String normalizedUrl = uri.normalize().toString();
		int urlHashCode = normalizedUrl.hashCode();
		String anchor = page.getWebURL().getAnchor();
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			ConditionsExtractor conditionsExtractor = ConditionsExtractor.getInstance();
			ConditionsExtractor.getInstance().setHtml(html);
			String title = conditionsExtractor.getTitle();
			String content = conditionsExtractor.getContent();
			String header = conditionsExtractor.getHeader();
			ConditionsInsertion conditionsInsertionMethod = NHSController.getConditionsInsertion();
			conditionsInsertionMethod.insertData(url, anchor, title, header, content, urlHashCode);
			NHSController.SuccesfullyFetchedURL();
		}

	}
}
