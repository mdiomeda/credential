package com.diomeda.credential.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the Person database table.
 * 
 */
@Entity
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String lastName;

	private String email;

	private String firstName;

	private String phone;


	// bi-directional many-to-one association to Usuario
	@JsonIgnore
	@OneToMany(mappedBy = "person")
	private List<UserAccount> usersAccount;

	public Person() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<UserAccount> getUsersAccount() {
		return usersAccount;
	}

	public void setUsersAccount(List<UserAccount> usersAccount) {
		this.usersAccount = usersAccount;
	}

	public UserAccount addUserAccount(UserAccount userAccount) {
		getUsersAccount().add(userAccount);
		userAccount.setPerson(this);

		return userAccount;
	}

	public UserAccount removeUserAccount(UserAccount userAccount) {
		getUsersAccount().remove(userAccount);
		userAccount.setPerson(null);

		return userAccount;
	}

}