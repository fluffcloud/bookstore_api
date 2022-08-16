package com.example.bookstore_final_app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "authors")
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String birthday;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinTable( name="author_book", joinColumns = @JoinColumn(name="author_id"), inverseJoinColumns = @JoinColumn(name="book_id"))
    private Set<Book> books = Collections.synchronizedSet(new HashSet<>());
    public void addBook(Book book) {
        this.books.add(book);
    }
    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public void removeAllBooks(Set<Book> books) {
        this.books.clear();
    }
}
