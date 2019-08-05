package co.vinod.training.cfg;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource({ "classpath:jdbc.properties" })
@ComponentScan(basePackages = { "co.vinod.training.dao" })
public class AppConfig6 {
	@Value("${jdbc.driver}")
	String driver;
	@Value("${jdbc.url}")
	String url;
	@Value("${jdbc.username}")
	String username;
	@Value("${jdbc.password}")
	String password;

	@Bean
	public JdbcTemplate template(DataSource ds) { // dependency injection
		return new JdbcTemplate(ds); // manual wiring
	}

	@Bean
	public DataSource ds() {
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName(driver);
		bds.setUrl(url);
		bds.setPassword(password);
		bds.setUsername(username);

		bds.setInitialSize(10);
		bds.setMaxTotal(100);
		bds.setMaxIdle(50);
		bds.setMinIdle(10);

		return bds;
	}

}
