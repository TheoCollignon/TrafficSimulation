package Model;

import View.ViewGenerator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JSONManager {

    public void saveJSONFile(Configuration configuration, String fileName) {
        try {
            FileWriter file = new FileWriter("./configurationFiles/"+fileName+".json");
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
            // Stock roads in JSON file
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
                else file.write("\n\t\t\t\"StartCity\":"+road.getStart().getName()+",");
                if (road.getEnd()==null) file.write("\n\t\t\t\"EndCity\":null");
                else file.write("\n\t\t\t\"EndCity\":"+road.getEnd().getName());
                file.write("\n\t\t}");
                if (i!=configuration.getRoads().size()-1) {
                    file.write(",");
                }
            }
            file.write("\n\t},");
            // Stock cars in JSON file
            file.write("\n\t\"Cars\":{");
            for (int i=0 ; i<configuration.getCars().size(); i++) {
                Car car = configuration.getCars().get(i);
                file.write("\n\t\t\"Car"+car.getId()+"\":{");
                file.write("\n\t\t\t\"Coords\":["+car.getPosition().getX()+","+car.getPosition().getY()+"],");
                file.write("\n\t\t\t\"Energy\":"+car.getEnergy()+",");
                file.write("\n\t\t\t\"CityFrom\":"+car.getCityFrom().getName()+",");
                file.write("\n\t\t\t\"Destination\":"+car.getDestination().getName()+",");
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
            obj = new JSONParser().parse(new FileReader("./configurationFiles/"+fileName+".json"));
            JSONObject jo = (JSONObject) obj;
            Map cities = (Map) jo.get("Cities");
            Iterator<Map.Entry> itr1 = cities.entrySet().iterator();
            while (itr1.hasNext()) {
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
            if(!(boolean) jo.get("isHippodamien")) {
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
                    Road createdRoad = configuration.addRoad(start,end);

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
                    for (int i = 0; i < coordsArray.length; i++) {
                        String temp = coordsArray[i];
                        temp = temp.substring(1);
                        temp = temp.substring(0, temp.length() - 1);
                        String[] coordinateString = temp.split(",");
                        Coordinate coordinate = new Coordinate(Float.parseFloat(coordinateString[0]), Float.parseFloat(coordinateString[1]));
                        coordinatesList.add(coordinate);
                    }
                    createdRoad.setCoordsList(coordinatesList);
                    //configuration.addCity(coordinate, size);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return configuration;
    }
}