package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JSONParser {

    public void saveJSONFile(Configuration configuration, String fileName) {
        try {
            FileWriter file = new FileWriter("./configurationFiles/"+fileName+".json");
            JSONObject obj = new JSONObject();

            JSONObject cities = new JSONObject();
            for (City city: configuration.getCities()) {
                JSONObject cityArray = new JSONObject();
                cityArray.put("X",""+city.getPosition().getX());
                cityArray.put("Y",""+city.getPosition().getY());
                cityArray.put("Size",""+city.getSize());
                cities.put(city.getName(),cityArray);
            }
            JSONObject roads = new JSONObject();
            for (Road road: configuration.getRoads()) {
                JSONObject roadArray = new JSONObject();
                roadArray.put("startX",""+road.getCoordStart().getX());
                roadArray.put("startY",""+road.getCoordStart().getY());
                roadArray.put("endX",""+road.getCoordEnd().getX());
                roadArray.put("endY",""+road.getCoordEnd().getY());
                roads.put("",roadArray);
            }
            obj.put("Cities", cities);
            obj.put("Roads", roads);

            file.write(obj.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}