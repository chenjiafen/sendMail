package com.itheima.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.bean.User;
import com.itheima.dao.BrithdayDao;

public class BirthdayService {

	public List<User> findList() throws SQLException {
		
		BrithdayDao bd=new BrithdayDao();
		return bd.finList();
	}

}
