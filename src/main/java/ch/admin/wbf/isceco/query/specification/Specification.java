package ch.admin.wbf.isceco.query.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * The Specification interface is used to encapsulate the filter that can be applied
 * to filter the results of a query. Implementations of this interface define the
 * toPredicate method which returns a Predicate that can be applied to a CriteriaQuery.
 * This allows for dynamic construction of database queries.
 *
 * <p>This code is inspired by the Specification pattern from Domain-Driven Design (DDD)
 * and uses the `Specification` interface from Spring Data JPA for creating type-safe and reusable query filter
 * but moved to a new package.
 * For more information,
 * refer to the official [Spring Data JPA documentation](<a href="https://spring.io/projects/spring-data-jpa">...</a>).</p>
 *
 * <p>
 * While this is not part of the core Jakarta Persistence API,
 * it leverages classes and interfaces from Jakarta Persistence
 * such as Root, CriteriaQuery, and CriteriaBuilder
 * to build type-safe queries.
 * </p>
 *
 *
 * @param <T> the type of the entity for which the specification is to be applied
 */
public interface Specification<T> {

    /**
     * Converts the given filter to a {@link Predicate} that can be used to filter query results.
     *
     * @param root the root type in the "from" clause
     * @param query the query itself, for manipulating the query
     * @param builder a builder for creating {@link Predicate} instances
     * @return returns a {@link Predicate} representing the filtering filter
     */
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                          CriteriaBuilder builder);


}
