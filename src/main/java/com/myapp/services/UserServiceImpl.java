package com.myapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.beans.User;
import com.myapp.dao.UserDao;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean validateUser(User user) {
		// TODO Auto-generated method stub
		System.out.println("Validating user service");
		return userDao.validateUser(user);
	}

	@Override
	public int insertUser(User user) {
		int rowsUpdated = userDao.insertUser(user);
		System.out.println(rowsUpdated);
		return rowsUpdated;
	}

}
