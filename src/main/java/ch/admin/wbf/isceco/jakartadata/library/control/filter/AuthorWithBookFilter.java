package ch.admin.wbf.isceco.jakartadata.library.control.filter;

import ch.admin.wbf.isceco.jakartadata.library.entity.Author;
import jakarta.json.bind.annotation.JsonbTypeAdapter;

import java.io.Serializable;
import java.util.Set;

/**
 * Filtering filter for {@link Author}
 * when using Criteria-based queries.
 *
 */
@SuppressWarnings("SpellCheckingInspection")
@JsonbTypeAdapter(AuthorWithBookFilterAdapter.class)
public record AuthorWithBookFilter(String name, Set<String> bookIsbns) implements Serializable {
}

