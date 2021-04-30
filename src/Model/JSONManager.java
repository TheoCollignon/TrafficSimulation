package Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
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
                file.write("\n\t\t\t\"Coords\":["+city.getPosition().getX()+","+city.getPosition().getY()+"],");
                file.write("\n\t\t\t\"ConnectedCities\": [");
                for (int j=0 ; j<city.getConnectedCities().size(); j++) {
                    City cityB = city.getConnectedCities().get(j);
                    file.write(cityB.getName());
                    if (j!=city.getConnectedCities().size()-1) {
                        file.write(",");
                    }
                }
                file.write("]\n\t\t}");
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

    public void readJSONFile(String fileName) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("./configurationFiles/"+fileName+".json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONObject config = (JSONObject) obj;
            JSONObject cities = (JSONObject) config.get("Cities");
            Set keySetCities = cities.keySet();
            //System.out.println(keySetCities.toString());
            Iterator itr = keySetCities.iterator();
            while (itr.hasNext()) {
                String t = (String) itr.next();
                System.out.println(cities.get(t));
            }
            //System.out.println(cities.get(0));
            JSONObject roads = (JSONObject) config.get("Roads");
            Set keySetRoads = roads.keySet();
            //System.out.println(keySetRoads.toString());
            itr = keySetRoads.iterator();
            while (itr.hasNext()) {
                String t = (String) itr.next();
                //System.out.println(roads.get(t));
            }
        } catch (IOException /*| ParseException*/ | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

}