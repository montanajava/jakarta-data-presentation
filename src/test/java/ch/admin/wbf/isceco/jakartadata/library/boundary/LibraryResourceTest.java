package ch.admin.wbf.isceco.jakartadata.library.boundary;

import ch.admin.wbf.isceco.query.page.OffsetPage;
import ch.admin.wbf.isceco.query.request.OffsetPaginationRequest;
import ch.admin.wbf.isceco.jakartadata.library.control.AuthorDto;
import ch.admin.wbf.isceco.jakartadata.library.control.filter.AuthorFilterBean;
import com.fasterxml.jackson.core.type.TypeReference;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.net.URI;
import java.util.*;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@QuarkusTest
public class LibraryResourceTest { // extends AbstractResourceTest {

    @TestHTTPResource("authors/search")
    private URI uri;

    @TestHTTPResource("authors/filtered-with-beanparam")
    private URI uriForBeanParam;

    @TestHTTPResource("authors/search-multivalue")
    private URI uriForMultivalue;

    private WebTarget target;
    private WebTarget targetForBeanParams;
    private WebTarget targetForMultivalue;

    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient();
        target = client.target(uri);
        targetForMultivalue = ClientBuilder.newClient().target(uriForMultivalue);
        targetForBeanParams = client.target(uriForBeanParam);
    }


    /**
     * Basic offset pagination test
     */
    @Test
    public void testFindAllWithNameAndIsbns() {

        List<String> isbns = new ArrayList<>(Arrays.asList("9781357924680", "9789876543226"));

        target = target.queryParam("name", "Harrison Kidman")
                .queryParam("pageRequested", 1)
                .queryParam("maxElementsOnFullPage", 2);

        for (String isbn : isbns) {
            target = target.queryParam("bookIsbns", isbn);
        }

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus()).isEqualTo(200);

        JsonObject jsonObject = getJsonObjectFromResponse(response);
        try (Jsonb jsonb = JsonbBuilder.create()) {
            OffsetPage<AuthorDto> pagedResult = jsonb.fromJson(
                    jsonObject.toString(),
                    new TypeReference<OffsetPage<AuthorDto>>() {}.getType()
            );
            List<AuthorDto> content = pagedResult.content();
            AuthorDto author = content.getFirst();

            assertThat(author.getName()).isEqualTo("Harrison Kidman");
            assertThat(author.getBooks()).hasSize(2);
        } catch (Exception e) {
            log.error("Error deserializing response", e);
        }


    }


    /**
     * Tests using legacy non-typesafe passing of values.
     * Assumes knowledge on both sides of parameter naming and
     * does not benefit from JAXRS serialization
     */
    @Test
    public void testFindWithMultiValueMap() {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();

        queryParams.add("name", "Harrison Kidman");
        queryParams.add("pageRequested", "1");
        queryParams.add("maxElementsOnFullPage", "1");
        queryParams.add("withTotalCount", "true");
        queryParams.add("bookIsbns", "9781357924680");
        queryParams.add("bookIsbns", "9789876543226");

        WebTarget queryTarget = targetForMultivalue;
        for (String key : queryParams.keySet()) {
            for (String value : queryParams.get(key)) {
                queryTarget = queryTarget.queryParam(key, value);
            }
        }

        Response response = queryTarget
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertThat(response.getStatus()).isEqualTo(200);

        JsonObject jsonObject = getJsonObjectFromResponse(response);
        try (Jsonb jsonb = JsonbBuilder.create()) {
            OffsetPage<AuthorDto> pagedResult = jsonb.fromJson(jsonObject.toString(),
                    new TypeReference<OffsetPage<AuthorDto>>() {
                    }.getType());
            List<AuthorDto> content = pagedResult.content();
            AuthorDto author = content.getFirst();

            assertThat(author.getName()).isEqualTo("Harrison Kidman");
            assertThat(author.getBooks()).hasSize(2);
        } catch (Exception e) {
            log.error("Error deserializing response", e);
        }
    }

    /**
     * Similar to the test using a MultiValueMap, this test illustrates
     * the convenience of the deserialization that you get for free in JAXRS
     */
    @Test
    public void testFindWithBeanParams() {
        // despite providing two isbns, Harrison Kidman is the author of only one of the books
        Set<String> isbns = new HashSet<>(Arrays.asList("9781357924680", "9789876543226"));

        AuthorFilterBean filter = new AuthorFilterBean();
        filter.setName("Harrison Kidman");
        filter.setIsbns(isbns);

        OffsetPaginationRequest offsetPaginationRequest = new OffsetPaginationRequest(1, 2, true);

        // Start with the base query params
        targetForBeanParams = targetForBeanParams.queryParam("name", filter.getName());

        // Add each ISBN as a separate query parameter
        for (String isbn : filter.getIsbns()) {
            targetForBeanParams = targetForBeanParams.queryParam("isbns", isbn);
        }
        // now the page request parameters
        targetForBeanParams = targetForBeanParams
                .queryParam("pageRequested", offsetPaginationRequest.getPageRequested())
                .queryParam("maxElementsOnFullPage", offsetPaginationRequest.getMaxElementsOnFullPage());

        Response response = targetForBeanParams.request(APPLICATION_JSON).get();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        JsonObject jsonObject = getJsonObjectFromResponse(response);
        OffsetPage<AuthorDto> offsetPage = JsonbBuilder.create().fromJson(jsonObject.toString(),
                new TypeReference<OffsetPage<AuthorDto>>() {
                }.getType());

        try (Jsonb jsonb = JsonbBuilder.create()) {
            OffsetPage<AuthorDto> pagedResult = jsonb.fromJson(jsonObject.toString(),
                    new TypeReference<OffsetPage<AuthorDto>>() {
                    }.getType());
            List<AuthorDto> content = offsetPage.content();
            AuthorDto author = content.getFirst();

            assertThat(author.getName()).isEqualTo("Harrison Kidman");
            assertThat(author.getBooks()).hasSize(2);
            assertThat(offsetPage.totalElements()).isEqualTo(1);
        } catch (Exception e) {
            log.error("Error deserializing response", e);
        }
    }


    public JsonObject getJsonObjectFromResponse(Response response) {
        String jsonString = response.readEntity(String.class);

        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonString))) {
            return jsonReader.readObject();
        }
    }

}
