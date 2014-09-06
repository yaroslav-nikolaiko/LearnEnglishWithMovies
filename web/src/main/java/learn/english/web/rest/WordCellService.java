package learn.english.web.rest;

/**
 * Created by yaroslav on 9/6/14.
 */
public class WordCellService<T> extends RestService<T> {
    public WordCellService(Class<T> entityClass){
        this.entityClass = entityClass;
        entityPath = "wordcell";
    }
}
