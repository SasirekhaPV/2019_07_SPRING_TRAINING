package co.vinod.training.programs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import co.vinod.training.cfg.AppConfig5;

public class P02_TestingJdbcTemplate {

	static JdbcTemplate tpl;
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig5.class);
		tpl = ctx.getBean(JdbcTemplate.class);
		
		System.out.println("Please wait...");
		printProductCount();
		printProductNameForId(44);
		
		ctx.close();
	}

	static void printProductNameForId(int productId) {
		// query --> 1 row, 1 column
		// template function to use --> queryForObject
		String sql = "select product_name from products where product_id = ?";
		String pname = tpl.queryForObject(sql, String.class, productId);
		System.out.println("Name = " + pname);
	}

	static void printProductCount() {
		String sql = "select count(*) from products";
		int pc = tpl.queryForObject(sql, Integer.class);
		System.out.println("There are " + pc + " products.");
	}
}
