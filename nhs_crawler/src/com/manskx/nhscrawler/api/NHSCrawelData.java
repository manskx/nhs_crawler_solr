package com.manskx.nhscrawler.api;

import java.net.HttpURLConnection;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.manskx.nhscrawler.manager.NHSController;



@Path("/nhs_crawel")
public class NHSCrawelData {
	@GET
	@Path("/do")
	@Produces("text/plain")
	public String hello() throws Exception {
		
		NHSController.getInstance().crawler();

		return "OK";
	}
}
