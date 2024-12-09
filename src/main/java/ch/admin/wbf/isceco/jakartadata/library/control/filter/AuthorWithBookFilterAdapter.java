package ch.admin.wbf.isceco.jakartadata.library.control.filter;

import jakarta.json.bind.adapter.JsonbAdapter;

import java.util.Set;

public class AuthorWithBookFilterAdapter implements JsonbAdapter<AuthorWithBookFilter, AuthorWithBookFilterAdapter.AuthorBookCriteriaJson> {

    public static class AuthorBookCriteriaJson {
        public String name;
        public Set<String> bookIsbns;
    }

    @Override
    public AuthorBookCriteriaJson adaptToJson(AuthorWithBookFilter obj) {
        AuthorBookCriteriaJson json = new AuthorBookCriteriaJson();
        json.name = obj.name();
        json.bookIsbns = obj.bookIsbns();
        return json;
    }

    @Override
    public AuthorWithBookFilter adaptFromJson(AuthorBookCriteriaJson obj) {
        return new AuthorWithBookFilter(obj.name, obj.bookIsbns);
    }

}
