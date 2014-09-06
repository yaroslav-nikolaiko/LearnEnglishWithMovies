package learn.english.web.rest;

import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.io.Serializable;

/**
 * Created by yaroslav on 9/6/14.
 */
public class WordCellService implements Serializable {
    @Inject
    RestService restService;

    public String translate(String text, String languageFrom, String languageTo) {
        return restService.path("translator").path(text).param("from", languageFrom).param("to", languageTo).get(String.class);
    }

}
