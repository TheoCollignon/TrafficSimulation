package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

public class JSONManager {

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
            System.out.println(configuration.getRoads().size());
            int i=1;
            for (Road road: configuration.getRoads()) {
                JSONObject roadArray = new JSONObject();
                roadArray.put("startX",""+road.getCoordStart().getX());
                roadArray.put("startY",""+road.getCoordStart().getY());
                roadArray.put("endX",""+road.getCoordEnd().getX());
                roadArray.put("endY",""+road.getCoordEnd().getY());
                roads.put("Road"+i,roadArray);
                i++;
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

    public void readJSONFile(String fileName) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            //Iterate over employee array
            //employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (IOException /*| ParseException*/ | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

}