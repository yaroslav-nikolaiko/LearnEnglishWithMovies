package learn.english.core.service;

import learn.english.model.utils.Persistent;
import learn.english.utils.ConfigurationManager;
import learn.english.utils.LogTrace;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by yaroslav on 6/28/14.
 */
@NoArgsConstructor
public abstract class AbstractService<T extends Persistent> {
    static final Logger logger = LogManager.getLogger(ConfigurationManager.value("logger"));
    EntityManager em;
    private Class<T> entityClass;

    @Context
    UriInfo uriInfo;

    protected AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    public T find(Long id) {
        logger.debug("Find entity by id={}",id);
        return em.find(entityClass, id);
    }

    @PUT
    public Response update(T entity) {
        logger.debug("Update entity {}",entity);
        em.merge(entity);
        return Response.ok().build();
    }


    public Response addToDataBase(T entity){
        logger.debug("Add entity {} to DB",entity);
        em.persist(entity);
        em.flush();
        URI entityURI = uriInfo.getAbsolutePathBuilder().path(String.valueOf(entity.getId())).build();
        return Response.created(entityURI).header("entity_id", entity.getId()).build();
    }

    public T singeResult(String queryName, Object... param){
        logger.debug("Single result query. Name = {}, parameters ={}",queryName,param);
        TypedQuery<T> query = buildQuery(queryName, param);
        return query.getSingleResult();
    }

    public List<T> resultList(String queryName, Object... param){
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
