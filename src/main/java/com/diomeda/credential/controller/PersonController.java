package com.diomeda.credential.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.diomeda.credential.model.Person;
import com.diomeda.credential.service.PersonService;
import com.diomeda.credential.util.CustomErrorType;

@RestController
@RequestMapping("api")
public class PersonController extends BaseController{

    @Autowired
    PersonService personService;

    @Autowired
    ResourceBundleMessageSource message;

    // -------------------Retrieve All Persons -------------------
    @GetMapping(value = "/person")
    public ResponseEntity<List<Person>> listAllPersons() {
        
        List<Person> persons = personService.findAllPersons();
        if (persons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT );
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    // -------------------Retrieve Single Person -------------------

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPerson(@PathVariable("id") Long id) {
        Person person = personService.findById(id);
        if (person == null) {
            return new ResponseEntity<>(new CustomErrorType(getMessage("person.single.error", id)), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    // ------------------- Create a Person -------------------

    @PostMapping(value = "/person", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> createPerson(Person person, UriComponentsBuilder ucBuilder) {

        if (personService.isPersonExist(person)) {
            return new ResponseEntity<>(
                    new CustomErrorType(getMessage("person.create.error", person.getId())),
                    HttpStatus.CONFLICT);
        }

        Person result = personService.savePerson(person);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/person/{id}").buildAndExpand(result.getFirstName()).toUri());
        return new ResponseEntity<>(result, headers , HttpStatus.CREATED);
    }

    // ------------------- Update a Person -------------------

    @PutMapping(value = "/person/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> updatePerson(@PathVariable("id") Long id, Person person) {

        Person currentPerson = personService.findById(id);

        if (currentPerson == null) {
            return new ResponseEntity<>(new CustomErrorType(getMessage("person.single.error", id)),
                    HttpStatus.NOT_FOUND);
        }

        person.setId(currentPerson.getId());

        personService.updatePerson(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    // ------------------- Delete a Person -------------------

    @DeleteMapping(value = "/person/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable("id") Long id) {

        Person person = personService.findById(id);
        if (person == null) {
            return new ResponseEntity<>(new CustomErrorType(getMessage("person.single.error", id)),
                    HttpStatus.NOT_FOUND);
        }
        personService.deletePerson(person);
        return new ResponseEntity<Person>(HttpStatus.OK);
    }

}
