package co.vinod.training.programs;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import co.vinod.training.cfg.AppConfig7;
import co.vinod.training.dao.DaoException;
import co.vinod.training.dao.ProductDao;
import co.vinod.training.entity.Product;

public class P03_TestingProductDaoMethods {

	private static ProductDao dao;
	
	public static void main(String[] args) throws DaoException {
		
		AnnotationConfigApplicationContext ctx;
		ctx = new AnnotationConfigApplicationContext(AppConfig7.class);
		
		dao = ctx.getBean("htDao", ProductDao.class);
		System.out.println("--------------------------------------------");
		// printDiscontinuedProducts();
		// printOutOfStockProducts();
		// printProductsInPriceRange();
		// updateProductPrice();
		printProductCount();
		
		System.out.println("--------------------------------------------");
		ctx.close();
		
	}

	static void printProductCount() throws DaoException {
		int pc = dao.count();
		System.out.println("There are " + pc + " products.");
	}

	static void updateProductPrice() throws DaoException {
		Product p = dao.getProductById(1);
		System.out.println(p.getProductName() + " --> $" + p.getUnitPrice());
		p.setUnitPrice(p.getUnitPrice() + 1);
		dao.updateProduct(p);
		p = dao.getProductById(1);
		System.out.println(p.getProductName() + " --> $" + p.getUnitPrice());
	}

	static void printProductsInPriceRange() throws DaoException {
		double min = 10, max=20;
		
		List<Product> list = dao.getProductsByPriceRange(min, max);
		System.out.printf("List of products between %.2f and %.2f dollars\n", min, max);
		for(Product p: list) {
			System.out.println(p.getProductName() + " --> $" + p.getUnitPrice());
		}
	}

	static void printOutOfStockProducts() throws DaoException {
		List<Product> list = dao.getOutOfStockProducts();
		System.out.println("List of discontinued products...");
		for(Product p: list) {
			System.out.println(p.getProductName() + " --> " + p.getUnitsInStock());
		}
	}

	static void printDiscontinuedProducts() throws DaoException {
		List<Product> list = dao.getDiscontinuedProducts();
		System.out.println("List of discontinued products...");
		for(Product p: list) {
			System.out.println(p.getProductName() + " --> " + p.getDiscontinued());
		}
	}
}






