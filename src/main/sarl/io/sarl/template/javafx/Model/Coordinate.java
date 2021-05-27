package io.sarl.template.javafx.Model;

import java.util.ArrayList;

public class Coordinate {
    private float x;
    private float y;
    private ArrayList<Car> carList;
    private City city;

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
        this.carList = new ArrayList<>();
    }

    public Coordinate(float x, float y, ArrayList<Car> carList) {
        this.x = x;
        this.y = y;
        this.carList = carList;
    }

    public Coordinate(float x, float y, ArrayList<Car> carList, City city) {
        this.x = x;
        this.y = y;
        this.carList = carList;
        this.city = city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void addCar(Car car) {
        this.carList.add(car);
    }

    public void removeCar(Car car) {
        // delete the car in the list
        // this.carList.remove(carList.get(carList.indexOf(car)));
        this.carList.remove(car);
    }

    public String toString(){
        String output = "Coords : ";
        output += String.valueOf(x);
        output += " , ";
        output += String.valueOf(y);
        return output;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ArrayList<Car> getCar() {
        return carList;
    }

    public boolean isCar() { return carList != null; }

    public void setCar(ArrayList<Car> car) {
        this.carList = car;
    }

    //Compares ONLY the coordinates
    public boolean equals(Coordinate coordToCompare){
        return (this.x == coordToCompare.getX() && this.y == coordToCompare.getY());
    }
}
