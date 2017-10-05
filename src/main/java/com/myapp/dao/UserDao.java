package com.myapp.dao;

import java.util.List;

import com.myapp.beans.Book;
import com.myapp.beans.User;

public interface UserDao {
	boolean validateUser(User user);
	int insertUser(User user);
	boolean checkUserExists(User user);
	User getUserInfo(User user);
	List<Book> getAssignedBooks(User user);
}
