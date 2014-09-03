package learn.english.web.controller;

import learn.english.web.exception.WebException;
import lombok.Data;
import learn.english.core.entity.User;
import learn.english.core.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
        //User user = userService.findByNameAndPassword(this.user.getName(),  this.user.getPassword());

        URI uri = UriBuilder.fromUri("http://localhost/core-1.0-SNAPSHOT/rest").port(8080).build();
        Client client = ClientBuilder.newClient();
        Response response = client.target(uri).path("user").queryParam("name", this.user.getName()).queryParam("password", this.user.getPassword()).request("application/json").get();
        //Response response = client.target(uri).path("user").request("application/json").get();

        User restUser =  response.readEntity(User.class);
        System.out.println("Fucking User : ---===>  "+restUser);

/*        if(user!=null){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", user.getName());
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            WebException e = new WebException("Loggin Error", FacesMessage.SEVERITY_WARN);
            e.setExplanation("Invalid credentials");
            throw e;
        }*/
        return restUser;
    }

}
