package cloudapp.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import cloudapp.jpa.DbRepo;
import cloudapp.jpa.DbRepoImpl;
import cloudapp.jpa.SqlGenerator;

public class JdbcConfig {

	@Bean
	public SqlGenerator sqlGenerator() {
		return new SqlGenerator();
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/deletenow");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public DbRepo dbRepo() {
		return new DbRepoImpl();
	}
}
