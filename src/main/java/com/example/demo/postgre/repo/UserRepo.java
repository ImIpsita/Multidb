package com.example.demo.postgre.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.postgre.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

}
