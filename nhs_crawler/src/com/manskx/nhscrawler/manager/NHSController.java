package com.manskx.nhscrawler.manager;

import com.manskx.nhscrawler.database.ConditionsInsertionDatabase;
import com.manskx.nhscrawler.resources.ConditionsInsertion;
import com.manskx.nhscrawler.resources.Configurations;
import com.manskx.nhscrawler.resources.MessageSource;
import com.sleepycat.je.rep.impl.TextProtocol.Message;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class NHSController {
	private static NHSController instance;
	public static boolean CrawlingStarted = false;
	public static boolean CrawlingFinished = false;
	private static ConditionsInsertion currConditionsInsertion;
	private static int SuccesfullyFetchedURLs = 0;

	public static int getSuccesfullyFetchedURLs() {
		return SuccesfullyFetchedURLs;
	}

	public static int getFaildFetchedURLs() {
		return FaildFetchedURLs;
	}

	private static int FaildFetchedURLs = 0;

	public static void SuccesfullyFetchedURL() {
		SuccesfullyFetchedURLs++;
	}

	public static void FaildFetchedURL() {
		FaildFetchedURLs++;
	}

	private NHSController() {
	}
	// singleton instance 
	public static NHSController getInstance() {
		if (instance == null) {
			instance = new NHSController();
			// initialize conditions data insertion method
			currConditionsInsertion = new ConditionsInsertionDatabase();
		}
		return instance;
	}

	public static ConditionsInsertion getConditionsInsertion() {
		return currConditionsInsertion;
	}

	public String getSuccessfulAndFaildUrlsStatus() {
		return "\n Successfully Fetchs: " + getSuccesfullyFetchedURLs() + "\n" + "Faild Fechs: "
				+ getFaildFetchedURLs();
	}

	public String getCrawlingStatus() {
		if (CrawlingStarted)
			return MessageSource.RUNNING_CRALWING + getSuccessfulAndFaildUrlsStatus();
		if (CrawlingFinished)
			return MessageSource.FINISHED_CRALWING + getSuccessfulAndFaildUrlsStatus();
		return MessageSource.NOT_STARTED_CRALWING;

	}
	/**
	 * start crawling in background thread.
	 * @return
	 * @throws Exception
	 */
	public String startCrawlingInBackground() throws Exception {

		if (CrawlingStarted)
			return MessageSource.WARNING_CRAWLING_IS_ALREADY_RUNNING;
		Runnable r = new Runnable() {
			public void run() {
				try {
					crawler();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		new Thread(r).start();
		CrawlingStarted = true;
		if (CrawlingStarted)
			return MessageSource.STARTING_CRAWLING;
		return MessageSource.ERROR_;
	}

	public void crawler() throws Exception {
		CrawlingStarted = true;
		/*
		 * crawlStorageFolder is a folder where intermediate crawl data is
		 * stored.
		 */
		String crawlStorageFolder = Configurations.CRAWL_STORAGE_FOLDER;

		/*
		 * numberOfCrawlers shows the number of concurrent threads that should
		 * be initiated for crawling.
		 */
		int numberOfCrawlers = Configurations.NUMBER_OF_CRAWLERS;

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
		config.setMaxDepthOfCrawling(Configurations.MAX_DEBTH_OF_CRAWLING);

		/*
		 * You can set the maximum number of pages to crawl. The default value
		 * is -1 for unlimited number of pages
		 */
		config.setMaxPagesToFetch(Configurations.MAX_PAGES_TO_FETCH);
		
		config.setConnectionTimeout(5000);
		/**
		 * Do you want crawler4j to crawl also binary data ? example: the
		 * contents of pdf, or the metadata of images etc
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
		controller.addSeed(Configurations.SEED_URL);

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(NHSCrawler.class, numberOfCrawlers);
		// Crawling flags
		CrawlingFinished = true;
		CrawlingStarted = false;

	}
}
