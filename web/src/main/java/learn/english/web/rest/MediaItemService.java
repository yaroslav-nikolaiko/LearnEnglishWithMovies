package learn.english.web.rest;

/**
 * Created by yaroslav on 9/6/14.
 */
public class MediaItemService<T> extends RestService<T>{
    public MediaItemService(Class<T> entityClass){
        this.entityClass = entityClass;
        entityPath = "item";
    }
}
