package com.diomeda.credential.service;

import java.util.List;
import com.diomeda.credential.model.Person;

public interface PersonService {
	
	Person findById(Long id);

	Person savePerson(Person person);

	Person updatePerson(Person person);

	void deletePerson(Person person);

	List<Person> findAllPersons();

	boolean isPersonExist(Person person);
}