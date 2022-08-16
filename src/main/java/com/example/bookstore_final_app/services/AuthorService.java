package com.example.bookstore_final_app.services;

import com.example.bookstore_final_app.entity.Author;
import com.example.bookstore_final_app.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public Author saveAuthor(Author author){ return authorRepository.save(author);}
    public Optional<Author> returnAuthorById(Integer id){ return authorRepository.findById(id);}
    public Author returnAuthorByAuthorName(String authorName){
        return authorRepository.findAuthorByName(authorName);
    }
    public Set<Author> returnAllAuthors(){
        Set<Author> author_set = new HashSet<>();
        List<Author> author_list = authorRepository.findAll();
        for (Author author : author_list)
            author_set.add(author);
        return author_set;
    };

}
