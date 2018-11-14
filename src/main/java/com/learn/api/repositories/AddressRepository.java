package com.learn.api.repositories;

import com.learn.api.models.Address;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {
    Address findById(ObjectId id);
}