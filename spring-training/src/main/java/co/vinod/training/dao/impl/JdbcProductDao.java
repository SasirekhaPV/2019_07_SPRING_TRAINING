package co.vinod.training.dao.impl;

import co.vinod.training.dao.DaoException;
import co.vinod.training.dao.ProductDao;

public class JdbcProductDao implements ProductDao {

	@Override
	public int count() throws DaoException {
		return 77;
	}

}
