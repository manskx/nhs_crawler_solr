package com.manskx.nhscrawler.manager;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
public class NHSController {
	private static NHSController instance;

	private NHSController() {
	}

	public static NHSController getInstance() {
		if (instance == null) {
			instance = new NHSController();
		}
		return instance;
	}

	
	public void crawler() throws Exception{
		 /*
	     * crawlStorageFolder is a folder where intermediate crawl data is
	     * stored.
	     */
	        String crawlStorageFolder = "/data/";

	    /*
	     * numberOfCrawlers shows the number of concurrent threads that should
	     * be initiated for crawling.
	     */
	        int numberOfCrawlers = 5;

	        CrawlConfig config = new CrawlConfig();

	        config.setCrawlStorageFolder(crawlStorageFolder);

	    /*
	     * Be polite: Make sure that we don't send more than 1 request per
	     * second (1000 milliseconds between requests).
	     */
	        config.setPolitenessDelay(1000);

	    /*
	     * You can set the maximum crawl depth here. The default value is -1 for
	     * unlimited depth
	     */
	        config.setMaxDepthOfCrawling(10);

	    /*
	     * You can set the maximum number of pages to crawl. The default value
	     * is -1 for unlimited number of pages
	     */
	        config.setMaxPagesToFetch(1000);

	        /**
	         * Do you want crawler4j to crawl also binary data ?
	         * example: the contents of pdf, or the metadata of images etc
	         */
	        config.setIncludeBinaryContentInCrawling(false);

	    /*
	     * Do you need to set a proxy? If so, you can use:
	     * config.setProxyHost("proxyserver.example.com");
	     * config.setProxyPort(8080);
	     *
	     * If your proxy also needs authentication:
	     * config.setProxyUsername(username); config.getProxyPassword(password);
	     */

	    /*
	     * This config parameter can be used to set your crawl to be resumable
	     * (meaning that you can resume the crawl from a previously
	     * interrupted/crashed crawl). Note: if you enable resuming feature and
	     * want to start a fresh crawl, you need to delete the contents of
	     * rootFolder manually.
	     */
	        config.setResumableCrawling(false);

	    /*
	     * Instantiate the controller for this crawl.
	     */
	        PageFetcher pageFetcher = new PageFetcher(config);
	        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
	        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

	    /*
	     * For each crawl, you need to add some seed urls. These are the first
	     * URLs that are fetched and then the crawler starts following links
	     * which are found in these pages
	     */
	        controller.addSeed("http://www.nhs.uk/conditions/Pages/hub.aspx");


	    /*
	     * Start the crawl. This is a blocking operation, meaning that your code
	     * will reach the line after this only when crawling is finished.
	     */
	        controller.start(NHSCrawler.class, numberOfCrawlers);
	}
}
