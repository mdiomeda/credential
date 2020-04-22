package com.diomeda.credential.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.diomeda.credential.model.UserAccount;
import com.diomeda.credential.repository.UserRepository;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	

	public UserAccount findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public UserAccount findByName(String name) {
		return userRepository.findByName(name);
	}

	public void saveUser(UserAccount user) {
		userRepository.save(user);
	}

	public void updateUser(UserAccount user){
		saveUser(user);
	}

	public void deleteUser(UserAccount user){
		userRepository.delete(user);
	}

	public List<UserAccount> findAllUsers(){
		
		return userRepository.findAll();
	}

	public boolean isUserExist(UserAccount user) {
		return findByName(user.getName()) != null;
	}

}
