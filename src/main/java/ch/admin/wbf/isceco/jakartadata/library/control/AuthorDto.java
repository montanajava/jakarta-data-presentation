package ch.admin.wbf.isceco.jakartadata.library.control;

import ch.admin.wbf.isceco.jakartadata.library.entity.Author;
import ch.admin.wbf.isceco.jakartadata.library.entity.Book;
import ch.admin.wbf.isceco.jakartadata.library.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Author}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;
    private String ssn;
    private String name;
    private String addressId;
    private Set<BookDto> books;


    public Set<BookDto> books() {
        return books;
    }


    @Override
    public int hashCode() {
        return Objects.hash(ssn);
    }

    @Override
    public String toString() {
        return "AuthorDto[" +
                "ssn=" + ssn + ", " +
                "name=" + name + ", " +
                "addressId=" + addressId + ", " +
                "books=" + books + ']';
    }

    /**
     * DTO for {@link Book}
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class BookDto implements Serializable {
        private String isbn;
        private String title;
        private String description;
        private LocalDate creationDate;
        private Type type;
        private int pages;
        private BigDecimal price;
        private BigInteger quantitySold;
    }
}
