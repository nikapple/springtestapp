package com.myapp.dao;

import com.myapp.beans.User;

public interface UserDao {
	boolean validateUser(User user);
	int insertUser(User user);
	boolean checkUserExists(User user);
	User getUserInfo(User user);
}
