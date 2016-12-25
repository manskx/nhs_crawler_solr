package com.manskx.nhscrawler.api;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


import com.manskx.nhscrawler.manager.NHSController;
import com.manskx.nhscrawler.resources.MessageSource;

@Path("/crawling")
public class NHSCrawelData {
	/**
	 * 
	 * @return String status about starting crawling
	 * @throws Exception
	 */
	@GET
	@Path("/status")
	@Produces("text/plain")
	public String getCrawlingStatusService() throws Exception {

		return String.valueOf(NHSController.getInstance().getCrawlingStatus());

	}

	@POST
	@Path("/start")
	@Produces("text/plain")
	public String startCrawlingInBackgroundService() {
		try {
			return String.valueOf(NHSController.getInstance().startCrawlingInBackground());
		} catch (Exception e) {
			return MessageSource.ERROR_ + e.getMessage();
		}
	}
	
	
	
}
