package yaroslav.learn.english.web;

/**
 * Created by yaroslav on 6/13/14.
 */
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;


//@ManagedBean
@Named
@SessionScoped
public class SelectOneMenuView implements Serializable {
    private Theme theme;
    private List<Theme> themes;

    // @ManagedProperty("#{themeService}")
    @Inject
    private ThemeService service;

    @PostConstruct
    public void init() {
        //themes
        themes = service.getThemes();
    }



    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Produces
    public List<Theme> getThemes() {
        return themes;
    }

    public void setService(ThemeService service) {
        this.service = service;
    }
}
