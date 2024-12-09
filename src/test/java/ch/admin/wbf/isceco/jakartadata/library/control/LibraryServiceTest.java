package ch.admin.wbf.isceco.jakartadata.library.control;

import ch.admin.wbf.isceco.jakartadata.library.control.AuthorDto;
import ch.admin.wbf.isceco.jakartadata.library.control.LibraryService;
import ch.admin.wbf.isceco.jakartadata.library.control.filter.AuthorWithBookFilter;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class LibraryServiceTest {

    @Inject
    LibraryService service;

    /**
     * Shows how to create a dynamic query with joins.
     */
    @Test
    public void nameMatcherTest() {
        assertThat(true).isTrue();

        // Define filter
        Set<String> isbns = new HashSet<>(Arrays.asList("9781357924680", "9789876543226"));
        AuthorWithBookFilter authorWithBookFilter = new AuthorWithBookFilter("arriso", isbns);


        PageRequest pageRequest = PageRequest.ofPage(1);
        Page<AuthorDto> results = service.findAll(authorWithBookFilter, pageRequest);

        long totalElements = results.totalElements();
        assertThat(totalElements).isEqualTo(1);

        List<AuthorDto> content = results.content();
        assertThat(results).hasSize(1);

        AuthorDto retrievedAuthor = content.getFirst();
        Set<AuthorDto.BookDto> retrievedBooks = retrievedAuthor.books();


        List<AuthorDto.BookDto> bookList = new ArrayList<>(retrievedBooks);
        bookList.sort(Comparator.comparing(book -> book.getTitle().toLowerCase()));


        assertThat(retrievedBooks).hasSize(2);
        assertThat(retrievedAuthor.getName()).isEqualTo("Harrison Kidman");

        AuthorDto.BookDto firstBook = bookList.stream().findFirst().orElseThrow(() -> new AssertionError("First book not found"));
        assertThat(firstBook.getTitle()).isEqualTo("Sorcerer's Syntax");

        AuthorDto.BookDto lastBook = bookList.stream().skip(bookList.size() - 1).findFirst().orElseThrow(() -> new AssertionError("Last book not found"));
        assertThat(lastBook.getTitle()).isEqualTo("SQL and the City");
    }



}
