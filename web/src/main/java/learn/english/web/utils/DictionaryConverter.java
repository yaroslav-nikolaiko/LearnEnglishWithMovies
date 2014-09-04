package learn.english.web.utils;

import learn.english.model.entity.Dictionary;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class DictionaryConverter implements Converter {
    @Inject
    List<Dictionary> dictionaries;


    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            for(Dictionary d : dictionaries)
                if(value.equals(String.valueOf( d.getId() )  )  )
                    return d;
        }
            return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Dictionary) object).getId());
        }
        else {
            return null;
        }
    }
}
