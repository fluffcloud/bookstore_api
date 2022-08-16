package com.example.bookstore_final_app.services;

import com.example.bookstore_final_app.entity.Book;
import com.example.bookstore_final_app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public Book saveBook(Book book){
        return  bookRepository.save(book);
    }
    public Set<Book> returnBookByTitle(String title){ return bookRepository.findByTitle(title);}
    public Set<Book> returnAllBooks(){
            Set<Book> book_set = new HashSet<>();
            List<Book> book_list = bookRepository.findAll();
            for (Book book : book_list)
                book_set.add(book);
            return book_set;
        };
    public Optional<Book> returnBookById(int id){ return bookRepository.findById(id);}
    public void deleteBookById(int id) { bookRepository.deleteById(id);}
    public Book returnBookByIsbn(String isbn) {return bookRepository.findBookByIsbn(isbn);}
    public Book updateBook(Book book){
        Book existingBook= bookRepository.findById(book.getId()).orElse(null);
        existingBook.setGenre(book.getGenre());
        existingBook.setYear(book.getYear());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setTitle(book.getTitle());
        existingBook.setPrice(book.getPrice());
        //existingBook.setAuthors(book.getAuthors());
        return bookRepository.save(existingBook);
    }

}
