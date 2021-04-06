package Model;

import View.ViewGenerator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Car extends Thread{
    private Coordinate position;
    private int enegy;
    private ViewGenerator viewGenerator;


    public Car(Coordinate position, int enegy, ViewGenerator viewGenerator) {
        this.position = position;
        this.enegy = enegy;
        this.viewGenerator = viewGenerator;
        this.start();
    }

    public void run() {
        while (true) {
            int duration = 1000;
            try {
                System.out.println("cc");
                // here the action of the cars
                updateCarPosition(this);
                viewGenerator.updateCarPositionDraw(this);
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

    }

    public void updateCarPosition(Car car) {
        // change the position of the car
        City startingCity;
        City endCity;


        System.out.println("they see me rolling");
    }

    public int getEnegy() {
        return enegy;
    }

    public void setEnegy(int enegy) {
        this.enegy = enegy;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
