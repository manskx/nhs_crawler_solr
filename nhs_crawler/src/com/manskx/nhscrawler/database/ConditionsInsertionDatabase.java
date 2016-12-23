package com.manskx.nhscrawler.database;

import org.hibernate.Session;

import com.manskx.nhscrawler.persistence.HibernateUtil;
import com.manskx.nhscrawler.resources.ConditionsInsertion;

public class ConditionsInsertionDatabase implements ConditionsInsertion{

	public void insertData(String url, String anchor, String title, String header, String contentdata, int hashed_url) {
        Session session = HibernateUtil.getSessionFactory().openSession();
		
		session.beginTransaction();
		Conditions condition = new Conditions();
		
		condition.setUrl(url);
		condition.setAnchor(anchor);
		condition.setTitle(title);
		condition.setHeader(header);
		condition.setContentdata(contentdata);
		condition.setHashed_url(hashed_url);
		session.save(condition);               
		//session.delete(stock);
		
		session.getTransaction().commit();
	}

}
