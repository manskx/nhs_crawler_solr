package com.manskx.nhscrawler.test;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.Test;

import com.manskx.nhscrawler.persistence.HibernateUtil;

public class DatabaseTest {
	@Test
	public void testDatabaseConnection() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		assertEquals( session.isConnected(),true);
	}
	
	@Test
	public void testDatabaseInsertion() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		assertEquals( " "," " );
	}
	
}
