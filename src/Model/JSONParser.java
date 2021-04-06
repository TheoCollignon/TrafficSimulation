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
                JSONArray cityArray = new JSONArray();
                JSONObject x = new JSONObject();
                JSONObject y = new JSONObject();
                JSONObject size = new JSONObject();
                x.put("X",city.getPosition()[0]);
                y.put("Y",city.getPosition()[0]);
                size.put("Size",city.getPosition()[0]);
                cityArray.put(x);
                cityArray.put(y);
                cityArray.put(size);
                cities.put(city.getName(),cityArray);
            }
            obj.put("Cities", cities);

            file.write(obj.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}