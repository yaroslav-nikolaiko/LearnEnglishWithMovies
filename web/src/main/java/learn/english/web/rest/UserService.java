package learn.english.web.rest;

import learn.english.model.entity.Dictionary;
import learn.english.model.entity.User;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Created by yaroslav on 9/6/14.
 */
public class UserService implements Serializable {
    @Inject
    RestService restService;

    public void addToDataBase(User user) {
        Response response = restService.path("user").post(user);
        user.setId(restService.entityId(response));
    }

    public void addDictionary(User user, Dictionary dictionary) {
        Response response = restService.path("dict").param("userID", user.getId()).post(dictionary);
        dictionary.setId(restService.entityId(response));
        user.addDictionary(dictionary);
    }

    public void removeDictionary(User user, Dictionary currentDictionary) {
        restService.path("dict").delete(currentDictionary.getId());
        user.removeDictionary(currentDictionary);
    }

    public boolean nameExist(String name) {
        return Boolean.valueOf(restService.path("user/name").path(name).get(String.class));
    }

    public boolean emailExist(String email) {
        return Boolean.valueOf(restService.path("user/email").path(email).get(String.class));
    }

    public User findByNameAndPassword(String name, String password) {
        return restService.path("user").param("name", name).param("password", password).get(User.class);
    }
}
