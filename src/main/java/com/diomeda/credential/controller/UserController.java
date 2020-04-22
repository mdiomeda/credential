package com.diomeda.credential.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.diomeda.credential.model.UserAccount;
import com.diomeda.credential.service.UserService;
import com.diomeda.credential.util.CustomErrorType;

@RestController
@RequestMapping("api")
public class UserController extends BaseController{

	@Autowired
	UserService userService;

	// ------------------- Retrieve All -------------------
	@GetMapping(value = "/user")
	public ResponseEntity<?> listAllUsers() {
		List<UserAccount> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single User by Id -------------------

	@GetMapping(value = "/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
		UserAccount user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(new CustomErrorType(getMessage("user.single.error", id)), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// ------------------- Update a User -------------------

	@PutMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, UserAccount user) {

		UserAccount currentUser = userService.findById(id);

		if (currentUser == null) {
			return new ResponseEntity<>(new CustomErrorType(getMessage("user.single.error", id)),
					HttpStatus.NOT_FOUND);
		}

		currentUser.setId(user.getId());
		currentUser.setName(user.getName());
		currentUser.setPassword(user.getPassword());
		currentUser.setStatus(user.getStatus());
		currentUser.setPerson(user.getPerson());

		userService.updateUser(currentUser);
		return new ResponseEntity<>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User -------------------

	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {

		UserAccount user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(new CustomErrorType(getMessage("user.single.error", id)),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUser(user);
		return new ResponseEntity<UserAccount>(HttpStatus.NO_CONTENT);
	}
}