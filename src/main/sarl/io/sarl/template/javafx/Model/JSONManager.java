package io.sarl.template.javafx.Model;

import io.sarl.template.javafx.View.ViewGenerator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.net.URL;

public class JSONManager {

    public void saveJSONFile(Configuration configuration, String fileName) {
        try {
        	URL location = getClass().getResource("../configurationFiles/"+fileName+".json");
        	String jsonFile = location.toString();
        	jsonFile = jsonFile.substring(6);
            FileWriter file = new FileWriter(jsonFile);
            file.write("{");
            // Stock cities in JSON file
            file.write("\n\t\"isHippodamien\":"+configuration.isHippodamien+",");
            file.write("\n\t\"Cities\":{");
            for (int i=0 ; i<configuration.getCities().size(); i++) {
                City city = configuration.getCities().get(i);
                file.write("\n\t\t\""+city.getName()+"\":{");
                file.write("\n\t\t\t\"Size\":"+city.getSize()+",");
                file.write("\n\t\t\t\"Coords\":["+city.getPosition().getX()+","+city.getPosition().getY()+"]");
                file.write("\n\t\t}");
                if (i!=configuration.getCities().size()-1) {
                    file.write(",");
                }
            }
            file.write("\n\t},");
            // Stock roads in JSON file if not hippodamian
            if (!configuration.isHippodamien) {
            	file.write("\n\t\"Roads\":{");
                for (int i=0 ; i<configuration.getRoads().size(); i++) {
                    Road road = configuration.getRoads().get(i);
                    file.write("\n\t\t\"Road"+i+"\":{");
                    file.write("\n\t\t\t\"Coords\": [");
                    for (int j=0 ; j<road.getCoordsList().size(); j++) {
                        Coordinate coordinate = road.getCoordsList().get(j);
                        file.write("["+coordinate.getX()+","+coordinate.getY()+"]");
                        if (j!=road.getCoordsList().size()-1) {
                            file.write(",");
                        }
                    }
                    file.write("],\n\t\t\t\"Width\":"+road.getRoadWidth()+",");
                    if (road.getStart()==null) file.write("\n\t\t\t\"StartCity\":null,");
                    else file.write("\n\t\t\t\"StartCity\":\""+road.getStart().getName()+"\",");
                    if (road.getEnd()==null) file.write("\n\t\t\t\"EndCity\":null");
                    else file.write("\n\t\t\t\"EndCity\":\""+road.getEnd().getName()+"\"");
                    file.write("\n\t\t}");
                    if (i!=configuration.getRoads().size()-1) {
                        file.write(",");
                    }
                }
                file.write("\n\t},");
            }
            // Stock cars in JSON file
            file.write("\n\t\"Cars\":{");
            for (int i=0 ; i<configuration.getCars().size(); i++) {
                Car car = configuration.getCars().get(i);
                file.write("\n\t\t\"Car"+car.getId()+"\":{");
                file.write("\n\t\t\t\"Energy\":"+car.getEnergy()+",");
                file.write("\n\t\t\t\"CityFrom\":\""+car.getCityFrom().getName()+"\",");
                file.write("\n\t\t\t\"Destination\":\""+car.getDestination().getName()+"\",");
                file.write("\n\t\t\t\"Speed\":"+car.getSpeed());
                file.write("\n\t\t}");
                if (i!=configuration.getCars().size()-1) {
                    file.write(",");
                }
            }
            file.write("\n\t}");
            file.write("\n}");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration readJSONFile(ViewGenerator viewGenerator, String fileName) {
        Configuration configuration = new Configuration(viewGenerator);
        Object obj = null;
        try {
        	// for Eclipse URL : 
        	URL location = getClass().getResource("../configurationFiles/"+fileName+".json");
        	String jsonFile = location.toString();
        	jsonFile = jsonFile.substring(6);
            
            obj = new JSONParser().parse(new FileReader(jsonFile));
            JSONObject jo = (JSONObject) obj;
            // CITIES
            readCities(jo, configuration);

            configuration.mapLayout = new MapLayout(configuration.getCities().size(),configuration);
            boolean isHippodamian = (boolean) jo.get("isHippodamien");
            
            configuration.isHippodamien = isHippodamian;

            if(!isHippodamian) { // If not hippodamian
            	readRoads(jo, configuration);

            	readCars(jo, configuration);
            } else { // If hippodamian
            	configuration.generateHippodamianRoads();
            	
            	readCars(jo, configuration);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return configuration;
    }

	private void readCities(JSONObject jo, Configuration configuration) {
		Map cities = (Map) jo.get("Cities");
        int nbCities = 0;
        Iterator<Map.Entry> itr1 = cities.entrySet().iterator();
        while (itr1.hasNext()) {
            nbCities++;
            Map.Entry pair = itr1.next();
            String name = (String) pair.getKey();
            Map city = (Map) cities.get(pair.getKey());
            int size = Integer.parseInt(city.get("Size").toString());
            String tempCoord = city.get("Coords").toString();
            tempCoord = tempCoord.substring(1);
            tempCoord = tempCoord.substring(0, tempCoord.length() - 1);
            String[] coords = tempCoord.split(",");
            Coordinate coordinate = new Coordinate(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]));
            configuration.addCity(coordinate, size, name);
        }
	}
	
	private void readRoads(JSONObject jo, Configuration configuration) {
		Map roads = (Map) jo.get("Roads");
        Iterator<Map.Entry> itr2 = roads.entrySet().iterator();
        while (itr2.hasNext()) {
            Map.Entry pair = itr2.next();
            Map road = (Map) roads.get(pair.getKey());
            String startCity = road.get("StartCity").toString();
            String endCity = road.get("EndCity").toString();
            City start = null;
            City end = null;
            for (City city : configuration.getCities()) {
                if (city.getName().equals(startCity)) start=city;
                if (city.getName().equals(endCity)) end=city;
            }
            Road createdRoad = configuration.addRoad(start.getCrossRoad(),end.getCrossRoad());

            int width = Integer.parseInt(road.get("Width").toString());
            createdRoad.setRoadWidth(width);
            StringBuilder coords = new StringBuilder(road.get("Coords").toString());
            boolean needChanges = false;
            for (int i = 0; i < coords.length(); i++) {
                if (coords.charAt(i) == ',') {
                    if (needChanges) {
                        coords.setCharAt(i, '/');
                        needChanges = false;
                    } else {
                        needChanges = true;
                    }
                }
            }
            String tempCoords = coords.toString();
            tempCoords = tempCoords.substring(1);
            tempCoords = tempCoords.substring(0, tempCoords.length() - 1);
            String[] coordsArray = tempCoords.split("/");
            ArrayList<Coordinate> coordinatesList = new ArrayList<>();
            coordinatesList.add(start.getPosition());
            for (int i = 1; i < coordsArray.length-1; i++) {
                String temp = coordsArray[i];
                temp = temp.substring(1);
                temp = temp.substring(0, temp.length() - 1);
                String[] coordinateString = temp.split(",");
                Coordinate coordinate = new Coordinate(Float.parseFloat(coordinateString[0]), Float.parseFloat(coordinateString[1]));
                coordinatesList.add(coordinate);
            }
            coordinatesList.add(end.getPosition());
            createdRoad.setCoordsList(coordinatesList);
            //configuration.addCity(coordinate, size);
        }
	}
	
	private void readCars(JSONObject jo, Configuration configuration) {
		Map cars = (Map) jo.get("Cars");
        Iterator<Map.Entry> itr3 = cars.entrySet().iterator();
        while (itr3.hasNext()) {
            Map.Entry pair = itr3.next();
            Map car = (Map) cars.get(pair.getKey());
            float energy = Float.parseFloat(car.get("Energy").toString());
            int speed = Integer.parseInt(car.get("Speed").toString());

            String startCity = car.get("CityFrom").toString();
            String endCity = car.get("Destination").toString();
            City cityFrom = null;
            City destination = null;
            for (City city : configuration.getCities()) {
                if (city.getName().equals(startCity)) cityFrom=city;
                if (city.getName().equals(endCity)) destination=city;
            }

            int id = Integer.parseInt(((String)pair.getKey()).substring(3));
            configuration.addCar(energy, cityFrom, destination, id, speed);
        }
	}
}