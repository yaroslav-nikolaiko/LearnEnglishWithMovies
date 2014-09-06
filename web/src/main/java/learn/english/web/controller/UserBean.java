package learn.english.web.controller;

import learn.english.model.entity.User;
import learn.english.web.exception.WebException;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.Serializable;
import java.net.URI;

/**
 * Created by yaroslav on 6/9/14.
 */
@Named
@RequestScoped
public @Data class UserBean implements Serializable{
/*    @EJB
    private UserService userService;*/
    private User user;
    @Inject RestService restService;

    @PostConstruct
    void init(){
        user = new User();
    }

    public void validateName(FacesContext context, UIComponent component, Object value){
        String name = (String) value;
        Boolean nameExist = Boolean.valueOf(restService.path("user/name").path(name).get(String.class));
        if(nameExist){
            FacesMessage message = new FacesMessage("Name "+name+" already exist");
            throw new ValidatorException(message);
        }
    }

    public void validateEmail(FacesContext context, UIComponent component, Object value){
        String email = (String) value;
        Boolean emailExist = Boolean.valueOf(restService.path("user/email").path(email).get(String.class));
        if(emailExist){
            FacesMessage message = new FacesMessage("Email "+email+" already exist");
            throw new ValidatorException(message);
        }
    }

    public User login() {
        return restService.path("user").
                                    param("name", user.getName()).
                                    param("password", user.getPassword()).
                                    get(User.class);

/*        if(user!=null){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", user.getName());
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            WebException e = new WebException("Loggin Error", FacesMessage.SEVERITY_WARN);
            e.setExplanation("Invalid credentials");
            throw e;
        }*/
    }

}
