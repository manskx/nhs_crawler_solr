package com.manskx.nhscrawler.manager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;

import org.json.JSONObject;

import com.manskx.nhscrawler.resources.Configurations;

public class NHSConditionsSearch {
	private static NHSConditionsSearch instance;

	private static Map<String, Integer> stoppingWordsList;

	private NHSConditionsSearch() {
	}

	public static NHSConditionsSearch getInstance() throws IOException {
		if (instance == null) {
			instance = new NHSConditionsSearch();
			stoppingWordsList = getStoppingWords();
		}
		return instance;
	}

	public JSONObject getNHSConditionsSearchRsults(String query) throws UnsupportedEncodingException {
		Map<String, String> solrQueryParam = createSolrQueryParams(query);

		// do ge request to solr server and return the results
		String responseEntity = ClientBuilder.newClient().target(Configurations.SOLR_HOST).path("select")
				.queryParam("defType", solrQueryParam.get("defType")).queryParam("fl", solrQueryParam.get("fl"))
				.queryParam("indent", solrQueryParam.get("indent")).queryParam("q", solrQueryParam.get("q"))
				.queryParam("qf", solrQueryParam.get("qf")).queryParam("wt", solrQueryParam.get("wt"))

				.request().get(String.class);
		JSONObject responseJSON_obj = new JSONObject(responseEntity);
		return responseJSON_obj;
	}

	/**
	 * this method is responsable for creating the  solr query parameters
	 * @param searchQuery input search query from the user
	 * @return hashmap contains all query parameters to be used in url call
	 * @throws UnsupportedEncodingException
	 */
	public Map<String, String> createSolrQueryParams(String searchQuery) throws UnsupportedEncodingException {
		//lemmatize query
		List<String> lemmatizeList 	=	textLemmatizer(searchQuery);
		ArrayList<String> cleanWords = removeStoppingWords(lemmatizeList);
		// create query words
		String wordsQuery = createQueryWords(cleanWords);

		Map<String, String> solrQueryParam = new HashMap<String, String>();
		solrQueryParam.put("defType", "edismax");
		// selected fields to be shown in the results
		solrQueryParam.put("fl", Configurations.SOLR_RESULTS_FIELDS);
		
		solrQueryParam.put("indent", "c");
		String searchQueryPlus = searchQuery.replaceAll(" ", "+");
		
		// if the complete sentence appears in the text, so it has higher score than seperate words
		String qSolrQuery = "title:" + wordsQuery + "+OR+" + "\"" + searchQueryPlus + "\"^"
				+ Configurations.SOLR_SENTENCE_SCORE + " header:" + wordsQuery + "+OR+" + "\"" + searchQueryPlus + "\"^"
				+ Configurations.SOLR_SENTENCE_SCORE + " url:" + wordsQuery + "+OR+" + "\"" + searchQueryPlus + "\"^"
				+ Configurations.SOLR_SENTENCE_SCORE + " contentdata:" + wordsQuery + "+OR+" + "\"" + searchQueryPlus
				+ "\"^" + Configurations.SOLR_SENTENCE_SCORE + " anchor:" + wordsQuery + "+OR+" + "\"" + searchQueryPlus
				+ "\"^" + Configurations.SOLR_SENTENCE_SCORE;

		solrQueryParam.put("q", qSolrQuery);
		// let some fields have higher score than other fields 
		// ex: if word "cancer" appears in title then it is better than to be appeared in content 
		String qfSolrQuery = "title^" + Configurations.SOLR_TITLE_SCORE + " header^" + Configurations.SOLR_HEADER_SCORE
				+ " url^" + Configurations.SOLR_URL_SCORE + " contentdata^" + Configurations.SOLR_CONTENT_SCORE
				+ " anchor^" + Configurations.SOLR_ANCHOR_SCORE;

		solrQueryParam.put("qf", qfSolrQuery);
		//result format
		solrQueryParam.put("wt", Configurations.SOLR_RESULTS_FORMATE);

		return solrQueryParam;

	}
/**
 * 
 * @param clearWords query words to be inserted to query string
 * @return clear words seperated by "+" operator 
 */
	private String createQueryWords(ArrayList<String> clearWords) {
		String wordsQuery = "";
		String separator = "";

		for (String s : clearWords) {
			wordsQuery += separator + s;
			separator = "+";
		}
		return wordsQuery;
	}
	/**
	 * this is genaric method to lemmatize text inside an input string query
	 * more info {http://stanfordnlp.github.io/CoreNLP/}
	 * @param query
	 * @return list of lemmatized clean words
	 */
	public List<String> textLemmatizer(String query) {
		StanfordLemmatizer slem = new StanfordLemmatizer();
		return slem.lemmatize(query);
	}

	/**
	 * this is genaric method to remove stopping words inside an input string query
	 * @param query
	 * @return list of clean words that don't exists in the list of stopping words
	 */
	public ArrayList<String> removeStoppingWords(List<String> words) {
		ArrayList<String> cleanWords = new ArrayList<String>();
		for (int i = 0; i < words.size(); i++) {
			if (!stoppingWordsList.containsKey(words.get(i)))
				cleanWords.add(words.get(i));
		}
		return cleanWords;
	}
	/**
	 * this methods reads the stopping words from solr file and returns it
	 * @return hashmap contains list of stopping words
	 * @throws IOException
	 */
	private static Map<String, Integer> getStoppingWords() throws IOException {
		// Open the file
		FileInputStream fstream = new FileInputStream(Configurations.SOLR_STOPPING_WORDS_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		Map<String, Integer> stoppingWordsList = new HashMap<String, Integer>();
		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			// ignore comment line ( starts with "#")
			if (!strLine.isEmpty() && strLine.charAt(0) != '#') {
				stoppingWordsList.put(strLine, 0);
			}

		}
		// Close the input stream
		br.close();
		return stoppingWordsList;
	}
}
