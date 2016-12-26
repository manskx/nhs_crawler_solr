package com.manskx.nhscrawler.test;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.manskx.nhscrawler.persistence.HibernateUtil;

public class UrlTest {
	@Test
	public void testDatabaseInsertion() throws URISyntaxException {
		String url = "http://www.Example.com:80/bar.html";
		URI uri	=	null;
		uri	=	new URI(url);
		String normalizedUrl = uri.normalize().toString();
		assertEquals( normalizedUrl,url);
	}
}
