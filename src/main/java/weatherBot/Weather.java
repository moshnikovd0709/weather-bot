package weatherBot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import static weatherBot.Utils.getURL;

public class Weather {

    public static String getWeather(String message, WeatherModel model) throws IOException {
        URL url = new URL(getURL(message));

        Scanner in = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (in.hasNext()) {
            result.append(in.nextLine());
        }
        in.close();

        JSONObject object = new JSONObject(result.toString());
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setMain((String) obj.get("main"));
        }

        return "City: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "C" + "\n" +
                "Main: " + model.getMain() + "\n";
    }
}
