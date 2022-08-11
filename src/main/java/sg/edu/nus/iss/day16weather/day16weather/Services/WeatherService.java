package sg.edu.nus.iss.day16weather.day16weather.Services;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.day16weather.day16weather.Models.Weather;
import sg.edu.nus.iss.day16weather.day16weather.repositories.WeatherRepository;

@Service
public class WeatherService {

    private static final String URL = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${API_KEY}")
    private String key;

    @Autowired
    private WeatherRepository weatherRepo;
    public List<Weather> getWeatherWithName(String city, String ... userID)
    {
        if(userID.length>0 )
        {
            String userName = userID[0];
            if(!weatherRepo.isLoggedInUser(userName))
            {
                System.out.println("User is Not User "+userName);
                return null;
            }
            else{
                System.out.println("Valid User "+userName);
                return getWeather(city);
            }
        } 
        else{
            return getWeather(city);
        }
    }

    public List<Weather> getWeather(String city) {

        Optional<String> opt = weatherRepo.get(city);
        String payload;

        // checking if the info is in the cache
        if (opt.isEmpty()) {
            System.out.println("Getting weather from OpenWeatherMap");
            // figure out what URL to call
            // If its a get, whats the querry param
            // constructing the querry String
            String url = UriComponentsBuilder.fromUriString(URL)
                    .queryParam("q", city)
                    .queryParam("appid", key)
                    .toUriString();

            // Create GET request
            RequestEntity<Void> req = RequestEntity.get(url).build();

            // template
            // make the call to OpenWeatherMap
            RestTemplate template = new RestTemplate();
            // we are expecting a response in string
            ResponseEntity<String> resp = template.exchange(req, String.class);

            // checking the status
            if (resp.getStatusCodeValue() != 200) {
                System.err.println("Error status code is not 200");
                return Collections.emptyList();
            }

            payload = resp.getBody();
            System.out.println("payload: " + payload);

            weatherRepo.save(city, payload);
        } else {
            // Retrieve value for the box if not empty
            payload = opt.get();

        }

        Reader strReader = new StringReader(payload);
        // create a JsonReader from Reader
        JsonReader jsonReader = Json.createReader(strReader);
        // finally reading the payload as Java Json object
        JsonObject weatherResult = jsonReader.readObject();
        // returns the value of the key weather
        JsonArray cities = weatherResult.getJsonArray("weather");
        List<Weather> list = new LinkedList<>();
        for (int i = 0; i < cities.size(); i++) {
            // getting all items in weather [0]
            JsonObject jo = cities.getJsonObject(i);
            list.add(Weather.create(jo));
        }
        return list;
    }

   

}
