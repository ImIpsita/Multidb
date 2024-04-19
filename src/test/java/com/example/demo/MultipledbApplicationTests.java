package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.postgre.entity.User;
import com.example.demo.postgre.repo.UserRepo;
import com.example.demo.sql.entity.Product;
import com.example.demo.sql.repo.ProductRepo;

@SpringBootTest
class MultipledbApplicationTests {
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	UserRepo userRepo;

//	@Test
//	void contextLoads() {
//	}
//	
	@Test
	public void dbTest() {
		
		Product p = new Product();
		p.setPrice(44L);
		p.setProductDesc("Test-product");
		p.setProductName("Test");
		
		productRepo.save(p);
		
		User user = new User();
		user.setEmail("test@gmail.com");
		user.setUserName("ipsita");
		
		userRepo.save(user);
	}
	

}
