package co.vinod.training.programs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import co.vinod.training.cfg.AppConfig4;
import co.vinod.training.dao.DaoException;
import co.vinod.training.dao.ProductDao;

public class P01_GetProductCount {

	public static void main(String[] args) throws DaoException {
		System.out.println("Starting the app...");
		ProductDao dao;

		AnnotationConfigApplicationContext ctx;
		ctx = new AnnotationConfigApplicationContext(AppConfig4.class);

		dao = ctx.getBean("jdbc", ProductDao.class); 
		int pc = dao.count();
		System.out.println("There are " + pc + " products.");

		ctx.close();
	}
}
