package com.learn.api.repositories;

import com.learn.api.models.Pets;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.ClientSession;
import org.bson.Document;
import org.springframework.transaction.annotation.Transactional;

public class PetsTransactionalRepository {


    @Transactional
    public void savePetError(Pets pet, MongoClient client){

        MongoDatabase database = client.getDatabase("rest_tutorial");

        try(ClientSession session = client.startSession()){

            session.startTransaction();

            //MongoCollection<Pets> collection =

            //collection.insertOne(session, pojoToDoc(pet));

            //collection.insertOne(session, new Pets(null, "test", "test", "test"));
            //collection.insertOne(session, pet);
            database.getCollection("pets").insertOne(session, pojoToDoc(pet));
            pet.setName("secondDog");
            database.getCollection("pets").insertOne(session, pojoToDoc(pet));
            pet.setName("thirdDog");
            database.getCollection("pets").insertOne(session, pojoToDoc(pet));

            Integer.parseInt(pet.getSpecies());

            session.commitTransaction();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            throw new MongoException("error on inserting: "+ex.getMessage());
        }
    }

    private Document pojoToDoc(Pets pet){
        Document doc = new Document();

        doc.put("name",pet.getName());
        doc.put("species",pet.getSpecies());
        doc.put("breed",pet.getBreed());

        return doc;
    }

    @Transactional
    public void savePet(Pets pet, PetsRepository repository) {
        repository.save(pet);
    }

    private void testError() throws Exception {

        throw new Exception("forced exception");

    }
}
