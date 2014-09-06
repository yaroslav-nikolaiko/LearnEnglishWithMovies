package learn.english.web.rest;

/**
 * Created by yaroslav on 9/6/14.
 */
public class DictionaryService<T> extends RestService<T> {
    public DictionaryService(Class<T> entityClass){
        this.entityClass = entityClass;
        entityPath = "dict";
    }
}
