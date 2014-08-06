package learn.english.web.controller;

import learn.english.web.exception.WebException;
import lombok.Data;
import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/9/14.
 */
@Named
@RequestScoped
public @Data class UserBean implements Serializable{
    @EJB
    private UserService userService;
    private User user;

    @PostConstruct
    void init(){
        user = new User();
    }

    public void validateName(FacesContext context, UIComponent component, Object value){
        String name = (String) value;
        if(userService.nameExist(name)){
            FacesMessage message = new FacesMessage("Name "+name+" already exist");
            throw new ValidatorException(message);
        }
    }

    public void validateEmail(FacesContext context, UIComponent component, Object value){
        String email = (String) value;
        if(userService.emailExist(email)){
            FacesMessage message = new FacesMessage("Email "+email+" already exist");
            throw new ValidatorException(message);
        }
    }

    public User login() {
        User user = userService.findByNameAndPassword(this.user.getName(),  this.user.getPassword());
        if(user!=null){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", user.getName());
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            WebException e = new WebException("Loggin Error", FacesMessage.SEVERITY_WARN);
            e.setExplanation("Invalid credentials");
            throw e;
        }
        return user;
    }

}
