package ch.admin.wbf.isceco.query.page;


import ch.admin.wbf.isceco.query.request.AdjacentCursoredPageRequest;

import java.util.List;


/**
 * Serialization-friendly Record type based on  {@link jakarta.data.page.CursoredPage}.
 *
 * <p>
 * This record holds the paginated content, information sufficient to execute queries for
 * the next and previous pages,
 * as well as estimtes about the total number of elements and total number of pages.</p>
 *
 * <p>Page numbers, total numbers of elements across all pages, and total count
 * of pages are not accurate when cursor-based pagination is used and should not
 * be relied upon.</p>
 *
 * @param <T> the type of the content in the paginated result
 * @param content the list of content elements for the current page
 * @param nextCursoredPageRequest the request details for the next page, or null if no next page exists
 * @param previousCursoredPageRequest the request details for the previous page, or null if no previous page exists
 * @param totalElements the total number of elements across all pages, estimated. A negative value
 *  *                      indicates that a total count of elements and pages
 *  *                      is not available.
 * @param totalPages the total number of pages, estimated
 */
public record CursoredPage<T> (List<T> content,
                               AdjacentCursoredPageRequest nextCursoredPageRequest,
                               AdjacentCursoredPageRequest previousCursoredPageRequest,
                               long totalElements,
                               long totalPages) {

    public boolean hasNext() {
        return nextCursoredPageRequest() != null;
    }


    public boolean hasPrevious() {
        return previousCursoredPageRequest() != null;
    }
};


