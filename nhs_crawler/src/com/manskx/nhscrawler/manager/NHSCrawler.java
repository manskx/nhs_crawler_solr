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
	 * This method is to specify whether the given url
	 * should be crawled or not.
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		// Ignore the url if it has an extension that matches our defined set of
		// image extensions.
		if (IMAGE_EXTENSIONS.matcher(href).matches()) {
			return false;
		}

		// Only accept the url if it is in the Configurations.URL_PREFIX
		return href.startsWith(Configurations.URL_PREFIX);
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * 
	 */
	@Override
	public void visit(Page page) {
		//convert url to lower case to avoid duplicates
		String url = page.getWebURL().getURL().toLowerCase();
		URI uri	=	null;
		
		try {
			uri	=	new URI(url);
		} catch (URISyntaxException e) {
			NHSController.FaildFetchedURL();
			return;
		}
		// normalize url		
		String normalizedUrl = uri.normalize().toString();
		//unique url hashcode is used to avoid duplicate URLs in database
		int urlHashCode = normalizedUrl.hashCode();
		String anchor = page.getWebURL().getAnchor();
		if (page.getParseData() instanceof HtmlParseData) {
			
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			//extract conditions using jsoup
			ConditionsExtractor conditionsExtractor = ConditionsExtractor.getInstance();
			ConditionsExtractor.getInstance().setHtml(html);
			String title = conditionsExtractor.getTitle();
			String content = conditionsExtractor.getContent();
			String header = conditionsExtractor.getHeader();
			// insert extracted conditions in the insertions method ( database/ jsonfile/ online api ....)
			ConditionsInsertion conditionsInsertionMethod = NHSController.getConditionsInsertion();
			conditionsInsertionMethod.insertData(normalizedUrl, anchor, title, header, content, urlHashCode);
			// callback trigger
			NHSController.SuccesfullyFetchedURL();
		}

	}
}
