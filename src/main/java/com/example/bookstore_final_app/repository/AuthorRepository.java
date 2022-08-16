package com.example.bookstore_final_app.repository;

import com.example.bookstore_final_app.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Author findAuthorByName(String name);

}
