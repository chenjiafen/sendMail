package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.bean.User;
import com.itheima.utils.DataSourceUtils;

public class BrithdayDao {

	public List<User> finList() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="Select * from user where birthday like '%-10-31%' ";
		List<User> query=qr.query(sql, new BeanListHandler<User>(User.class));
		return query;
	}

}
