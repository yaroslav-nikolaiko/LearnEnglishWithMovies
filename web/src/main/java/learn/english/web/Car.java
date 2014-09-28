package learn.english.web;


import lombok.Data;

/**
 * Created by yaroslav on 9/27/14.
 */
@Data
public class Car {
    private String id;
    private String brand;
    private int year;
    private String color;
    private int price;
    private boolean soldState;

    public Car(String id, String brand, int year, String color, int price, boolean soldState) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.color = color;
        this.soldState = soldState;
        this.price = price;
    }


}
