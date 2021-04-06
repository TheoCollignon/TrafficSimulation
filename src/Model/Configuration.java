package Model;

import View.ViewGenerator;

import java.util.ArrayList;
import java.util.Random;

public class Configuration {

    private ArrayList<City> cities;
    private ArrayList<Road> roads;
    private ArrayList<Car> cars;
    private ArrayList<TrafficLight> trafficLights;
    private ViewGenerator viewGenerator;
    private Random random = new Random();

    public Configuration(ViewGenerator viewGenerator) {
        this.viewGenerator = viewGenerator;
        cities = new ArrayList<>();
        roads = new ArrayList<>();
        cars = new ArrayList<>();
        trafficLights = new ArrayList<>();
    }

    public void addRandomElements(int nbCities) {
        for (int i = 0; i < nbCities; i++) {
            int x = random.nextInt(500) + 50;
            int y = random.nextInt(500) + 50;
            int size = random.nextInt(25) + 15;
            String name = String.valueOf(i+1);
            while (!addCity(new int[]{x,y}, size, name)) {
                x = random.nextInt(500) + 50;
                y = random.nextInt(500) + 50;
            }
        }
        for (int i = 0; i < nbCities; i++) {
            for (int j = i+1; j < nbCities; j++) {
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
    }

    public boolean addCity(int[] position, int size, String name) {
        for (City city: cities) {
            if (Math.abs(city.getPosition()[0] - position[0]) < 50) return false;
            if (Math.abs(city.getPosition()[1] - position[1]) < 50) return false;
        }
        City newCity = new City(position, size, name);
        cities.add(newCity);
        return true;
    }

    public void addRoad(City A, City B) {
        Road road = new Road(A,B);
        A.addConnectedCity(B);
        B.addConnectedCity(A);
        roads.add(road);
    }

    public void setupCars(int numberCars) {
        // init the cars
        for (int j = 0; j < numberCars; j++ ) {
            // coord at city for the moment
            int[] coord = cities.get(j).getPosition();
            // create the car
            Car c = new Car( coord, 100);
            // put it in the list
            cars.add(c);
        }


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
}
