package com.learn.api.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="address")
@TypeAlias("address")
public class Address {

    @Id
    public ObjectId _id;

    public String street;
    public String number;
    public String zipCode;

    // Constructors
    public Address() {}

    public Address(String street, String number, String zipCode) {
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
    }

    @Id
    public String get_id() {
        return _id == null ? null : _id.toHexString();
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
