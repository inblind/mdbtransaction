package com.learn.api.controller;


import com.learn.api.repositories.PersonRepository;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.learn.api.models.Person;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Person> getAllPerson() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Person getpersonById(@PathVariable("id") ObjectId id) {
        return repository.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifypersonById(@PathVariable("id") ObjectId id, @Valid @RequestBody Person person) {
        person.setId(id);
        repository.save(person);
    }


    //======= POC Starts ========

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Person createPerson(@Valid @RequestBody Person person) {

        repository.save(person);
        return person;
    }

    @RequestMapping(value = "/transactional", method = RequestMethod.POST)
    public Person createPersonTransaction(@Valid @RequestBody Person person) {
        person.setId(ObjectId.get());
        repository.savePersonTransactional(person);
        return person;
    }

    @RequestMapping(value = "/mongoTemplateTransactional", method = RequestMethod.POST)
    public Person createPersonMongoTemplate(@Valid @RequestBody Person person) {
        //Person.setId(ObjectId.get());
        repository.savePersonMongoTemplateTransactional(person);
        return person;
    }

    @RequestMapping(value = "/mongoTemplateWithSessionTransactional", method = RequestMethod.POST)
    public Person createPersonMongoTemplateWithSession(@Valid @RequestBody Person person) {
        //Person.setId(ObjectId.get());
        repository.savePersonMongoTemplateWithSessionTransactional(person);
        return person;
    }


    @RequestMapping(value = "/repositoryTransactional", method = RequestMethod.POST)
    public Person createPersonError(@Valid @RequestBody Person person) {
        //Person.setId(ObjectId.get());
        repository.savePersonRepositoryTransactional(person, repository);
        return person;
    }
    //======= POC Ends ========

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteperson(@PathVariable ObjectId id) {
        repository.delete(repository.findById(id));
    }
}