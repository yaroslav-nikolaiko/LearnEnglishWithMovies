package learn.english.core.service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yaroslav on 6/28/14.
 */
public abstract class AbstractService<T> {
    EntityManager em;
    private Class<T> entityClass;

    protected AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T find(Long id) {
        return em.find(entityClass, id);
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public void addToDataBase(T entity){
        em.persist(entity);
    }

    T singeResult(String queryName, String... param){
        TypedQuery<T> query = buildQuery(queryName, param);
        return query.getSingleResult();
    }

    List<T> resultList(String queryName, String... param){
        TypedQuery<T> query = buildQuery(queryName, param);
        return query.getResultList();
    }

    protected TypedQuery<T> buildQuery(String queryName, String... param){
        TypedQuery<T> query = em.createNamedQuery(queryName, entityClass);
        setParameters(query, param);
        return query;
    }

    protected Query buildSimpleQuery(String queryName, String... param){
        Query query = em.createNamedQuery(queryName);
        setParameters(query, param);
        return query;
    }

    private void setParameters(Query query, String... param){
        for (int i=0; i<param.length; i++)
            query.setParameter(i+1, param[i]);
    }
}
