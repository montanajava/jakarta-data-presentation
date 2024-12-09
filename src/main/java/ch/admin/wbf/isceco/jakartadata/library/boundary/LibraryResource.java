package ch.admin.wbf.isceco.jakartadata.library.boundary;

import ch.admin.wbf.isceco.query.page.OffsetPage;
import ch.admin.wbf.isceco.query.request.OffsetPaginationRequest;
import ch.admin.wbf.isceco.jakartadata.library.control.AuthorDto;
import ch.admin.wbf.isceco.jakartadata.library.control.LibraryService;
import ch.admin.wbf.isceco.jakartadata.library.control.filter.AuthorFilterBean;
import ch.admin.wbf.isceco.jakartadata.library.control.filter.AuthorWithBookFilter;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@ApplicationScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("authors")
public class LibraryResource {

    @Inject
    LibraryService libraryService;

    /**
     * Illustrate the framework using queryParams.
     * Easily understood but not type-safe
     */
    @GET
    @Path("/search")
    @Transactional
    @Produces(APPLICATION_JSON)
    public Response findAll(
            @QueryParam("name") String name,
            @QueryParam("bookIsbns") List<String> bookIsbns,
            @QueryParam("pageRequested") int page,
            @QueryParam("maxElementsOnFullPage") int size) {

        // create filter
        Set<String> bookIsbnSet = new HashSet<>(bookIsbns);
        AuthorWithBookFilter authorCriteria = new AuthorWithBookFilter(name, bookIsbnSet);

        // create jakarta-data pageRequest
        PageRequest pageRequest = PageRequest.ofPage(page).size(size).withTotal();

        // get a jakarta-data page back from the service
        Page<AuthorDto> result = libraryService.findAll(authorCriteria, pageRequest);

        // create a json-friendly page
        var pagedResults = OffsetPage.createWithDefaultPageRequest(result.content(), result.totalElements(), result.hasNext());

        return Response.ok(pagedResults).build();
    }

    /**
     * Illustrate that the solution is still compatible with MultivalueMap
     * which assists in migrating to the new system.
     * <P>
     *     That said, this approach is not type-safe and depends on
     *     a contracted communicated between frontend and backend developers.
     * </P>
     *
     */
    @GET
    @Path("/search-multivalue")
    @Transactional
    @Produces(APPLICATION_JSON)
    public Response findAllWithMultivaluedMap(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String name = queryParams.getFirst("name");
        List<String> bookIsbns = queryParams.get("bookIsbns");
        int page = Integer.parseInt(queryParams.getFirst("pageRequested"));
        int size = Integer.parseInt(queryParams.getFirst("maxElementsOnFullPage"));
        boolean withTotalCount = Boolean.parseBoolean(queryParams.getFirst("withTotalCount"));

        // create filter
        Set<String> bookIsbnSet = new HashSet<>(bookIsbns);
        AuthorWithBookFilter authorCriteria = new AuthorWithBookFilter(name, bookIsbnSet);

        // create jakarta-data pageRequest
        PageRequest pageRequest = withTotalCount ? PageRequest.ofPage(page).size(size).withTotal() :
                PageRequest.ofPage(page).size(size).withoutTotal();

        // get a jakarta-data page back from the service
        Page<AuthorDto> result = libraryService.findAll(authorCriteria, pageRequest);

        // create a json-friendly page
        var reply = new OffsetPage<>(null, result.content(), result.totalElements(), result.hasNext());

        return Response.ok(reply).build();
    }

    @GET
    @Path("/filtered-with-beanparam")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response findAll2(@BeanParam AuthorFilterBean criteria, @BeanParam OffsetPaginationRequest offsetPaginationRequest) {
        jakarta.data.page.PageRequest request = offsetPaginationRequest.toJakartaDataPageRequest();
        Page<AuthorDto> result = libraryService.findAllWithAuthorFilterBean(criteria, request);

        // make a json-friendly page
        var pageToReturn =
                new OffsetPage<>(offsetPaginationRequest, result.content(), result.totalElements(), result.hasNext());
        return Response.ok(pageToReturn).build();
    }
}



