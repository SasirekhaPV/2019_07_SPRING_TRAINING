package co.vinod.training.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.vinod.training.dao.DaoException;
import co.vinod.training.dao.ProductDao;
import co.vinod.training.entity.Product;
import lombok.Setter;

@Scope("prototype")
@Repository("jdbc")
@Setter
public class JdbcProductDao implements ProductDao {

	private String driver;
	private String url;
	private String username;
	private String password;
	private Connection connection;

	// represents a DB connection pool
	@Autowired(required = false)
	// @Qualifier("pool")
	private DataSource dataSource;
	
	public JdbcProductDao() {
		System.out.println("JdbcProductDao instantiated!");
	}
	
	

	private Connection getConnection() throws ClassNotFoundException, SQLException {

		if (dataSource != null) {
			return dataSource.getConnection();
		}

		if (connection != null) {
			return connection;
		}

		Class.forName(driver);
		return DriverManager.getConnection(url, username, password);
	}

	@Override
	public int count() throws DaoException {

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("select count(*) from products");
				ResultSet rs = stmt.executeQuery();) {
			rs.next();
			return rs.getInt(1);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}

	}

	public JdbcProductDao(String driver, String url, String username, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public void addNewProduct(Product product) throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

	@Override
	public Product getProductById(Integer productId) throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

	@Override
	public List<Product> getProductsByCategory(Integer categoryId) throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

	@Override
	public List<Product> getProductsBySupplier(Integer supplierId) throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

	@Override
	public List<Product> getOutOfStockProducts() throws DaoException {
		throw new DaoException("Not implemented yet!");
	}

}
