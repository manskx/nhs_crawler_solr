package com.manskx.nhscrawler.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import com.manskx.nhscrawler.manager.NHSConditionsSearch;
import com.manskx.nhscrawler.persistence.HibernateUtil;

public class QueryTest {
	@Test
	public void testStoppingWords1() throws IOException {
		String Query = "my name is ahmed";
		ArrayList<String> actualtResultWolds = NHSConditionsSearch.getInstance()
				.removeStoppingWords(Arrays.asList( Query.split(" ")));
		// Can be any order
		assertThat(actualtResultWolds, contains("my", "name", "ahmed"));
	}

	@Test
	public void testStoppingWords2() throws IOException {
		String Query = "this is a cat";
		ArrayList<String> actualtResultWolds = NHSConditionsSearch.getInstance()
				.removeStoppingWords(Arrays.asList( Query.split(" ")));		// Can be any order
		assertThat(actualtResultWolds, contains("cat"));
	}

	@Test
	public void testStoppingWords3() throws IOException {
		String Query = "treatments for headaches";
		ArrayList<String> actualtResultWolds = NHSConditionsSearch.getInstance()
				.removeStoppingWords(Arrays.asList( Query.split(" ")));		// Can be any order
		assertThat(actualtResultWolds, contains("treatments", "headaches"));
	}

	@Test
	public void testSolrQueryParam_defType() throws IOException {
		String Query = "treatments for headaches";// createSolrQueryParams
		Map<String, String> solrQueryParam = NHSConditionsSearch.getInstance().createSolrQueryParams(Query);
		// Can be any order
		assertEquals(solrQueryParam.get("defType"), "edismax");
	}

	@Test
	public void testSolrQueryParam_fl() throws IOException {
		String Query = "treatments for headaches";// createSolrQueryParams
		Map<String, String> solrQueryParam = NHSConditionsSearch.getInstance().createSolrQueryParams(Query);
	
		assertEquals(solrQueryParam.get("fl"), "title,url,header,anchor");
	}

	@Test
	public void testSolrQueryParam_q() throws IOException {
		String Query = "treatments for headaches";// createSolrQueryParams
		Map<String, String> solrQueryParam = NHSConditionsSearch.getInstance().createSolrQueryParams(Query);
		// Can be any order
		String expectedQ = "title:treatment+headache+OR+\"treatments+for+headaches\"^5 "
				+ "header:treatment+headache+OR+\"treatments+for+headaches\"^5 "
				+ "url:treatment+headache+OR+\"treatments+for+headaches\"^5 "
				+ "contentdata:treatment+headache+OR+\"treatments+for+headaches\"^5 "
				+ "anchor:treatment+headache+OR+\"treatments+for+headaches\"^5";
		assertEquals(solrQueryParam.get("q"), expectedQ);
	}

	@Test
	public void testSolrQueryParam_qf() throws IOException {
		String Query = "treatments for headaches";// createSolrQueryParams
		Map<String, String> solrQueryParam = NHSConditionsSearch.getInstance().createSolrQueryParams(Query);
		String expectedQF = "title^10 header^10 url^10 contentdata^2 anchor^5";
		assertEquals(solrQueryParam.get("qf"), expectedQF);
	}

}
