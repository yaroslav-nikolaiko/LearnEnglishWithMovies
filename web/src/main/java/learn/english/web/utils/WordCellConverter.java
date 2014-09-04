package learn.english.web.utils;

import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.web.controller.SessionController;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 8/9/14.
 */
@Named
public class WordCellConverter implements Converter {
    @Inject SessionController sessionController;
    @Inject Map<String, WordCell> words;


    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            return words.get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((WordCell) object).getWord());
        }
        else {
            return null;
        }
    }

}
