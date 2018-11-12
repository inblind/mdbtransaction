package com.learn.api;

import com.learn.api.models.Pets;
import com.learn.api.repositories.PetsRepository;
import com.mongodb.MongoCommandException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

	@Autowired
	private PetsRepository repository;

	@Test
	public void contextLoads() {
	}



	@Test
	@Transactional
	public void whenCountDuringMongoTransaction_thenException() {

		try{
		repository.save(new Pets(null, "test111", "test", "test"));
		repository.save(new Pets(null, "test", "test", "test"));
		repository.count();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}


}
