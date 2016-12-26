package com.manskx.nhscrawler.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.manskx.nhscrawler.manager.ConditionsExtractor;

public class ConditionsExtractorTest {
	
	String html = "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\">" + "<head>"
			+ "<title>Back pain - NHS Choices</title>" + "<div class=\"healthaz-header clear\">"
			+ "<h1>Back pain&nbsp;</h1>" + "</div>" + "<div class=\"main-content healthaz-content clear\">"
			+ "    <p>Back pain is a common problem that affects most people at some point in their life.</p>"
			+ "</div>" + "</html>";

	@Test
	public void testConditionsExtractorTitle() throws IOException {

		ConditionsExtractor conditionsExtractor = ConditionsExtractor.getInstance();
		ConditionsExtractor.getInstance().setHtml(html);
		String title = conditionsExtractor.getTitle();

		assertEquals(title, "Back pain - NHS Choices");
	}

	@Test
	public void testConditionsExtractorContent() throws IOException {

		ConditionsExtractor conditionsExtractor = ConditionsExtractor.getInstance();
		ConditionsExtractor.getInstance().setHtml(html);
		String content = conditionsExtractor.getContent();

		assertEquals(content, "Back pain is a common problem that affects most people at some point in their life.");
	}

	@Test
	public void testConditionsExtractorHeader() throws IOException {
		

		ConditionsExtractor conditionsExtractor = ConditionsExtractor.getInstance();
		ConditionsExtractor.getInstance().setHtml(html);
		String header = conditionsExtractor.getHeader();

		assertEquals(header, "Back pain ");
	}

}
