package com.manskx;

import java.io.File;
import java.io.PrintWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MedicalExtractor_XML {
	public static void main(String[] args) {

		try {
			File inputFile = new File("mplus_topics_2016-12-24.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SynonymsHandler synonymsHandler = new SynonymsHandler();
			PrintWriter synonymsFileWriter = new PrintWriter("synonyms_list.txt", "UTF-8");
			SynonymsHandler.setOutSynonymsFile(synonymsFileWriter);

			saxParser.parse(inputFile, synonymsHandler);
			SynonymsHandler.getOutSynonymsFile().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
