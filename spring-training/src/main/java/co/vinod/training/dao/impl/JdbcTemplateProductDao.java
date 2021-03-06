package co.vinod.training.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.vinod.training.dao.DaoException;
import co.vinod.training.dao.ProductDao;
import co.vinod.training.entity.Product;

@Repository("jtDao")
public class JdbcTemplateProductDao implements ProductDao {

	// in practice, required should be "true"
	@Autowired(required = false)
	private JdbcTemplate template;
	

	// a strategy object that knows how to convert a ResultSet's
	// current row into an object of Product class.
	// An arrow function (lambda expression) is the body of the ONLY function
	// in the interface of which variable you are assigning this arrow function.
	private RowMapper<Product> rowMapper = (rs, index) -> {
		Product p = new Product();
		p.setProductId(rs.getInt("product_id"));
		p.setProductName(rs.getString("product_name"));
		p.setCategoryId(rs.getInt("category_id"));
		p.setSupplierId(rs.getInt("supplier_id"));
		p.setQuantityPerUnit(rs.getString("quantity_per_unit"));
		p.setUnitPrice(rs.getDouble("unit_price"));
		p.setUnitsInStock(rs.getInt("units_in_stock"));
		p.setUnitsOnOrder(rs.getInt("units_on_order"));
		p.setReorderLevel(rs.getInt("reorder_level"));
		p.setDiscontinued(rs.getInt("discontinued"));
		return p;
	};

	@Override
	public void addNewProduct(Product product) throws DaoException {
		template.update("insert into products values(?,?,?,?,?,?,?,?,?,?)", 
			product.getProductId(),
			product.getProductName(),
			product.getCategoryId(),
			product.getSupplierId(),
			product.getQuantityPerUnit(),
			product.getUnitPrice(),
			product.getUnitsInStock(),
			product.getUnitsOnOrder(),
			product.getReorderLevel(),
			product.getDiscontinued());
		
	}

	@Override
	public Product getProductById(Integer productId) throws DaoException {
		String sql = "select * from products where product_id=?";
		return template.queryForObject(sql, rowMapper, productId);
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		String sql = "update products set product_name=?,"
				+ "category_id=?, supplier_id=?, quantity_per_unit=?,"
				+ "unit_price=?, units_in_stock=?, units_on_order=?,"
				+ "reorder_level=?, discontinued=? where product_id=?";
		template.update(sql, 
				product.getProductName(),
				product.getCategoryId(),
				product.getSupplierId(),
				product.getQuantityPerUnit(),
				product.getUnitPrice(),
				product.getUnitsInStock(),
				product.getUnitsOnOrder(),
				product.getReorderLevel(),
				product.getDiscontinued(),
				product.getProductId());
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		template.update("delete from products where product_id=?", productId);
	}

	@Override
	public int count() throws DaoException {
		// 1row 1column output --> use template.queryForObject(..)
		String sql = "select count(*) from products";
		return template.queryForObject(sql, Integer.class);
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		String sql = "select * from products";
		return template.query(sql, rowMapper);
	}

	@Override
	public List<Product> getProductsByCategory(Integer categoryId) throws DaoException {
		String sql = "select * from products where category_id = ?";
		return template.query(sql, rowMapper, categoryId);
	}

	@Override
	public List<Product> getProductsBySupplier(Integer supplierId) throws DaoException {
		String sql = "select * from products where supplier_id = ?";
		return template.query(sql, rowMapper, supplierId);
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		String sql = "select * from products where unit_price between ? and ?";
		return template.query(sql, rowMapper, min, max);
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		String sql = "select * from products where discontinued=1";
		return template.query(sql, rowMapper);
	}

	@Override
	public List<Product> getOutOfStockProducts() throws DaoException {
		
		// Technically we have to catch the actual spring specific exception
		// and re-throw the same as a DaoException (decorator pattern) for 
		// facilitating exception funneling. We are not doing this purposely,
		// because this is a redundant code, which is also a cross-cutting concern.
		// We can use AOP to address this issue, and let spring convert any exception
		// thrown into a DaoException automatically, not only from this class, but also
		// from any of the *Dao implementations.
		
		//try {
			String sql = "select * from products where units_in_stock=0";
			return template.query(sql, rowMapper);
		//} catch (DataAccessException e) {
		//	throw new DaoException(e); // exception funneling
		//}
	}

}
