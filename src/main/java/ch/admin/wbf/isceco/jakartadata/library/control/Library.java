package ch.admin.wbf.isceco.jakartadata.library.control;

import ch.admin.wbf.isceco.jakartadata.library.entity.Author;
import ch.admin.wbf.isceco.jakartadata.library.entity.Book;
import ch.admin.wbf.isceco.jakartadata.library.entity.Summary;
import ch.admin.wbf.isceco.jakartadata.library.entity.Type;
import jakarta.data.Limit;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.CursoredPage;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.*;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.processing.Pattern;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface Library {

    @Find
    Optional<Book> byIsbn(String isbn);

    @Find
    List<Book> byTitle(@Pattern String title);

    @Insert
    void add(Book book);

    /*
     * Illustrates how jakarta-data
     * is able to know what entity to delete from.
     * Here, the id of the entity is unambiguous.
     * <p>
     * In contrast, this will not work:
     * @Delete
     * void deletePublisher(Publisher publisher);
     */
    @Delete
    void delete(String isbn);



    @Find
    List<Book> allBooks(Sort<Book> bookSort);

    @Find
    @OrderBy("isbn")
    List<Book> allBooks();

    @Find
    @OrderBy("pages")
    CursoredPage<Book> allBooksCursored(PageRequest pageRequest);

    @Find
    @OrderBy("pages")
    List<Book> allBooks(PageRequest pageRequest);


    /**
     * Illustrate use of a Record as a DTO.
     */
    @Query("select b.isbn, b.title, listagg(a.name, ' & ') "
            + "from Book b join b.authors a "
            + "group by b "
            + "order by b.isbn")
    List<Summary> summarize();


    @Find
    List<Book> booksByTitle(@Pattern String title, Type type,
                            Order<Book> order, Limit limit);


    @Query("select count(isbn) from Book")
    long count();

    @Query("SELECT DISTINCT a FROM Author a WHERE (:name IS NULL OR a.name = :name) OR (:ssn IS NULL OR a.ssn = :ssn)")
    Page<Author> findByNameOrSsn(String name, String ssn, PageRequest pageRequest);

}
