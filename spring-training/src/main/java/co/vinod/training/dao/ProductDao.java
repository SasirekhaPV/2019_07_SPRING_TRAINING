package co.vinod.training.dao;

import java.util.List;

import co.vinod.training.entity.Product;

public interface ProductDao {

	// CRUD operations
	public void addNewProduct(Product product) throws DaoException;

	public Product getProductById(Integer productId) throws DaoException;

	public void updateProduct(Product product) throws DaoException;

	public void deleteProduct(Integer productId) throws DaoException;

	// QUERIES
	public int count() throws DaoException;

	public List<Product> getAllProducts() throws DaoException;

	public List<Product> getProductsByCategory(Integer categoryId) throws DaoException;

	public List<Product> getProductsBySupplier(Integer supplierId) throws DaoException;

	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException;

	public List<Product> getDiscontinuedProducts() throws DaoException;

	public List<Product> getOutOfStockProducts() throws DaoException;

}
