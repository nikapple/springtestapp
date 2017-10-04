package com.myapp.services;

import com.myapp.beans.User;

public interface UserService {

	boolean validateUser(User user);
	int insertUser(User user);
	boolean checkUserExists(User user);
	User getUserInfo(User user);
}
