package Controller;

import Model.Car;

import java.util.ArrayList;
import java.util.Random;

public class CarManager extends Thread{
    ArrayList<Car> carList;
    Random random;

    public CarManager(ArrayList<Car> carList) {
        this.carList = carList;
        random = new Random();
        this.start();
    }

    public void run() {
        while (true) {
            for (Car c : carList) {
                try {
                    // here the action of the cars
                    c.getRoadOn().moveCarPosition(c, c.getCityFrom());
                    Thread.sleep(c.getSpeed());
                } catch (InterruptedException e) {
                    System.out.println("crashed");
                    e.printStackTrace();
                    break;
                }
            }
        }

    }




    public ArrayList getCarList() {
        return carList;
    }

    public void setCarList(ArrayList carList) {
        this.carList = carList;
    }
}
