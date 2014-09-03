package learn.english.core.service;

import learn.english.utils.ConfigurationManager;
import learn.english.utils.LogTrace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yaroslav on 6/28/14.
 */
public abstract class AbstractService<T> {
    static final Logger logger = LogManager.getLogger(ConfigurationManager.value("logger"));
    EntityManager em;
    private Class<T> entityClass;

    protected AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T find(Long id) {
        logger.debug("Find entity by id={}",id);
        return em.find(entityClass, id);
    }

    public T update(T entity) {
        logger.debug("Update entity {}",entity);
        return em.merge(entity);
    }

    public void addToDataBase(T entity){
        logger.debug("Add entity {} to DB",entity);
        em.persist(entity);
    }

    T singeResult(String queryName, Object... param){
        logger.debug("Single result query. Name = {}, parameters ={}",queryName,param);
        TypedQuery<T> query = buildQuery(queryName, param);
        return query.getSingleResult();
    }

    List<T> resultList(String queryName, Object... param){
        logger.debug("Result List query. Name = {}, parameters ={}",queryName,param);
        TypedQuery<T> query = buildQuery(queryName, param);
        return query.getResultList();
    }

    protected TypedQuery<T> buildQuery(String queryName, Object... param){
        logger.debug("Build Typed query. Name = {}, parameters ={}",queryName,param);
        TypedQuery<T> query = em.createNamedQuery(queryName, entityClass);
        setParameters(query, param);
        return query;
    }

    protected Query buildSimpleQuery(String queryName, Object... param){
        logger.debug("Build Simple query. Name = {}, parameters ={}",queryName,param);
        Query query = em.createNamedQuery(queryName);
        setParameters(query, param);
        return query;
    }

    private void setParameters(Query query, Object... param){
        logger.debug("Set parameters {} to Query {}",param,query);
        for (int i=0; i<param.length; i++)
            query.setParameter(i+1, param[i]);
    }
}
