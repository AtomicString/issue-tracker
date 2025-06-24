package me.atomicstring.tracker;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceFactory {
	
	public static HikariDataSource createDataSource(String username, String password, String dbName) {
		
		HikariConfig config = new HikariConfig();
		
		config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
		
		config.addDataSourceProperty("portNumber", 5432);
		config.addDataSourceProperty("user", username);
		config.addDataSourceProperty("password", password);
		config.addDataSourceProperty("serverName", "db");
		config.addDataSourceProperty("databaseName", dbName);
		
		return new HikariDataSource(config);
	}
	
}
