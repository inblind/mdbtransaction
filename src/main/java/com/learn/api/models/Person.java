package com.learn.api.models;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="person")
@TypeAlias("person")
public class Person{
    @Id
    public ObjectId _id;

    public String firstName;
    public String lastName;

    public List<Address> addresses;

    // Constructors
    public Person() {}

    public Person(String firstName, String lastName, List<Address>  addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = addresses;
    }


    @Id
    public String get_id() {
        return _id == null ? null : _id.toHexString();
    }


    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address>  getAddresses() {
        return addresses;
    }

    public void setAddress(List<Address>  address) {
        this.addresses = addresses;
    }
}