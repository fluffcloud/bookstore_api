package com.example.bookstore_final_app.repository;

import com.example.bookstore_final_app.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book,Integer> {
    Set<Book> findByTitle(String title);
    Book findBookByIsbn(String isbn);

}
