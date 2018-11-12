package com.learn.api.controller;


import com.learn.api.repositories.PetsRepository;
import com.learn.api.repositories.PetsTransactionalRepository;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.learn.api.models.Pets;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetsController {

    @Autowired
    private PetsRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoClient client;

    private PetsTransactionalRepository transactionalRepo;

    public PetsController() {
        this.transactionalRepo = new PetsTransactionalRepository();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Pets> getAllPets() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pets getPetById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifyPetById(@PathVariable("id") ObjectId id, @Valid @RequestBody Pets pets) {
        //pets.set_id(id);
        repository.save(pets);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Pets createPet(@Valid @RequestBody Pets pets) {
        //pets.set_id(ObjectId.get());

        if(transactionalRepo == null)
            this.transactionalRepo = new PetsTransactionalRepository();

        transactionalRepo.savePet(pets, repository);
        //transactionalRepo.savePetError(pets);
        return pets;
    }

    @RequestMapping(value = "/error", method = RequestMethod.POST)
    public Pets createPetError(@Valid @RequestBody Pets pets) throws Exception {
        //pets.set_id(ObjectId.get());
        transactionalRepo.savePetError(pets, client);
        return pets;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePet(@PathVariable ObjectId id) {
        repository.delete(repository.findBy_id(id));
    }
}