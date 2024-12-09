package ch.admin.wbf.isceco.query.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractSpecification<E> implements Specification<E> {

    private final List<SpecificationFilter<E>> specifications = new ArrayList<>();

    public void addSpecification(SpecificationFilter<E> specificationFilter) {
        if (specificationFilter != null) {
            specifications.add(specificationFilter);
        }
    }

    protected List<Predicate> getPredicates(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SpecificationFilter<E> specificationFilter : specifications) {
            Predicate predicate = specificationFilter.apply(root, query, builder);
            if (predicate != null) {
                predicates.add(predicate);
            }
        }
        return predicates;
    }

    protected Predicate combinePredicates(CriteriaBuilder builder, Predicate[] predicatesArray) {
        return builder.and(predicatesArray);
    }


    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = getPredicates(root, query, builder);

        if (predicates.isEmpty()) {
            return null;
        }

        if (predicates.size() == 1) {
            return predicates.getFirst();
        }

        return combinePredicates(builder, predicates.toArray(new Predicate[0]));
    }


    @SuppressWarnings("unchecked")
    protected Class<E> getEntityClass() {
        return Optional.ofNullable(getClass().getGenericSuperclass())
                .map(ParameterizedType.class::cast)
                .map(superSuperClassType -> (Class<E>) superSuperClassType.getActualTypeArguments()[0])
                .orElseThrow(() -> new IllegalStateException("Unexpected superClass for " + getClass()));
    }
}




