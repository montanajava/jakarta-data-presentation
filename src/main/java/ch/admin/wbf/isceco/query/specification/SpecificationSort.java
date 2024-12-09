package ch.admin.wbf.isceco.query.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * The SortSpecification interface is used to encapsulate the filter that can be
 * applied to sort the results of a query. Implementations of this interface define
 * the apply (Sorting) method which applies the sorting to a CriteriaQuery. This allows
 * for dynamic construction of database queries.
 *
 * @param <T> the type of the entity for which the sorting specification is to be applied
 */
@FunctionalInterface
public interface SpecificationSort<T> {

    void apply(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);

}

