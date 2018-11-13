package com.learn.api.repositories;

import com.learn.api.models.Person;
import com.learn.api.models.Address;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.ClientSession;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

public class PersonTransactionalRepository {


    @Transactional
    public void savePersonTransaction(Person p, MongoClient client){

        MongoDatabase database = client.getDatabase("POC_DB_4O");

        try(ClientSession session = client.startSession()){

            session.startTransaction();

            database.getCollection("person").insertOne(session, pojoToDoc(p));
            p.setFirstName("secondPerson");
            database.getCollection("person").insertOne(session, pojoToDoc(p));
            p.setFirstName("thirdPerson");
            database.getCollection("person").insertOne(session, pojoToDoc(p));

            Integer.parseInt(p.getLastName());

            session.commitTransaction();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            throw new MongoException("error on inserting: "+ex.getMessage());
        }
    }

    @Transactional
    public void savePersonMongoTemplate(Person p, MongoClient client, MongoTemplate mongoTemplate){

        mongoTemplate.insert(p);
        mongoTemplate.insert(new Person("secondPerson", "lastName", new Address("street", "number", "61410")));
        Integer.parseInt(p.getLastName());
        mongoTemplate.insert(new Person("thirdPerson", "lastName", null));
    }

    @Transactional
    public void savePersonError(Person p, MongoClient client){

        MongoDatabase database = client.getDatabase("POC_DB_4O");

        try(ClientSession session = client.startSession()){

            session.startTransaction();

            database.getCollection("person").insertOne(session, pojoToDoc(p));
            p.setFirstName("secondPerson");
            database.getCollection("person").insertOne(session, pojoToDoc(p));
            p.setFirstName("thirdPerson");
            database.getCollection("person").insertOne(session, pojoToDoc(p));

            Integer.parseInt(p.getLastName());

            session.commitTransaction();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            throw new MongoException("error on inserting: "+ex.getMessage());
        }
    }

    private Document pojoToDoc(Person pet){
        Document doc = new Document();

        doc.put("firstName",pet.getFirstName());
        doc.put("lastName",pet.getLastName());
        doc.put("address",pet.getAddress());

        return doc;
    }

    @Transactional
    public void savePerson(Person person, PersonRepository repository) {
        repository.save(person);
    }

    private void testError() throws Exception {

        throw new Exception("forced exception");

    }
}
