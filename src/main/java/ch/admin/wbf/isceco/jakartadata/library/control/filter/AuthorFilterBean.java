package ch.admin.wbf.isceco.jakartadata.library.control.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class AuthorFilterBean {

    @QueryParam("ssn")
    private String ssn;
    @QueryParam("name")
    private String name;
    @QueryParam("addressId")
    private String addressId;
    @QueryParam("isbns")
    private Set<String> isbns;

}
