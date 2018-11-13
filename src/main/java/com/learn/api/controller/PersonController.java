package com.learn.api.controller;


import com.learn.api.repositories.PersonRepository;
import com.learn.api.repositories.PersonTransactionalRepository;
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

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoClient client;

    private PersonTransactionalRepository transactionalRepo;

    public PersonController() {
        this.transactionalRepo = new PersonTransactionalRepository();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Person> getAllPerson() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Person getpersonById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifypersonById(@PathVariable("id") ObjectId id, @Valid @RequestBody Person Person) {
        Person.setId(id);
        repository.save(Person);
    }


    //======= POC Starts ========

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Person createPerson(@Valid @RequestBody Person Person) {

        transactionalRepo.savePerson(Person, repository);
        return Person;
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public Person createPersonTransaction(@Valid @RequestBody Person Person) throws Exception {
        Person.setId(ObjectId.get());
        transactionalRepo.savePersonTransaction(Person, client);
        return Person;
    }

    @RequestMapping(value = "/mongoTemplate", method = RequestMethod.POST)
    public Person createPersonMongoTemplate(@Valid @RequestBody Person Person) throws Exception {
        //Person.setId(ObjectId.get());
        transactionalRepo.savePersonMongoTemplate(Person, client, mongoTemplate);
        return Person;
    }

    //======= POC Ends ========

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteperson(@PathVariable ObjectId id) {
        repository.delete(repository.findBy_id(id));
    }
}