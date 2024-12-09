package ch.admin.wbf.isceco.jakartadata.library.control;

import ch.admin.wbf.isceco.db.PostgresTestResource;
import ch.admin.wbf.isceco.jakartadata.library.entity.Book;
import ch.admin.wbf.isceco.jakartadata.library.entity.Type;
import ch.admin.wbf.isceco.jakartadata.library.entity._Book;
import io.quarkus.test.common.WithTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.data.Limit;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@QuarkusTest
@WithTestResource(value = PostgresTestResource.class)
public class LibraryTest {


    @Inject
    Library library;

    @Test
    void testFindAll() {
        List<Book> languages = library.allBooks();
        assertThat(languages)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void findBookByTitleUsingPattern() {
        List<Book> books = library.byTitle("% Bites");
        assertThat(books).hasSize(1);
    }

    @Test
    void deleteBook() {
        library.delete("9789876543219");
        long count = library.count();
        assertThat(count).isEqualTo(13);
    }

   @Test
    void countBooks() {
       long count = library.count();
       assertThat(count).isEqualTo(14);
    }


    /**
     * Illustration of simple resultSet size limitation
     */
    @Test
    void orderedBooksByTitleAndTypeLimitedResultSet() {
        List<Book> allPaginated = library.booksByTitle("The%", Type.Book, Order.by(_Book.title.asc()), Limit.of(2));
        assertThat(allPaginated).hasSize(2);
        assertThat(allPaginated.stream().map(Book::getTitle).allMatch(title -> title.startsWith("The")))
                .isTrue();
        assertThat(allPaginated.getFirst().getTitle()).isEqualTo("The Data Detective");
    }


    // integration test using known data set.


    /**
     * Illustrate cursor-based paginated retrieval.

     * <p>
     * Note that we are using ordering as defined in the repository
     */
    @Test
    void orderedBooksByTitleAndTypePaginated() {
        // we are interested in the 3rd page (0-based index) in which 3 items may be retrieved.
        PageRequest pageRequest = PageRequest.ofPage(2).size(3);
        Page<Book> cursoredPageOfBooks = library.allBooksCursored(pageRequest);

        List<Book> books = cursoredPageOfBooks.content();

        // getPages refers to the pages of the books the pagination of the results.
        assertThat(books).hasSize(3);
        assertThat(books.getFirst().getPages()).isEqualTo(160);
        assertThat(books.get(1).getPages()).isEqualTo(170);
        assertThat(books.getLast().getPages()).isEqualTo(180);
    }


    /**
     * Illustrate cursor-based paginated retrieval starting at a key.
     * Gaven King prefers calling this key-based retrieval as, indeed, we are specifying a key.
     * <p>
     * The ordering as defined in the repository is still maintained
     */
    @Test
    void orderedBooksByTitleAndTypeCursorPaginated() {
        PageRequest pageRequest = PageRequest.ofSize(5).afterCursor(PageRequest.Cursor.forKey("9789876543210"));

        List<Book> books = library.allBooks(pageRequest);

        assertThat(books).hasSize(5);
        assertThat(books.getFirst().getTitle()).isEqualTo("The Sleepy Mapper");
        assertThat(books.getLast().getTitle()).isEqualTo("Scrolls of the SQL Sages");

        // our list is sorted by pages as defined in the annotation of the method.
        assertThat(books.stream().map(Book::getPages).sorted().toList()).isEqualTo(books.stream().map(Book::getPages).toList());
    }



}
