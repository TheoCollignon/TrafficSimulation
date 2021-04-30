package Model;

import View.ViewGenerator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Car {
    private Coordinate position;
    private float energy;
    private ViewGenerator viewGenerator;
    private Road roadOn;
    private City cityFrom;
    private City destination;
    private Random random;
    private int speed;
    private int id;


    public Car(Coordinate position, float energy, ViewGenerator viewGenerator, Road roadOn, City cityFrom, City destination, int id) {
        this.position = position;
        this.energy = energy;
        this.viewGenerator = viewGenerator;
        this.roadOn = roadOn;
        this.cityFrom = cityFrom;
        this.destination = destination;
        random = new Random();
        this.speed = random.nextInt(100) + 20 ;
        this.id = id;
    }
    //    public void run() {
//        while (true) {
//            int duration = random.nextInt(100) + 50;
//            try {
//                // here the action of the cars
//                roadOn.moveCarPosition(this, cityFrom);
//                Thread.sleep(duration);
//            } catch (InterruptedException e) {
//                System.out.println("crashed");
//                e.printStackTrace();
//                break;
//            }
//        }
//
//    }

    public void changeCarPosition(Coordinate position) {
        if (this.position == position) System.out.println("Error"); // this has to be fixed, even if there is another error somewhere
       /* try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        // if there is enough energy, we move the car
        if (this.energy >= 0 ) {
            this.energy -= 0.01 * this.speed;
            System.out.println(this.energy);
            this.position.removeCar(this);
            this.position = position;
            this.position.addCar(this);
        }

    }

    public City getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(City cityFrom) {
        this.cityFrom = cityFrom;
    }

    public float getEnegy() {
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


    public void changeRoad(Road nextRoad, City currentCity) {
        this.roadOn = nextRoad;
        this.cityFrom = currentCity;
        // we want to setup the destination
        if (currentCity != nextRoad.getStart()) this.destination = nextRoad.getStart();
        else this.destination = nextRoad.getEnd();

    }

    public float getEnergy() {
        return energy;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public ViewGenerator getViewGenerator() {
        return viewGenerator;
    }

    public void setViewGenerator(ViewGenerator viewGenerator) {
        this.viewGenerator = viewGenerator;
    }

    public Road getRoadOn() {
        return roadOn;
    }

    public void setRoadOn(Road roadOn) {
        this.roadOn = roadOn;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
