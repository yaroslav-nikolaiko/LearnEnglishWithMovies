package learn.english.web.rest;

/**
 * Created by yaroslav on 9/6/14.
 */
public class UserService<T> extends RestService<T>{
    public UserService(Class<T> entityClass){
        this.entityClass = entityClass;
        entityPath = "user";
    }
}
