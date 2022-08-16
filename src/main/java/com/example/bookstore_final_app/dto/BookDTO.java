package com.example.bookstore_final_app.dto;

import com.example.bookstore_final_app.entity.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BookDTO {
    @NotBlank(message = "ISBN is required!")
    private String isbn;
    @NotBlank(message = "Title is required!")
    private String title;
    @NotNull
    @Positive(message = "Price cannot be Zero or negative")
    private double price;
    private int year;
    private String genre;
    private Set<Author> authors;
}
