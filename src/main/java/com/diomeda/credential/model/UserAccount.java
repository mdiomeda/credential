package com.diomeda.credential.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the UserAccount database table.
 * 
 */
@Entity
@NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u")
public class UserAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="jpaPkSeq", sequenceName="user_account_id_seq", allocationSize=1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaPkSeq")
	private Long id;

	private String status;

	private String name;

	@JsonIgnore
	private String password;

	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	// bi-directional many-to-one association to Roles
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "rol_user_account", joinColumns = @JoinColumn(name = "user_account_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles;


	public UserAccount() {
	}

	public Rol addRol(Rol rol) {
		getRoles().add(rol);
		rol.getUsersAccount().add(this);

		return rol;
	}

	public Rol removeRol(Rol rol) {
		getRoles().remove(rol);
		rol.getUsersAccount().remove(this);
		return rol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Person getPerson() {
		return this.person;
	}

	public Set<Rol> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
}