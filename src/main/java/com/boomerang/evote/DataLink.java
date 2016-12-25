package com.boomerang.evote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


@Repository
public class DataLink {
	private static final Logger LOG = Logger.getLogger(DataLink.class);

	//SQL statements
	private static final String SHOW_TABLES = "SHOW TABLES";

	@Value("${spring.datasource.username}")
	String username;

	@Value("${spring.datasource.password}")
	String password;

	@Value("${spring.datasource.url}")
	String url;
	
	@Value("${spring.datasource.link}")
	String link;
	
	public DataLink() {
		LOG.info("Instantiated DataLink");
	}
	
	public String touch() {
		LOG.info("Touching DB");
		StringBuilder message = new StringBuilder();
		Connection cloudSQL = null;
		try {
			cloudSQL = altConnection();
			Statement query = cloudSQL.createStatement();
			ResultSet resultSet = query.executeQuery(SHOW_TABLES);
			while (resultSet.next()) {
				message.append(resultSet.getString(1));
				message.append(System.lineSeparator());
			}
			closeConnection(cloudSQL);
		} catch(SQLException e) {
			LOG.error("Google Cloud SQL Connection not established- " + e);
			StackTraceElement[] trace = e.getStackTrace();
			for (int i = 0; i < trace.length; i ++) {
				message.append(trace[i]);
				message.append(System.lineSeparator());
			}
		}
		return message.toString();
	}
	
	private Connection openConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	private Connection altConnection() throws SQLException {
		return DriverManager.getConnection(link);
	}
	
	private void closeConnection(Connection connection) throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
