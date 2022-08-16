package com.example.bookstore_final_app.dto;

import com.example.bookstore_final_app.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class AuthorDTO {
    @NotBlank(message = "Name is required!")
    private String name;
    @NotBlank(message = "Birthday is required!")
    private String birthday;
    private Set<Book> books;
}
