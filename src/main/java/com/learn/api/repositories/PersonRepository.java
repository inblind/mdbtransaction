package com.learn.api.repositories;

import com.learn.api.models.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
    Person findBy_id(ObjectId _id);
}