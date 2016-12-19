package com.manskx.nhscrawler.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.manskx.nhscrawler.manager.NHSController;

public class ConditionsInsertions {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/NHS_Conditions";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	private static ConditionsInsertions instance;

	private ConditionsInsertions() {
	}

	public static ConditionsInsertions getInstance() {
		if (instance == null) {
			instance = new ConditionsInsertions();
		}
		return instance;
	}

	public void insertData(String url, String anchor, String title, String header, String contentdata, int hashed_url)
			throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");

		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		// the mysql insert statement
		String query = " insert into conditions (url, anchor, title, header, contentdata, hashed_url)"
				+ " values (?, ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1, url);
		preparedStmt.setString(2, anchor);

		preparedStmt.setString(3, title);
		preparedStmt.setString(4, header);
		preparedStmt.setString(5, contentdata);
		preparedStmt.setInt(6, hashed_url);
		// execute the preparedstatement
		preparedStmt.execute();

		conn.close();
	}

}
