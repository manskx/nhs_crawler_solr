package com.manskx;

import java.io.File;
import java.io.PrintWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SynonymsHandler extends DefaultHandler {

	private static PrintWriter outSynonymsFile;
	String Title;

	boolean healthTopicStarted;
	boolean alsoCalled;
	String Line;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("health-topic")) {
			Title = attributes.getValue("title");
			Line = Title;
			healthTopicStarted = true;
		} else if (qName.equalsIgnoreCase("also-called")) {
			alsoCalled = true;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("health-topic")) {
			healthTopicStarted = false;
			if (!Line.equals(Title))// out line to file
				outSynonymsFile.println(Line);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		if (healthTopicStarted&&alsoCalled) {
			Line += "," + new String(ch, start, length);
			alsoCalled = false;
		}
	}

	public static PrintWriter getOutSynonymsFile() {
		return outSynonymsFile;
	}

	public static void setOutSynonymsFile(PrintWriter outSynonymsFile) {
		SynonymsHandler.outSynonymsFile = outSynonymsFile;
	}

}
