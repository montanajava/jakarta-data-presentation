package ch.admin.wbf.isceco.query.request;

public enum Mode {
    /**
     * Indicates forward cursor-based pagination, which follows the
     * direction of the sort criteria, using a cursor that is
     * formed from the key of the last entity on the current page.
     */
    CURSOR_NEXT,

    /**
     * Indicates a request for a page with cursor-based pagination
     * in the previous page direction to the sort criteria, using a cursor
     * that is formed from the key of first entity on the current page.
     * The order of results on each page follows the sort criteria
     * and is not reversed.
     */
    CURSOR_PREVIOUS,

    /**
     * Indicates a request for a page using offset pagination.
     * The starting position for pages is computed as an offset from
     * the first result based on the page number and maximum page size.
     * Offset pagination is used when a cursor is not supplied.
     */
    OFFSET

}
