package com.manskx.nhscrawler.resources;

/**
 * This is configuration file
 * Mainly this class contains all configuration needed for different experiments
 * 
 * @author Ahmed
 *
 */
public class Configurations {
	// seed url used to start crawling
	public static final String SEED_URL = "http://www.nhs.uk/conditions/Pages/hub.aspx";
	// prefix url, avoid any url doesn't start with this prefix
	public static final String URL_PREFIX = "http://www.nhs.uk/conditions/";
	//number of concurrent crawlers threads running in the same time
	public static final int NUMBER_OF_CRAWLERS = 5;
	//maximum crawl page depth
	public static final int MAX_DEBTH_OF_CRAWLING = 50;
	//maximum nuver of pages to be crawled, this is absolute number to all crawlers treads 
	// or you want to crawling to infinity.
	public static final int MAX_PAGES_TO_FETCH = 5000;
	
	// this is temp folder to store crawling date 
	public static final String CRAWL_STORAGE_FOLDER = "/home/mansk/nhs/nhs_crawler/nhs_crawler/";
	
	// html id to extract the content in the page ( extracted manually)
	public static final String JSOUP_HTML_ContentID = ".main-content.healthaz-content, .guidespanel"; //.main-content.healthaz-content OR .guidespanel guidespanel
	// html id to extract the header inside the page ( extracted manually)
	public static final String JSOUP_HTML_HeaderID = ".healthaz-header";
	
	// solr engine base server url
	// this is important because in most cases solr is not running in the same machine	
	public static final String SOLR_HOST = "http://localhost:8983/solr/nhs_conditions/";
	
	// these are some configurations to enhance search results accuracy based on query scoring
	// There numbers are generated from multiple experiments, we could select the best values 
	// by learning machine learning module and doing data annotation
	public static final int SOLR_HEADER_SCORE = 10;
	public static final int SOLR_TITLE_SCORE = 10;
	public static final int SOLR_URL_SCORE = 10;
	public static final int SOLR_CONTENT_SCORE = 2;
	public static final int SOLR_ANCHOR_SCORE = 5;
	public static final int SOLR_SENTENCE_SCORE = 5;

	// the output formate to show results
	public static final String SOLR_RESULTS_FORMATE = "json";
	// the fields to be shown in the result json/xml
	public static final String SOLR_RESULTS_FIELDS = "title,url,header,anchor";
	// stopping words file
	public static final String SOLR_STOPPING_WORDS_FILE = "/home/mansk/nhs/nhs_crawler/solr-6.3.0/server/solr/nhs_conditions/conf/stopwords.txt";

}
