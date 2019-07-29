package co.vinod.training.programs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import co.vinod.training.cfg.AppConfig1;
import co.vinod.training.dao.DaoException;
import co.vinod.training.dao.ProductDao;

public class P01_GetProductCount {

	public static void main(String[] args) throws DaoException {
		ProductDao dao;
		
		// spring container
		AnnotationConfigApplicationContext ctx;
		ctx = new AnnotationConfigApplicationContext(AppConfig1.class);
		
		// ClassPathXmlApplicationContext ctx;
		// ctx = new ClassPathXmlApplicationContext("context1.xml");
		
		// dao = new DummyProductDao(); // tight coupling
		
		dao = ctx.getBean("jdbc", ProductDao.class); // loose coupling
		
		int pc = dao.count();
		System.out.println("There are " + pc + " products.");
		
		ctx.close();
	}
}