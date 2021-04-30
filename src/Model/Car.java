package Model;

import View.ViewGenerator;

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
        // this has to be fixed, even if there is another error somewhere
        if (this.position == position) System.out.println("Error-001");
        // if there is a car in front of us, we can't go to the next coordinate
        if (position.getCar().size() == 0 ) {
            changeCoordinate(position);
        }
        else {
            // if there is a car on the next coordinate, we verify that it is on the other side of the road to continue
            if (this.destination != position.getCar().get(0).getDestination() ) {
                changeCoordinate(position);
            }
        }

    }

    public void changeCoordinate(Coordinate position) {
        // if there is enough energy, we move the car
        if (this.energy >= 0) {
            adaptCarSpeed(this.position);
            this.energy -= 0.01 * this.speed;
            this.position.removeCar(this);
            this.position = position;
            this.position.addCar(this);
        }
    }

    public void adaptCarSpeed(Coordinate position) {
        // we'll make the car slow down in the last 25% of the road
        int adaptValue =  (int)(this.roadOn.getPointNum() * 0.20) ;
        double slowValue = 1.4;
        double speedValue= 0.7;

        // speed max verification

            // slowing down the car part :
            // if we are close to the end city
            if ((this.roadOn.getCoordsList().indexOf(position) >  (this.roadOn.getPointNum() - adaptValue )) && this.getDestination() == this.roadOn.getEnd()) {
                this.speed =(int)(this.speed * slowValue);
            }
            // if we are close to the start city
            else if ((this.roadOn.getCoordsList().indexOf(position) < adaptValue ) && this.getDestination() == this.roadOn.getStart()) {
                this.speed = (int)(this.speed * slowValue );
            }
        if (this.speed > 50){
            // speeding up the cars :
             if ((this.roadOn.getCoordsList().indexOf(position) < adaptValue ) && this.getDestination() == this.roadOn.getEnd()) {
                this.speed = (int)(this.speed * speedValue);
            }
            // if we are close to the start city
            else if ((this.roadOn.getCoordsList().indexOf(position) > (this.roadOn.getPointNum() - adaptValue)) && this.getDestination() == this.roadOn.getStart()) {
                this.speed = (int)(this.speed * speedValue);
            }
            // System.out.println("my speed" + this.speed);


            // when at city :

        }




    }

    public City getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(City cityFrom) {
        this.cityFrom = cityFrom;
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
