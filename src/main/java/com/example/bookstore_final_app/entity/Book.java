package com.example.bookstore_final_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "books")
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String isbn;

    private String title;
    private int year;
    private double price;
    private String genre;

    @ManyToMany(mappedBy = "books")
    @JsonIgnore()
    private Set<Author> authors= Collections.synchronizedSet(new HashSet<>());


}
