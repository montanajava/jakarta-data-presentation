package ch.admin.wbf.isceco.query.criteriarepository;


import ch.admin.wbf.isceco.query.specification.Specification;
import ch.admin.wbf.isceco.query.specification.SpecificationSort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;

/**
 * CriteriaBasedRepository is an interface for a repository that allows the retrieval of entities
 * based on dynamic filter and sorting specifications. It uses the Specification pattern for
 * filtering and the SortSpecification interface for sorting the results.
 *
 * @param <T> the type of the entity that this repository will handle.
 */
public interface CriteriaBasedRepository<T> {

    /**
     * Retrieves a paginated list of entities of type {@code T} that match the given specification
     * and sorting filter.
     *
     * @param spec       the specification that defines the filter for filtering entities
     * @param sortSpec   the sorting specification that defines how the results should be ordered
     * @param pageRequest the page request containing pagination information (page number, page size, etc.)
     * @return a {@code Page} of entities of type {@code T} that match the given filter and sorting
     */
    Page<T> findAll(Specification<T> spec, SpecificationSort<T> sortSpec, PageRequest pageRequest);

    /**
     * Handles common case where no sort was specified.
     *
     * @param spec       the specification that defines the filter for filtering entities
     * @param pageRequest the page request containing pagination information (page number, page size, etc.)
     * @return a {@code Page} of entities of type {@code T} that match the given filter and sorting
     */
    default Page<T> findAll(Specification<T> spec, PageRequest pageRequest) {
        return findAll(spec, null, pageRequest);
    }

}
