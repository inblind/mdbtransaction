package com.learn.api.repositories;

import com.learn.api.models.Person;
import org.springframework.transaction.annotation.Transactional;

public interface PersonRepositoryCustom {

    @Transactional
    void savePersonTransactional(Person p);

    @Transactional
    void savePersonMongoTemplateTransactional(Person p);

    @Transactional
    void savePersonRepositoryTransactional(Person p, PersonRepository repository);

    @Transactional
    void savePersonMongoTemplateWithSessionTransactional(Person p);

}
