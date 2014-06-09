package yaroslav.learn.english.web.controller;

import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/9/14.
 */
@Named
@RequestScoped
public class UserController implements Serializable{
    @EJB
    private UserService userService;


    @Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Email is not in valid format")
    private String email;
    @Size(max=20)
    private String name;
    @Size(min=3, max=20)
    private String password;


    public void validateName(FacesContext context, UIComponent component, Object value){
        String name = (String) value;
        if(userService.nameExist(name)){
            FacesMessage message = new FacesMessage("Name "+name+" already exist");
            throw new ValidatorException(message);
        }
    }

    public void validateEmail(FacesContext context, UIComponent component, Object value){
        String email = (String) value;
        if(userService.nameExist(email)){
            FacesMessage message = new FacesMessage("Email "+email+" already exist");
            throw new ValidatorException(message);
        }
    }

    public User buildUser(){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
