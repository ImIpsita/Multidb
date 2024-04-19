package com.example.demo.sql.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.sql.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

}
