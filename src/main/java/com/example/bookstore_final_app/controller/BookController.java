package com.example.bookstore_final_app.controller;

import com.example.bookstore_final_app.dto.BookDTO;
import com.example.bookstore_final_app.entity.Author;
import com.example.bookstore_final_app.entity.Book;
import com.example.bookstore_final_app.error_handling.ResourceNotFoundException;
import com.example.bookstore_final_app.services.AuthorService;
import com.example.bookstore_final_app.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.*;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/addBook")
    ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO dto) {
        // convert DTO to entity
        Book book = modelMapper.map(dto, Book.class);
        Set<Author> authors =book.getAuthors();
        for (Author author: authors){
            Author existing_author = authorService.returnAuthorByAuthorName(author.getName());
            Book existing_book = bookService.returnBookByIsbn(book.getIsbn());
            if(existing_author==null){
                if (existing_book==null)
                    author.addBook(book);
                else
                    author.addBook(existing_book);
                authorService.saveAuthor(author);
            } else{
                if(existing_book==null)
                    existing_author.addBook(book);
                else
                    existing_author.addBook(existing_book);
                authorService.saveAuthor(existing_author);
            }
        }
        if (authors.isEmpty())
            book = bookService.saveBook(book);
        BookDTO bookResponse = modelMapper.map(book, BookDTO.class);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @GetMapping("/ReturnBookByTitle")
    public ResponseEntity<Set<Book>> returnBookByTitle(@RequestParam(required = true) String title) {
        Set<Book> books = new HashSet<>();
        bookService.returnBookByTitle(title).forEach(books::add);
        if (books.isEmpty()) { return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("/ReturnAllBooks")
    public ResponseEntity<Set<Book>> returnAllBooks() {
        Set<Book> books = new HashSet<>();
        bookService.returnAllBooks().forEach(books::add);
        if (books.isEmpty()) { return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("/ReturnBookById/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") int id) {
        Book book = bookService.returnBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Book with Id : "+id));
        BookDTO bookResponse = modelMapper.map(book, BookDTO.class);
        return new ResponseEntity<>(bookResponse,HttpStatus.OK);
    }

    @PutMapping(value="/UpdateBookByIsbn/{isbn}")
    ResponseEntity<String> updateBookByIsbn(@PathVariable("isbn")  @Min(1) String isbn, @Valid @RequestBody BookDTO dto) {
        // convert DTO to entity
        Book update_book = modelMapper.map(dto, Book.class);
        Set<Author> updated_authors =update_book.getAuthors();
        Book existing_book = bookService.returnBookByIsbn(isbn);

        // Existing book cannot be null
        if(existing_book != null) {
            // Check for removal of author from book
            Set<Author> existing_authors = existing_book.getAuthors();
            existing_authors.removeAll(updated_authors);

            update_book.setId(existing_book.getId());
            Book updated_book = bookService.updateBook(update_book);

            // Remove author_book pairs
            if (existing_authors.size() > 0){
                for (Author remove_authors: existing_authors){
                    remove_authors.removeBook(updated_book);
                    authorService.saveAuthor(remove_authors);
                }
            }
            for (Author new_author : updated_authors) {
                Author existing_author = authorService.returnAuthorByAuthorName(new_author.getName());
                if (existing_author == null) {
                    new_author.addBook(updated_book);
                    authorService.saveAuthor(new_author);
                }
                else{
                    new_author.setId(existing_author.getId());

                    existing_author.addBook(updated_book);
                    Set<Book> existing_author_books = existing_author.getBooks();
                    new_author.setBooks(existing_author_books);
                }
                authorService.saveAuthor(new_author);
            }
            return ResponseEntity.ok().body("Book with ISBN : "+isbn+" updated with success!");
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @DeleteMapping(value="/DeleteBookById/{id}")
    ResponseEntity deleteBookById( @PathVariable("id") @Min(1) Integer id) {
        Book book = bookService.returnBookById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Book with Id : "+id));
        Set<Author> delete_authors =book.getAuthors();
        for (Author author:delete_authors){
            author.removeBook(book);
            authorService.saveAuthor(author);
        }
        bookService.deleteBookById(id);
        return ResponseEntity.ok().body("Book with ID : "+id+" deleted with success!");
    }

    @GetMapping("/ReturnBooksByAuthorName/{author_name}")
    public ResponseEntity<Set<Book>> returnBooksByAuthorName(@PathVariable("author_name") String author_name) {
        Author author = authorService.returnAuthorByAuthorName(author_name);
        Set<Book> books = author.getBooks();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

}
