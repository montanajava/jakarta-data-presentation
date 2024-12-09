package ch.admin.wbf.isceco.query.page;

import ch.admin.wbf.isceco.query.request.OffsetPaginationRequest;

import java.util.List;

/**
 * Serialization-friendly Record type based on {@link jakarta.data.page.Page}.
 *
 * @param pageRequest  The {@link OffsetPaginationRequest pageRequest} for which this
 *                     page was obtained. Why is this returned?
 *                     <ul>
 *                         <li><b>Contextual Integrity:</b> Including the PageRequest object with the OffsetPage response allows the OffsetPage to fully encapsulate the request context that resulted in this specific page of data, transforming the page data into a self-describing entity.</li>
 *                         <li><b>Simplification of Logic:</b> Other components or systems might not have direct access to the original PageRequest object; hence, this inclusion ensures uniform logic application whenever the OffsetPage travels.</li>
 *                         <li><b>Debugging and Logging:</b> Embedding the PageRequest provides complete information on the query parameters for diagnostic and logging purposes without additional dependencies.</li>
 *                         <li><b>Consistency and Immutability:</b> As Java records are immutable, including the PageRequest ensures a consistent state, making the data tamper-proof.</li>
 *                     </ul>
 * @param content      The page content
 * @param totalElements The total number of elements across all pages that
 *                      can be requested for the query. A negative value
 *                      indicates that a total count of elements and pages
 *                      is not available.
 * @param moreResults  Whether there is a (nonempty) next page of results
 * @param <T>          The type of elements on the page
 */
public record OffsetPage<T>(
        OffsetPaginationRequest pageRequest,
        List<T> content,
        long totalElements,
        boolean moreResults
) {
    // Factory method instead of additional constructor
    public static <T> OffsetPage<T> createWithDefaultPageRequest(List<T> content, long totalElements, boolean moreResults) {
        return new OffsetPage<>(null, content, totalElements, moreResults);
    }
}
