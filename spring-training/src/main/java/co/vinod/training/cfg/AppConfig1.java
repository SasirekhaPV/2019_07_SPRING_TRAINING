package co.vinod.training.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import co.vinod.training.dao.ProductDao;
import co.vinod.training.dao.impl.DummyProductDao;
import co.vinod.training.dao.impl.JdbcProductDao;

@Configuration
@PropertySource({ "classpath:jdbc.properties" })
public class AppConfig1 {

	@Value("${jdbc.driver}")
	String driver;
	@Value("${jdbc.url}")
	String url;
	@Value("${jdbc.username}")
	String username;
	@Value("${jdbc.password}")
	String password;

	@Lazy // to be used with singleton beans
	@Bean(name = "dummy")
	public ProductDao dummy() {
		return new DummyProductDao();
	}

	@Scope("prototype")
	@Bean(name = { "jdbcDao", "dao" })
	public ProductDao jdbc() {
		JdbcProductDao dao = new JdbcProductDao();
		dao.setDriver(driver);
		dao.setUrl(url);
		dao.setUsername(username);
		dao.setPassword(password);
		return dao;
	}

	@Scope("prototype")
	@Bean(name = "jdbc")
	public ProductDao dao() {
		return new JdbcProductDao(driver, url, username, password);
	}

}
