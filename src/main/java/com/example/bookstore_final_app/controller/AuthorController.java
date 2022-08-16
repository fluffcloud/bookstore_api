package com.example.bookstore_final_app.controller;

import com.example.bookstore_final_app.dto.AuthorDTO;
import com.example.bookstore_final_app.entity.Author;
import com.example.bookstore_final_app.error_handling.ResourceNotFoundException;
import com.example.bookstore_final_app.services.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/addAuthor")
    public ResponseEntity<?> addAuthor(@RequestBody AuthorDTO dto){
        // convert DTO to entity
        Author new_author = modelMapper.map(dto, Author.class);
        Set<Author> existing_authors = authorService.returnAllAuthors();
        for (Author existing_author: existing_authors){
            if (existing_author.getName().equals(new_author.getName()))
                return ResponseEntity.badRequest().body("Author already exists! Author Name : "+ new_author.getName());;
        }
        authorService.saveAuthor(new_author);
        return new ResponseEntity<>(new_author, HttpStatus.CREATED);
    }

    @GetMapping("/ReturnAuthorById/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") int id) {
        Author author = authorService.returnAuthorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Author with Id : "+id));
        AuthorDTO authorResponse = modelMapper.map(author, AuthorDTO.class);
        return new ResponseEntity<>(authorResponse,HttpStatus.OK);
    }


}
