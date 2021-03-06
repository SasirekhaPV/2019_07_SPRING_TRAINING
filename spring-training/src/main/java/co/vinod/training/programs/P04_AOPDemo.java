package co.vinod.training.programs;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import co.vinod.training.cfg.AppConfig7;
import co.vinod.training.dao.DaoException;
import co.vinod.training.dao.ProductDao;
import co.vinod.training.entity.Product;

public class P04_AOPDemo {

	public static void main(String[] args) throws DaoException {
		AnnotationConfigApplicationContext ctx;
		ctx = new AnnotationConfigApplicationContext(AppConfig7.class);

		ProductDao dao = ctx.getBean("htDao", ProductDao.class);

		System.out.println("dao is an instanceof " + dao.getClass().getName());

		int pc = dao.count();
		System.out.println("There are " + pc + " products.");

		Product p1 = dao.getProductById(22);
		System.out.println("p1.name = " + p1.getProductName());
		System.out.println("p1.price = $" + p1.getUnitPrice());

		try {
			p1.setUnitPrice(p1.getUnitPrice() + 1);
			dao.updateProduct(p1);
			System.out.println("Product price changed1");
			p1 = dao.getProductById(22);
			System.out.println("After updating, p1.price = $" + p1.getUnitPrice());
		} catch (DaoException e) {
			System.out.println("Sorry! could not updated product price.");
		}
		
		double min = 50.0, max = 500.0;
		List<Product> list = dao.getProductsByPriceRange(min, max);
		System.out.println("There are " + list.size() + " products between $" + min + " and $" + max);

		min = 500.0;
		max = 50.0;
		list = dao.getProductsByPriceRange(min, max);
		System.out.println("There are " + list.size() + " products between $" + min + " and $" + max);

		ctx.close();

	}
}
