package Model;

import View.ViewGenerator;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Configuration {

    private ArrayList<City> cities;
    private ArrayList<Road> roads;
    private ArrayList<Car> cars;
    private ArrayList<TrafficLight> trafficLights;
    private ViewGenerator viewGenerator;
    private Random random;
    boolean isHippodamien;
    float[][][] coordPlanHippodamien;
    MapLayout mapLayout;

    public Configuration(ViewGenerator viewGenerator) {
        random = new Random();
        this.viewGenerator = viewGenerator;
        cities = new ArrayList<>();
        roads = new ArrayList<>();
        cars = new ArrayList<>();
        trafficLights = new ArrayList<>();
        isHippodamien = true;
    }

    public void addRandomElements(int nbCities) {

        //creating the map layout
        mapLayout = new MapLayout(nbCities);
        //creating cities
        for (int i = 0; i < nbCities; i++) {
            int x = random.nextInt(500) + 50;
            int y = random.nextInt(500) + 50;
            int size = random.nextInt(20) + 10;
            String name = String.valueOf(i + 1);
            while (!addCity(new Coordinate(x, y), size, name)) {
                x = random.nextInt(500) + 50;
                y = random.nextInt(500) + 50;
            }
        }
        //generating the hippodamian grid in case the configuration is hoppodamian
        if(isHippodamien){

            //creating the coord lists...
            coordPlanHippodamien = new float[nbCities][nbCities][2];
            float[] listCityCoordsX = new float[nbCities];
            float[] listCityCoordsY = new float[nbCities];
            //... and filling them
            for(int i = 0; i < nbCities; i++) {
                listCityCoordsX[i] = cities.get(i).getPosition().getX();
                listCityCoordsY[i] = cities.get(i).getPosition().getY();
            }
            //sorting the city coordinates list so it ranges correctly
            Arrays.sort(listCityCoordsY);
            Arrays.sort(listCityCoordsX);
            //giving coordinates to every point in the plan
            for(int i = 0; i < nbCities; i++) {
                for(int j = 0; j < nbCities; j++){
                    Coordinate newCoord = new Coordinate(listCityCoordsX[i], listCityCoordsY[j]);
                    float[] coords = {newCoord.getX(), newCoord.getY()};
                    coordPlanHippodamien[i][j] = coords;
                    mapLayout.setBranch(i, j, newCoord);
                }
            }
            //adding the roads
            for(int i = 0; i < nbCities ; i++) {
                for (int j = 0; j < nbCities ; j++) {
                    //getting the coords of the current point
                    Coordinate startCoord = new Coordinate(coordPlanHippodamien[i][j][0],coordPlanHippodamien[i][j][1]);
                    //If we're at the last point, don't try to draw an additional horizontal road
                    if(i < nbCities - 1){
                        Coordinate endCoordEast = new Coordinate(coordPlanHippodamien[i+1][j][0],coordPlanHippodamien[i+1][j][1]);
                        addRoad(startCoord,endCoordEast);
                    }
                    //same as above for vertical roads
                    if(j < nbCities - 1){
                        Coordinate endCoordSouth = new Coordinate(coordPlanHippodamien[i][j+1][0],coordPlanHippodamien[i][j+1][1]);
                        addRoad(startCoord,endCoordSouth);
                    }
                }
            }
        }
        //creating roads
        for (int i = 0; i < nbCities; i++) {
            for (int j = i + 1; j < nbCities; j++) {
                boolean isRoad = random.nextBoolean();
                if (isRoad) addRoad(cities.get(i), cities.get(j));
            }
            int nbConnectedCities = cities.get(i).getConnectedCities().size();
            if (nbConnectedCities == 0) {
                int connectedCity = i;
                while (connectedCity == i) connectedCity = random.nextInt(cities.size());
                addRoad(cities.get(i), cities.get(connectedCity));
            }
        }
        // when the cities are linked, we setup all the point between the road
        for (int i = 0; i < roads.size(); i++) {
            roads.get(i).calculateCoordinates(roads.get(i).getCoordsList().get(0), roads.get(i).getCoordsList().get(roads.get(i).getCoordsList().size() - 1),25);
        }
    }

    public boolean addCity(Coordinate position, int size, String name) {
        for (City city: cities) {
            if (Math.abs(city.getPosition().getX() - position.getX()) < (size + city.getSize()) ) return false;
            if (Math.abs(city.getPosition().getY() - position.getY()) < (size + city.getSize()) ) return false;
        }
        City newCity = new City(position, size, name);
        cities.add(newCity);
        return true;
    }

    public void addRoad(City A, City B) {
        Road road = new Road(A,B);
        A.addConnectedCity(B);
        A.addConnectedRoad(road);
        B.addConnectedCity(A);
        B.addConnectedRoad(road);

        roads.add(road);
    }

    //This method is meant to be used in the future instead of the top one
    public void addRoad(Coordinate coordA, Coordinate coordB){
        //creation of the road
        Road newRoad = new Road(coordA,coordB);
        //if the starting or ending point corresponds to a city, it is attributed to the road
        for(City city : cities){
            if(city.getPosition().equals(coordA)){
                newRoad.setStart(city);
            }
            if(city.getPosition().equals(coordB)){
                newRoad.setEnd(city);
            }
        }
        if(isHippodamien){

        }else{

        }
        //adding the road to the road list
        roads.add(newRoad);
    }

    public ArrayList<Car> setupCars(int numberCars) {
        // init the cars
        for (int j = 0; j < numberCars; j++ ) {
            // find a random connected city
            int randomCityConnected = random.nextInt(cities.get(j).getConnectedCities().size());
            // create the car
            Road road = findRoad(cities.get(j),cities.get(j).getConnectedCities().get(randomCityConnected));
            Car c = null;
            // put it in the list
            if (road.getCoordsList().get(0).getCar() != null) {
                c = new Car( road.getCoordsList().get(road.getCoordsList().size()-1),100, viewGenerator, road, road.getStart(), road.getEnd());
                road.getCoordsList().get(road.getCoordsList().size()-1).addCar(c);
            }
            else {
                c = new Car( road.getCoordsList().get(0),100, viewGenerator, road,  road.getStart(), road.getEnd());
                road.getCoordsList().get(0).addCar(c);
            }

            cars.add(c);
        }
        return cars;
    }

    public Road findRoad(City A, City B) {
        for (Road road : roads){
            if ((road.getStart() == A && road.getEnd() == B) ||(road.getStart() == B && road.getEnd() == A) ) {
                return road;
            }
        }
        return null;
    }



    public void addTrafficLight() {
        trafficLights.add(new TrafficLight(viewGenerator));
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public ArrayList<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    float[] getHippodamianRoad(City A, City B){
        float[] coordsRoad = null;
        return coordsRoad;
    }
}
