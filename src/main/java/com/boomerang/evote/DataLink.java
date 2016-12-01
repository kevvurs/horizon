package com.boomerang.evote;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.cloud.sql.jdbc.Connection;
import com.google.cloud.sql.jdbc.ResultSet;
import com.google.cloud.sql.jdbc.ResultSetMetaData;
import com.google.cloud.sql.jdbc.Statement;
//import com.mysql.jdbc.Driver;

@Repository
public class DataLink {
	private static final Logger LOG = Logger.getLogger(DataLink.class);
	private boolean driverCompiled;

	//SQL statements
	private static final String showTables = "SHOW TABLES";

	@Value("${ae-cloudsql.datasource.driver-class-name}")
	String driverClass;

	@Value("${ae-cloudsql.datasource.database-url}")
	String databaseUrl;

	@Value("${ae-cloudsql.datasource.socket-url}")
	String socketUrl;

	public DataLink() {
		LOG.info("Instantiated DataLink");
	}

	@PostConstruct
	public void init() {
		this.driverCompiled = runDriver();
	}

	private boolean runDriver() {
		boolean success = false;
		try {
			Class.forName(driverClass);
			success = true;
		} catch (Exception e) {
			LOG.warn("Error running Google Cloud SQL driver -" + e);
		}
		return success;
	}

	public String touch() {
		LOG.info("Touching DB");
		StringBuilder postInfo = new StringBuilder();
		try {
			Connection connection = (Connection) DriverManager.getConnection(socketUrl);
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery(showTables);
			ResultSetMetaData md = res.getMetaData();
			while (res.next()) {
				for (int i = 1; i <= md.getColumnCount(); i++) {
					postInfo.append('[');
					postInfo.append(res.getString(i));
					postInfo.append(']');
				}
			}
			return postInfo.toString();
		} catch (SQLException e) {
			LOG.error(e);
			return e.getMessage();
		}
	}
}
