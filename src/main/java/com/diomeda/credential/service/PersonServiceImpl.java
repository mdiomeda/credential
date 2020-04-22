package com.diomeda.credential.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diomeda.credential.model.Person;
import com.diomeda.credential.repository.PersonRepository;


@Service("personService")
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public Person findById(Long id) {
		return personRepository.findById(id).orElse(null);
	}

	@Override
	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	@Override
	public Person updatePerson(Person person) {
		return savePerson(person);
	}

	@Override
	public void deletePerson(Person person) {
		personRepository.delete(person);
	}

	@Override
	public List<Person> findAllPersons() {
		return personRepository.findAll();
	}

	@Override
	public boolean isPersonExist(Person person) {
		return findById(person.getId()) != null;
	}

}
