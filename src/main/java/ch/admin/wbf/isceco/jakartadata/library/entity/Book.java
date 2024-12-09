package ch.admin.wbf.isceco.jakartadata.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
public class Book {

    @Id
    private String isbn;

    @NaturalId
    public String title;

    @Basic(optional = false)
    public String description;

    private LocalDate creationDate;

    @Enumerated(STRING)
    @Basic(optional = false)
    private Type type = Type.Book;

    @ManyToOne
    private Publisher publisher;

    @ManyToMany(mappedBy = Author_.BOOKS)
    Set<Author> authors;

    @Basic(optional = false)
    private int pages;

    private BigDecimal price;
    private BigInteger quantitySold;

    public Book(String isbn, String title, String text) {
        this.isbn = isbn;
        this.title = title;
    }

    public Book() {}

    @Override
    public String toString() {
        return isbn + " : " + title + " [" + type + "], " + pages + " pages";
    }
}

