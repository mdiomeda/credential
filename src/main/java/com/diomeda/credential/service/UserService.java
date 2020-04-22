package com.diomeda.credential.service;


import java.util.List;

import com.diomeda.credential.model.UserAccount;


public interface UserService {
	
	UserAccount findById(Long id);

	UserAccount findByName(String name);

	void saveUser(UserAccount user);

	void updateUser(UserAccount user);

	void deleteUser(UserAccount user);

	List<UserAccount> findAllUsers();

	boolean isUserExist(UserAccount user);
}