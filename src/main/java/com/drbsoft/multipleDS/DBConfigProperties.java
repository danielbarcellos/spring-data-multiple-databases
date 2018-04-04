package com.drbsoft.multipleDS;


import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:db-config.properties" })
@ConfigurationProperties(prefix = "multiple")
public class DBConfigProperties {
	
	public static class DataSourceSet {
		private String url;
		private String username;
		private String password;
		private String driverClassName;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getDriverClassName() {
			return driverClassName;
		}
		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}
	}
	
	private Map<String, DataSourceSet> datasource;
	
	public Map<String, DataSourceSet> getDatasource() {
		return datasource;
	}
	
	public void setDatasource(Map<String, DataSourceSet> datasource) {
		this.datasource = datasource;
	}
}