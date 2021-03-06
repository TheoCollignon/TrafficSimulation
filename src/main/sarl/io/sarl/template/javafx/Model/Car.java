package io.sarl.template.javafx.Model;

import io.sarl.template.javafx.View.ViewGenerator;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Car {
    private Coordinate position;
    //gives the direction of the car relative to the road : -1 means the car is moving towards to start of the road
    private float direction;
    private float energy;
    private ViewGenerator viewGenerator;
    private Road roadOn;
    private City cityFrom;
    private City destination;
    private Random random;
    private Configuration config;
    private int speed;
    private int id;
    private int maxPerception;
    private UUID uuid;
    private ArrayList<Coordinate> toBeDelete;
    private ArrayList<Integer> speeds = new ArrayList<Integer>();
    
    //the list of roads the car has to drive on to get to its destination
    private List<Road> roadPlan;


    public Car(Configuration config, float energy, ViewGenerator viewGenerator, City cityFrom, int id) {
    	this.uuid = UUID.randomUUID();
        this.position = cityFrom.getPosition();
        this.toBeDelete = new ArrayList<>();
        this.config = config;
        this.roadPlan = new ArrayList<>();
        this.energy = energy;
        this.viewGenerator = viewGenerator;
        this.cityFrom = cityFrom;
        random = new Random();
        this.speed = random.nextInt(100) + 20 ;
        this.id = id;
        this.maxPerception = random.nextInt(100) + 50; 
        //generating a first road plan
        getNewRoadPlan(cityFrom);
        Road firstRoad = roadPlan.get(0);
        this.roadOn = firstRoad;
        if(firstRoad.getStart() != null && firstRoad.getStart().equals(cityFrom)){
            firstRoad.getCoordsList().get(0).addCar(this);
            this.direction = 1;
        } else {
            firstRoad.getCoordsList().get(firstRoad.getCoordsList().size()-1).addCar(this);
            this.direction = -1;
        }
        /* use for debug
        System.out.println("My position (car number " + id + ") :");
        System.out.println(position);*/
    }

    public void changeCarPosition(Coordinate position) {
        // this has to be fixed, even if there is another error somewhere
        if (this.position == position) System.out.println("Error-001");
        // if there is a car in front of us, we can't go to the next coordinate
        if (position.getCar().size() == 0 ) {
            changeCoordinate(position);
        }
        else {
            // if there is a car on the next coordinate, we verify that it is on the other side of the road to continue
            if (this.direction != position.getCar().get(0).getDirection() ) {
                changeCoordinate(position);
            } /*else { //use for debug

                System.out.println("car " + id + " blocked by car " + position.getCar().get(0).getId());
            }*/
        }
    }

    public synchronized void changeCoordinate(Coordinate position) {
        // if there is enough energy, we move the car
        // if (this.energy >= 0 && !isInTownEvent()) {
        if (this.energy >= 0) {
            adaptCarSpeed(this.position);
            // this.energy -= 0.0001 * this.speed;
            this.energy -= 0.1;
           
//            try {
//				Thread.sleep(5);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
            
            this.position.removeCar(this);
    
            // System.out.println("HELLO LES MAIIIIIIIIIIIIIIII");
//            Iterator<Car> i = this.position.getCar().iterator();
//            while (i.hasNext()) {
//               Car car = (Car) i.next();
//               if (car.equals(this)) {
//            	  try {
//            		  i.remove();
//            	  } catch (ConcurrentModificationException e) {
//      				// TODO Auto-generated catch block
//      				e.printStackTrace();
//      			}
//                  // System.out.println("\n the car is removed");
//                  break;
//               }
//            } 
            
//            this.toBeDelete.add(this.position);
//            System.out.println("JAJOUTE UN TRUC A DELETE FDP");
            this.position = position;
            this.position.addCar(this);
            if(this.energy <= 0){
                System.out.println("All out of gas !");
            }
        }
    }

    public boolean isInTownEvent() {
        // checking if start or end exists and then if we are currently in it before anything else
        if ((this.roadOn.getStart() != null && this.position == this.roadOn.getStart().getPosition()) ||
                (this.roadOn.getEnd() != null && this.position == this.roadOn.getEnd().getPosition()))  {
            int randomValue = this.random.nextInt(100);
            int eventProba = 70;
            if (randomValue <= eventProba) {
                // make an event to stop the car in town, redlight, buy an ice cream, say hello to nahtan
                // actually the car won't move
                return true;
            }
        } return false;
    }

    public void adaptCarSpeed(Coordinate position) {
        // we'll make the car slow down in the last 25% of the road
        int adaptValue =  (int)(this.roadOn.getPointNum() * 0.20) ;
        double slowValue = 1.4;
        double speedValue= 0.7;

        // speed max verification
        // slowing down the car part :
        // if we are close to the end city
        if ((this.roadOn.getCoordsList().indexOf(position) >  (this.roadOn.getPointNum() - adaptValue )) && this.getDirection() == 1) {
            this.speed =(int)(this.speed * slowValue);
        }
        // if we are close to the start city
        else if ((this.roadOn.getCoordsList().indexOf(position) < adaptValue ) && this.getDirection() == -1) {
            this.speed = (int)(this.speed * slowValue );
        }
        // this if is important, in case of switching of road size too quickly
        if (this.speed > 50){
            // speeding up the cars :
             if ((this.roadOn.getCoordsList().indexOf(position) < adaptValue ) && this.getDirection() == 1) {
                this.speed = (int)(this.speed * speedValue);
            }
            // if we are close to the start city
            else if ((this.roadOn.getCoordsList().indexOf(position) > (this.roadOn.getPointNum() - adaptValue)) && this.getDirection() == -1) {
                this.speed = (int)(this.speed * speedValue);
            }
        }
    }

    public void changeRoad(Road nextRoad, Crossroad currentCrossroad, boolean isNewTravel) {
        this.roadOn = nextRoad;
        //if this is the beginning of a new travel, we keep in mind the starting city
        if(isNewTravel){
            this.cityFrom = currentCrossroad.getCity();
        }
        // setting up the direction
        if (currentCrossroad != nextRoad.getCrossroadStart()){
            direction = -1;
        } else{
            direction = 1;
        }

    }

    //choses randomly a city and calls the maplayout to know what path to follow
    public void getNewRoadPlan(City cityStart){
        City chosenCity = cityStart;
        this.cityFrom = cityStart;
        this.roadPlan.clear();
        while(this.roadPlan.size() == 0){
            while(chosenCity.equals(cityStart)){
                chosenCity = config.getCities().get(random.nextInt(config.getCities().size()));
            }
            this.roadPlan.clear();
            this.roadPlan.addAll(config.mapLayout.getPathBetweenTwoCities(cityStart, chosenCity));
        }
        //printRoadPlan(); //Use for debug
        this.destination = chosenCity;
        //System.out.println("new road plan !");
    }

    public Road getNextRoad(Road lastRoad){
        for(int i = 0; i < roadPlan.size(); i++){
            if(roadPlan.get(i).equals(lastRoad)){
                return roadPlan.get(i+1);
            }
        }
        //road somehow non-existent
        System.out.println("Error : can't find next road");
        return null;
    }

    //debug method : only works with one car for clarity purposes
    public void printRoadPlan(){
        if(this.id == 2){
            System.out.println("First city coords :");
            System.out.println(cityFrom.getPosition());
            System.out.println("Road plan :");
            int iterator = 1;
            for(Road r : roadPlan){
                System.out.println("Road " + String.valueOf(iterator) + " :");
                System.out.println(r.getCrossroadStart().getCoords());
                System.out.println(r.getCrossroadEnd().getCoords());
            }
        }
    }

    public City getCityFrom() {
        return cityFrom;
    }
    
    public UUID getUUID() {
    	return this.uuid;
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
    
    public int getMaxPerception() {
    	return this.maxPerception;
    }

    public void setRoadOn(Road roadOn) {
        this.roadOn = roadOn;
    }
    
    public ArrayList<Coordinate> getToBeDelete() {
    	return this.toBeDelete;
    }
    

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDirection() {
        return direction;
    }

    public List<Road> getRoadPlan() {
        return roadPlan;
    }
    
    public void addSpeed(int i) {
    	speeds.add(i);
    }
    
    public float getSpeedMoy() {
    	int total = 0;
    	for (Integer a: speeds) {
    		total += a;
    	}
    	return total/speeds.size();
    }
}
