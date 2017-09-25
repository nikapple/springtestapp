package com.myapp.dao;

import javax.sql.DataSource;

import com.myapp.beans.User;

public interface UserDao {
	boolean validateUser(User user);
	int insertUser(User user);
}
