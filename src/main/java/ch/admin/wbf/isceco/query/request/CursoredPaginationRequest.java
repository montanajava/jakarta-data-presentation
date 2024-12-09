package ch.admin.wbf.isceco.query.request;

import jakarta.data.page.PageRequest;
/**
 * A minimalistic JSON-marshaling-friendly version of jakarta-data PageRequest
 * which allows users to jump to a page directly.
 * <p>>
 * Use for datasets where total size is not known or where dataset is not stable.
 */
public record CursoredPaginationRequest(int pageRequested,
                                        int maxElementsOnFullPage) {

    PageRequest toJakartaDataPageRequest() {
        return null;
    }

}
