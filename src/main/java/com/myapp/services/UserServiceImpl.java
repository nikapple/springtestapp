package com.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.beans.Book;
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
		return userDao.validateUser(user);
	}

	@Override
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public boolean checkUserExists(User user) {
		return userDao.checkUserExists(user);
	}

	@Override
	public User getUserInfo(User user) {
		return userDao.getUserInfo(user);
	}

	@Override
	public List<Book> getAssignedBooks(User user) {
		return userDao.getAssignedBooks(user);
	}

}
