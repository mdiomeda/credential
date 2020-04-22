package com.diomeda.credential.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diomeda.credential.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
