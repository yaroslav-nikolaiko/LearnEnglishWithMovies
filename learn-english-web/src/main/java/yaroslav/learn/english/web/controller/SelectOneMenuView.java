package yaroslav.learn.english.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;


@Named
@SessionScoped
public class SelectOneMenuView implements Serializable {

    private String car;
    private List<SelectItem> cars;

    @PostConstruct
    public void init() {
        //cars
        SelectItemGroup g1 = new SelectItemGroup("German Cars");
        g1.setSelectItems(new SelectItem[] {new SelectItem("BMW", "BMW"), new SelectItem("Mercedes", "Mercedes"), new SelectItem("Volkswagen", "Volkswagen")});

        SelectItemGroup g2 = new SelectItemGroup("American Cars");
        g2.setSelectItems(new SelectItem[] {new SelectItem("Chrysler", "Chrysler"), new SelectItem("GM", "GM"), new SelectItem("Ford", "Ford")});

        cars = new ArrayList<SelectItem>();
        cars.add(g1);
        cars.add(g2);

    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }


    public List<SelectItem> getCars() {
        return cars;
    }

}