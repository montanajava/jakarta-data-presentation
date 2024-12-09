package ch.admin.wbf.isceco.query.request;

import jakarta.data.page.PageRequest;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@NoArgsConstructor
@Getter @Setter
public class OffsetPaginationRequest {

    public OffsetPaginationRequest(int pageRequested, int maxElementsOnFullPage, boolean withTotalCount) {
        this.pageRequested = pageRequested;
        this.maxElementsOnFullPage = maxElementsOnFullPage;
        this.withTotalCount = withTotalCount;
    }

    @QueryParam("pageRequested")
    private int pageRequested;

    @QueryParam("maxElementsOnFullPage")
    private int maxElementsOnFullPage;

    @QueryParam("withTotalCount")
    private boolean withTotalCount;

    /**
     * Converts the current OffsetPaginationRequestBean to a Jakarta Data PageRequest.
     *
     * @return a PageRequest representing the pagination state.
     */
    public PageRequest toJakartaDataPageRequest() {
        return withTotalCount
                ? PageRequest.ofPage(pageRequested).size(maxElementsOnFullPage).withTotal()
                : PageRequest.ofPage(pageRequested).size(maxElementsOnFullPage).withoutTotal();
    }

}
