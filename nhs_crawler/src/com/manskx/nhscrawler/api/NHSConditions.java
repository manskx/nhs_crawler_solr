package com.manskx.nhscrawler.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.manskx.nhscrawler.manager.NHSConditionsSearch;
import com.manskx.nhscrawler.manager.NHSController;

@Path("/conditions")

public class NHSConditions {
	/**
	 * 
	 * @param query: this is the query search string 
	 * 				ex( “what are the symptoms of cancer?” “treatments for headaches”)
	 * @return : json search results from solr engine
	 * @throws Exception
	 */
	@GET
	@Path("/search")
	@Produces("application/json")
	public String getConditionsSearchResults(@QueryParam("query") String query) throws Exception {

		return String.valueOf(NHSConditionsSearch.getInstance().getNHSConditionsSearchRsults(query));

	}
}
