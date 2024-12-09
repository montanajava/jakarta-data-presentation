package ch.admin.wbf.isceco.query.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Apply a set of filters.
 *
 * @param <E> the type of the entity to which the filter filter will be applied
 */
@FunctionalInterface
public interface SpecificationFilter<E> {

    /**
     * Applies a set of filter to the given query and root, and returns a Predicate
     * representing the result of these filter.
     *
     * @param root the root type in the "from" clause
     * @param query the query itself, for manipulating the query
     * @param builder a builder for creating Predicate instances
     * @return a Predicate representing the filter
     */
    Predicate apply(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder);

}
