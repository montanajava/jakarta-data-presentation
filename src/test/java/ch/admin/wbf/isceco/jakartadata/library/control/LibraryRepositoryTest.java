package ch.admin.wbf.isceco.jakartadata.library.control;

import ch.admin.wbf.isceco.jakartadata.library.control.AuthorSpecification;
import ch.admin.wbf.isceco.jakartadata.library.control.LibraryRepository;
import ch.admin.wbf.isceco.jakartadata.library.control.filter.AuthorWithBookFilter;
import ch.admin.wbf.isceco.jakartadata.library.entity.Author;
import ch.admin.wbf.isceco.jakartadata.library.entity.Book;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the repository directly. Use as a demonstration of the API.
 */
@SuppressWarnings("SpellCheckingInspection")
@QuarkusTest
public class LibraryRepositoryTest {

    @Inject
    LibraryRepository repository;

    /**
     * This demonstrates creating filter with a join.
     */
    @Test
    @Transactional
    void testFindAllByCriteria() {

        // Define filter filter
        Set<String> isbns = new HashSet<>(Arrays.asList("9781357924680", "9789876543226"));
        AuthorWithBookFilter authorWithBookFilter = new AuthorWithBookFilter("arriso", isbns);

        // Use the filter in a specification
        AuthorSpecification spec = new AuthorSpecification();
        spec.addSpecification(AuthorSpecification.matchesName(authorWithBookFilter.name()));
        spec.addSpecification(AuthorSpecification.hasBooksWithIsbns(authorWithBookFilter.bookIsbns()));

        // Get a page given the specification and a page request.
        PageRequest pageRequest = PageRequest.ofPage(1);
        Page<Author> authorsPage = repository.findAll(spec, pageRequest);

        List<Author> authors = authorsPage.content();

        // Expect 1 author with 2 books
        assertThat(authors).hasSize(1);
        assertThat(authors.getFirst().getBooks()).hasSize(2);

        Author retrievedAuthor = authors.getFirst();
        assertThat(retrievedAuthor.getName()).isEqualTo("Harrison Kidman");

        // Sort books by title in a LinkedHashSet to maintain order
        Set<Book> sortedBooks = retrievedAuthor.getBooks().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // Retrieve first and last books
        Book retrievedFirstBook = sortedBooks.stream().findFirst().orElseThrow();
        assertThat(retrievedFirstBook.getTitle()).isEqualTo("SQL and the City");

        Book retrievedLastBook = sortedBooks.stream()
                .reduce((first, second) -> second)
                .orElseThrow();
        assertThat(retrievedLastBook.getTitle()).isEqualTo("Sorcerer's Syntax");
    }


}
