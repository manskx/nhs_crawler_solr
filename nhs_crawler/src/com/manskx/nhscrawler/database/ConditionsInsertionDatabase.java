package com.manskx.nhscrawler.database;

import org.hibernate.Session;

import com.manskx.nhscrawler.persistence.HibernateUtil;
import com.manskx.nhscrawler.resources.ConditionsInsertion;

public class ConditionsInsertionDatabase implements ConditionsInsertion {
	/**
	 * This method is responsable for inserting conditions data in the database using hibernate ORM
	 */
	public void insertData(String url, String anchor, String title, String header, String contentdata, int hashed_url) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		// condiation table schema in conditions.hbm.xml file
		session.beginTransaction();
		Conditions condition = new Conditions();

		condition.setUrl(url);
		condition.setAnchor(anchor);
		condition.setTitle(title);
		condition.setHeader(header);
		condition.setContentdata(contentdata);
		condition.setHashed_url(hashed_url);
		session.save(condition);

		session.getTransaction().commit();
	}

}
