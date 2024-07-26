package com.example.demo.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class blogservice {

    @Autowired
    private blogreop blogRepo;

    public List<blog> getAllBlogs() {
        return blogRepo.findAll();
    }
}
