package co.vinod.training.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import co.vinod.training.dao.DaoException;
import co.vinod.training.dao.ProductDao;
import co.vinod.training.entity.Product;

@Repository("htDao")
@SuppressWarnings("unchecked")
public class HibernateTemplateProductDao implements ProductDao {

	@Autowired(required = false)
	private HibernateTemplate template;

	@Override
	public void addNewProduct(Product product) throws DaoException {
		template.persist(product);
	}

	@Override
	public Product getProductById(Integer productId) throws DaoException {
		return template.get(Product.class, productId);
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		template.merge(product); // select from DB, compare with this object, update only if required
		// if(product.getUnitPrice()>20.0) throw new DaoException("Price is too much!");
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		Product product = this.getProductById(productId);
		if (product == null) {
			throw new DaoException("Invalid product id for deletion: " + productId);
		}
		template.delete(product);
	}

	@Override
	public int count() throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		Projection projection = Projections.rowCount();
		dc.setProjection(projection);
		return ((Long) template.findByCriteria(dc).get(0)).intValue();
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getProductsByCategory(Integer categoryId) throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.eq("categoryId", categoryId));
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getProductsBySupplier(Integer supplierId) throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.eq("supplierId", supplierId));
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.between("unitPrice", min, max));
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.eq("discontinued", 1));
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getOutOfStockProducts() throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.eq("unitsInStock", 0));
		return (List<Product>) template.findByCriteria(dc);
	}

}
