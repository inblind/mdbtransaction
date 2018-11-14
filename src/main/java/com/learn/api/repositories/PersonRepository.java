package com.learn.api.repositories;

import com.learn.api.models.Person;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PersonRepository extends MongoRepository<Person, String>, PersonRepositoryCustom {
    Person findBy_id(ObjectId _id);
}