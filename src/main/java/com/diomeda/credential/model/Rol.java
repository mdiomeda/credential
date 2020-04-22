package com.diomeda.credential.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="jpaRolPkSeq", sequenceName="rol_id_seq", allocationSize=1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaRolPkSeq")
	private Long id;

	private String description;

	private String name;

	// bi-directional many-to-many association to User
	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Set<UserAccount> usersAccount;

	public Rol() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserAccount> getUsersAccount() {
		return usersAccount;
	}

	public void setUsersAccount(Set<UserAccount> users) {
		this.usersAccount = users;
	}

}