package com.learn.api.repositories;

import com.learn.api.models.Person;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

public interface PersonRepositoryCustom {

    @Transactional
    public void savePersonTransaction(Person p);

    @Transactional
    public void savePersonMongoTemplate(Person p);

    @Transactional
    public void savePersonError(Person p);

}
