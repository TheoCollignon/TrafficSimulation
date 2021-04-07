package Model;

import View.ViewGenerator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Car extends Thread{
    private Coordinate position;
    private int energy;
    private ViewGenerator viewGenerator;
    private Road roadOn;


    public Car(Coordinate position, int energy, ViewGenerator viewGenerator, Road roadOn) {
        this.position = position;
        this.energy = energy;
        this.viewGenerator = viewGenerator;
        this.roadOn = roadOn;
        this.start();
    }

    public void run() {
        while (true) {
            int duration = 1000;
            try {
                // here the action of the cars
                roadOn.moveCarPosition(this);
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

    public void changeCarPosition(Coordinate position) {
        this.position = position;
    }

    public int getEnegy() {
        return energy;
    }

    public void setEnegy(int enegy) {
        this.energy = enegy;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
