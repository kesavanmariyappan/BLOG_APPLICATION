package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface signuprepo extends JpaRepository<Username,Long> {
	
	Username findByUnameAndPassword(String uname, String password);

	
}
