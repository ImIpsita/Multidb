package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.postgre.entity.User;
import com.example.demo.postgre.repo.UserRepo;
import com.example.demo.sql.entity.Product;
import com.example.demo.sql.repo.ProductRepo;

@SpringBootApplication
public class MultipledbApplication implements CommandLineRunner {
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	UserRepo userRepo;

	public static void main(String[] args) { 
		SpringApplication.run(MultipledbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Product p = new Product("Test", 100L, "testing");
		
		productRepo.save(p);
		System.out.println("Product inserted!!");
		
         User user = new User("ipsita", "test@gmail.com");
         userRepo.save(user);
         System.out.println("User inserted!!");
	}

}
