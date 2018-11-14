package com.learn.api.repositories;

import com.learn.api.models.Person;
import com.learn.api.models.Address;
import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.ClientSession;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoClient client;

    //method to insert into databse with ClientSession and databse.getCollection.insert (old fashion way)
    @Transactional
    public void savePersonTransactional(Person p) {

        MongoDatabase database = client.getDatabase("POC_DB_4O");

        try (ClientSession session = client.startSession()) {

            session.startTransaction();

            if (p.address != null)
                database.getCollection("address").insertOne(session, addressToDoc(p.getAddress()));

            database.getCollection("person").insertOne(session, personToDoc(p, p.getAddress()._id));

            Integer.parseInt(p.getLastName());

            session.commitTransaction();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MongoException("error on inserting: " + ex.getMessage());
        }
    }


    //method to insert into database with mongotemplate using transactional
    //this is not working, transactional will not do rollback on error
    @Transactional
    public void savePersonMongoTemplateTransactional(Person p) {

        mongoTemplate.insert(p);
        mongoTemplate.insert(new Person("secondPerson", "lastName", new Address("street", "number", "61410")));
        Integer.parseInt(p.getLastName());
        mongoTemplate.insert(new Person("thirdPerson", "lastName", null));
    }

    @Transactional
    public void savePersonMongoTemplateWithSessionTransactional(Person p) {

        ClientSession session = client.startSession();
        session.startTransaction();
        try {
            mongoTemplate.withSession( () -> session).
                    execute( action -> {

                        action.insert(p);
                        action.insert(new Person("secondPerson", "lastName", new Address("street", "number", "61410")));
                        Integer.parseInt(p.getLastName());
                        action.insert(new Person("thirdPerson", "lastName", new Address("street", "number", "61410")));

                        return p;
                    }
            );
            session.commitTransaction();
        } catch (Exception ex) {
            session.abortTransaction();
            System.out.println(ex.getMessage());
            throw new MongoException("error on inserting: " + ex.getMessage());
        } finally {
            session.close();
        }

    }

    //method to insert into database with repository using transactional
    //this is not working, transactional will not do rollback on error
    @Transactional
    public void savePersonRepositoryTransactional(Person p, PersonRepository repository) {

        repository.save(p);
        repository.save(new Person("secondPerson", "lastName", new Address("street", "number", "61410")));
        Integer.parseInt(p.getLastName());
        repository.save(new Person("thirdPerson", "lastName", null));
    }

    private Document personToDoc(Person p, ObjectId addressId) {
        Document doc = new Document();
        doc.put("_id", p._id);
        doc.put("firstName", p.getFirstName());
        doc.put("lastName", p.getLastName());
        doc.put("address", addressId);

        return doc;
    }

    private Document addressToDoc(Address address) {
        Document doc = new Document();
        if (address._id == null)
            address.setId(ObjectId.get());
        doc.put("_id", address._id);
        doc.put("street", address.getStreet());
        doc.put("number", address.getNumber());
        doc.put("zipCode", address.getZipCode());

        return doc;
    }

    private void testError() throws Exception {

        throw new Exception("forced exception");

    }
}
