package yaroslav.learn.english.web.units;

import yaroslav.learn.english.core.entity.Dictionary;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by yaroslav on 6/13/14.
 */
@ManagedBean(name="dictionaryConverter")
public class DictionaryConverter implements Converter {
    @Inject
    List<Dictionary> dictionaries;


    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for(Dictionary dict : dictionaries)
            if(String.valueOf(dict.getId()).equals(value))
                return dict;
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Dictionary) {
            return String.valueOf(((Dictionary) value).getId());
        } else {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid dictionary instance"));
        }
    }
}
