package ch.admin.wbf.isceco.query.criteriarepository;

import ch.admin.wbf.isceco.query.specification.Specification;
import ch.admin.wbf.isceco.query.specification.SpecificationSort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.page.impl.PageRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.ParameterizedType;
import java.util.List;


/**
 * AbstractCriteriaBasedRepository is an abstract class implementing {@link CriteriaBasedRepository} interface
 * for performing search operations on entities using the Criteria API based on dynamic specifications.
 *
 * <p>
 * To use this, subclass this, specifying the root entity.
 * </p>
 *
 * @param <E> the type of the entity that the repository will handle.
 */
public abstract class AbstractCriteriaBasedRepository<E> implements CriteriaBasedRepository<E> {

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractCriteriaBasedRepository() {
        Class<E> entityClass = getEntityClass();
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("The class " + entityClass.getName() + " is not a JPA entity.");
        }
    }

    @Override
    public Page<E> findAll(Specification<E> spec, SpecificationSort<E> sortSpec, PageRequest pageRequest) {
        if (pageRequest.mode() != PageRequest.Mode.OFFSET) {
            throw new IllegalArgumentException("Only OFFSET mode is currently supported");
        }

        Class<E> entityClass = getEntityClass();
        TypedQuery<E> typedQuery = buildQuery(spec, sortSpec, entityClass);

        int totalRows = typedQuery.getResultList().size();
        typedQuery.setFirstResult((int) ((pageRequest.page() - 1) * pageRequest.size()));
        typedQuery.setMaxResults(pageRequest.size());

        List<E> resultList = typedQuery.getResultList();
        return new PageRecord<>(pageRequest, resultList, totalRows);
    }

    protected TypedQuery<E> buildQuery(Specification<E> spec, SpecificationSort<E> sortSpec,
                                       Class<E> entityClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(entityClass);
        Root<E> root = query.from(entityClass);

        Predicate predicate = spec.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }

        if (sortSpec != null) {
            sortSpec.apply(root, query, builder);
        }

        return entityManager.createQuery(query);
    }

    @SuppressWarnings("unchecked")
    protected Class<E> getEntityClass() {
        Class<?> currentClass = getClass();

        while (currentClass.getSuperclass() != null) {
            java.lang.reflect.Type superClassType = currentClass.getGenericSuperclass();

            if (superClassType instanceof ParameterizedType parameterizedType) {
                return (Class<E>) parameterizedType.getActualTypeArguments()[0];
            }

            currentClass = currentClass.getSuperclass();
        }

        throw new IllegalStateException("Unexpected superClass for " + getClass());
    }

}
