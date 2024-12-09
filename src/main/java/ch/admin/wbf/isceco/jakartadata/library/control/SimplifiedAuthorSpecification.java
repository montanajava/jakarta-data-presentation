package ch.admin.wbf.isceco.jakartadata.library.control;

import ch.admin.wbf.isceco.jakartadata.library.entity._Author;
import ch.admin.wbf.isceco.jakartadata.library.entity._Book;
import ch.admin.wbf.isceco.query.specification.AbstractSpecification;
import ch.admin.wbf.isceco.query.specification.SpecificationFilter;
import ch.admin.wbf.isceco.jakartadata.library.control.filter.AuthorFilterBean;
import ch.admin.wbf.isceco.jakartadata.library.entity.Author;
import ch.admin.wbf.isceco.jakartadata.library.entity.Book;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.util.Set;

/**
 * Provides specification filters for querying {@link Author} entities.
 * This class defines custom query conditions
 * for filtering authors based on their attributes and relationships.
 */
public class SimplifiedAuthorSpecification extends AbstractSpecification<Author>  {

 

    public SimplifiedAuthorSpecification(AuthorFilterBean authorFilterBean) {
        if (authorFilterBean.getName() != null) {
            addSpecification(matchesName(authorFilterBean.getName()));
        }
        if (authorFilterBean.getIsbns() != null) {
            addSpecification(hasBooksWithIsbns(authorFilterBean.getIsbns()));
        }
        // Repeat similar checks for other properties

    }


    /**
     * Creates a filter for authors whose name match the given string with wildcards.
     *
     * @param name the name to filter authors by; if null, no filter is applied
     * @return a specification filter for authors with matching names, or null if the name is null
     */
    public static SpecificationFilter<Author> matchesName(String name) {
        return (root, query, cb) -> (name != null) ? cb.like(root.get(_Author.NAME), "%" + name + "%") : null;
    }






    /**
     * Creates a specification filter to find authors who have books with given ISBNs.
     *
     * @param isbns the set of ISBNs to filter authors by
     * @return a specification filter for authors who have books with the specified ISBNs
     */
    public static SpecificationFilter<Author> hasBooksWithIsbns(Set<String> isbns) {
        return (root, query, cb) -> {
            if (isbns == null || isbns.isEmpty()) {
                return null;
            }
            // Create a subquery for books
            Subquery<Book> subquery = query.subquery(Book.class);
            Root<Book> bookRoot = subquery.from(Book.class);
            subquery.select(bookRoot);
            subquery.where(
                    bookRoot.join("authors").in(root),  // Join with the current Author root
                    bookRoot.get(_Book.ISBN).in(isbns)  // Check ISBNs
            );
            // Ensure the main query uses distinct authors
            query.distinct(true);
            return cb.exists(subquery);
        };
    }
}
