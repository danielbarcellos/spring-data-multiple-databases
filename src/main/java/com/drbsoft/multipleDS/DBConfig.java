package com.drbsoft.multipleDS;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.drbsoft.multipleDS.DBConfigProperties.DataSourceSet;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class DBConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		return dataSource("spring");
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return buildJdbcTemplate(dataSource);
	}

	private JdbcTemplate buildJdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}

	@Bean
	public MultipleJdbcTemplate multiplesJdbcTemplate(DBConfigProperties configProperties) {
		MultipleJdbcTemplate multiplesJdbcTemplate = new MultipleJdbcTemplate();

		configProperties.getDatasource().forEach((k, v) -> multiplesJdbcTemplate.getMultiplesJdbcTemplate().put(k, buildJdbcTemplate(dataSource(v))));

		return multiplesJdbcTemplate;
	}

	private DataSource dataSource(DataSourceSet dsConfig) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(dsConfig.getDriverClassName());
		dataSource.setUrl(dsConfig.getUrl());
		dataSource.setUsername(dsConfig.getUsername());
		dataSource.setPassword(dsConfig.getPassword());

		return dataSource;
	}

	private DataSource dataSource(String dbPrefix) {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getProperty(dbPrefix + ".datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty(dbPrefix + ".datasource.url"));
		dataSource.setUsername(env.getProperty(dbPrefix + ".datasource.username"));
		dataSource.setPassword(env.getProperty(dbPrefix + ".datasource.password"));

		return dataSource;
	}

	public static class MultipleJdbcTemplate {
		final Map<String, JdbcTemplate> multiplesJdbcTemplate = new HashMap<>();

		public Map<String, JdbcTemplate> getMultiplesJdbcTemplate() {
			return multiplesJdbcTemplate;
		}
	}
}