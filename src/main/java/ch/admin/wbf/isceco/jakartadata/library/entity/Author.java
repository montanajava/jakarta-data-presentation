package ch.admin.wbf.isceco.jakartadata.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Author {

    public Author() {
    }
    
    public Author(String ssn, String name) {
        this.ssn = ssn;
        this.name = name;
    }

    @Id
    private String ssn;

    @Basic(optional = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany
    @JoinTable(name = "author_book",
            joinColumns = @JoinColumn(name = "author_ssn", referencedColumnName = "ssn"),
            inverseJoinColumns = @JoinColumn(name = "book_isbn", referencedColumnName = "isbn"))
    private Set<Book> books;

    public static final String BOOKS = "books";

}
