package io.sarl.template.javafx.Model;

import io.sarl.template.javafx.View.ViewGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Configuration {

	private ArrayList<City> cities;
	private ArrayList<Road> roads;
	private ArrayList<Car> cars;
	private ArrayList<Car> deletedCars;
	private ArrayList<TrafficLight> trafficLights;
	private ViewGenerator viewGenerator;
	private Random random;
	private boolean pause;
	private boolean stopSimulation;
	boolean isHippodamien;
	AtomicInteger idCount = new AtomicInteger();
	MapLayout mapLayout;
	private int nbTotalCarsSpawned = 0;

	public Configuration(ViewGenerator viewGenerator) {
		this.pause = false;
		this.stopSimulation = false;
		random = new Random();
		this.viewGenerator = viewGenerator;
		cities = new ArrayList<>();
		roads = new ArrayList<>();
		cars = new ArrayList<>();
		deletedCars = new ArrayList<>();
		trafficLights = new ArrayList<>();
	}

	public void addElements(boolean isHippo) {
		this.isHippodamien = isHippo;
		// creating the map layout
		this.mapLayout = new MapLayout(getCities().size(), this);
		// generating the hippodamian grid in case the configuration is hippodamian
		if (isHippodamien) {
			generateHippodamianRoads();
		} else {
			for (int i = 0; i < roads.size(); i++) {
				roads.get(i).calculateCoordinates(isHippodamien);
			}
		}
	}

	public void addRandomElements(int nbCities, boolean isHippodamien) {
		// creating cities randomly
		generateRandomCities(nbCities);
		if (!isHippodamien)
			generateNormalRoads();
		addElements(isHippodamien);
	}

	public void generateNormalRoads() {
		int nbCities = getCities().size();
		// creating roads
		for (int i = 0; i < nbCities; i++) {
			for (int j = i + 1; j < nbCities; j++) {
				boolean isRoad = random.nextBoolean();
				if (isRoad) {
					// creating a road and joining it to its crossroad extremities
					addRoad(cities.get(i).getCrossRoad(), cities.get(j).getCrossRoad());
				}
			}
			int nbConnectedCities = cities.get(i).getConnectedCities().size();
			if (nbConnectedCities == 0) {
				int connectedCity = i;
				while (connectedCity == i)
					connectedCity = random.nextInt(cities.size());
				addRoad(cities.get(i).getCrossRoad(), cities.get(connectedCity).getCrossRoad());
			}
		}
		// when the cities are linked, we setup all the point between the road
		for (int i = 0; i < roads.size(); i++) {
			roads.get(i).calculateCoordinates(isHippodamien);
		}
	}

	public void generateHippodamianRoads() {
		int nbCities = getCities().size();
		// creating the coord lists...
		float[] listCityCoordsX = new float[nbCities];
		float[] listCityCoordsY = new float[nbCities];
		// ... and filling them
		for (int i = 0; i < nbCities; i++) {
			listCityCoordsX[i] = cities.get(i).getPosition().getX();
			listCityCoordsY[i] = cities.get(i).getPosition().getY();
		}
		// sorting the city coordinates list so it ranges correctly, then passing the
		// list to MapLayout
		Arrays.sort(listCityCoordsY);
		Arrays.sort(listCityCoordsX);
		mapLayout.sortCities(listCityCoordsX, listCityCoordsY);
		// giving coordinates to every point in the plan
		for (int i = 0; i < nbCities; i++) {
			for (int j = 0; j < nbCities; j++) {
				Coordinate newCoord = new Coordinate(listCityCoordsX[i], listCityCoordsY[j]);
				Crossroad newCross = new Crossroad(newCoord);
				for (City c : cities) {
					if (c.getPosition().equals(newCoord)) {
						newCross = c.getCrossRoad();
					}
				}
				this.mapLayout.setCrossroad(i, j, newCross);
			}
		}
		// adding the roads
		for (int i = 0; i < nbCities; i++) {
			for (int j = 0; j < nbCities; j++) {
				// If we're at the last point, don't try to draw an additional horizontal road
				if (i < nbCities - 1) {
					addRoad(mapLayout.getListCrossroads().get(i).get(j),
							mapLayout.getListCrossroads().get(i + 1).get(j));
				}
				// same as above for vertical roads
				if (j < nbCities - 1) {
					addRoad(mapLayout.getListCrossroads().get(i).get(j),
							mapLayout.getListCrossroads().get(i).get(j + 1));
				}
			}
		}
		for (int i = 0; i < roads.size(); i++) {
			roads.get(i).calculateCoordinates(isHippodamien);
		}
	}

	public void generateRandomCities(int nbCities) {
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
	}

	public boolean addCity(Coordinate position, int size, String name) {
		for (City city : cities) {
			if (Math.abs(city.getPosition().getX() - position.getX()) < (size + city.getSize())
					&& Math.abs(city.getPosition().getY() - position.getY()) < (size + city.getSize())) {
				return false;
			}
		}
		City newCity = new City(position, size, name);
		cities.add(newCity);
		return true;
	}

	public Road addRoad(Crossroad crossA, Crossroad crossB) {
		Road newRoad = new Road(crossA, crossB);
		crossA.addJoinedRoad(newRoad);
		crossB.addJoinedRoad(newRoad);
		// adding the road to the road list
		roads.add(newRoad);
		return newRoad;
	}

	public ArrayList<Car> setupCars(int numberCars, int energy) {
		int id = this.idCount.incrementAndGet();
		// init the cars
		for (int j = 0; j < numberCars; j++) {
			// find a random connected city
			City startCity = cities.get(j);
			// create the car
			id = this.idCount.incrementAndGet();
			Car car = new Car(this, energy, viewGenerator, startCity, id);
			nbTotalCarsSpawned++;
			// put it in the list
			cars.add(car);
		}
		return cars;
	}
	
	
	
	public ArrayList<Car> getDeletedCars(){
		return deletedCars;
	}
	
	
	public ArrayList<Car> addCar() {
		int energy = 10000000;
		int id = this.idCount.incrementAndGet();
		// init the cars
		for (int j = 0; j < cities.size(); j++) {
			if(cities.get(j).getPosition().getCarList().size() == 0) {
				// find a random connected city
				City startCity = cities.get(j);
				// create the car
				Car car = new Car(this, energy*2, viewGenerator, startCity, id);
				// drawing the car
				// viewGenerator.drawNewCar(car);
				// put it in the list
				cars.add(car);
				nbTotalCarsSpawned++;
				return cars;
			}
			
		}
		return cars;
	}
	
	public ArrayList<Car> removeCar(Car car, boolean hitbox){
		deletedCars.add(car);
		System.out.println(deletedCars.size());
		if(!hitbox) {
			car.getPosition().getCarList().remove(car);
		}
		cars.remove(car);
		System.out.println("removing a car - Java side not agent");
		return cars;
	}

	
	public void addCar(float energy, City cityFrom, City destination, int id, int speed) {
		Car car = new Car(this, energy, viewGenerator, cityFrom, id);
		car.setSpeed(speed);
		cars.add(car);
	}
	
	

	public Road findRoad(City A, City B) {
		for (Road road : roads) {
			if ((road.getStart() == A && road.getEnd() == B) || (road.getStart() == B && road.getEnd() == A)) {
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
	
	public ViewGenerator getViewGenerator() {
		return viewGenerator;
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

	
	public boolean getPause() {
		return pause;
	}
	public boolean getStopSimulation(){
		return this.stopSimulation;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	public void setStopSimulation(boolean stop) {
		this.stopSimulation = stop;
	}
	
	public int getNbTotalCarsSpawned() {
		return nbTotalCarsSpawned;
	}


}
