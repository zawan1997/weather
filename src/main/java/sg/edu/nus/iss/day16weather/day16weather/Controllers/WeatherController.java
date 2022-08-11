package sg.edu.nus.iss.day16weather.day16weather.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.day16weather.day16weather.Models.Weather;
import sg.edu.nus.iss.day16weather.day16weather.Services.WeatherService;

@Controller
@RequestMapping(path={"/weather"})
public class WeatherController {

    @Autowired
    private WeatherService weatherSvc;

    @GetMapping
    //city is the key
    public String getWeatherName(Model model, @RequestParam String city) {
        List<Weather> weathers = weatherSvc.getWeather(city);
        model.addAttribute("city", city);
        model.addAttribute("weather", weathers);
        return "country";
    }

    @GetMapping
    //city is the key
    @RequestMapping("/withName")
    public String getWeatherWithValidName(Model model, @RequestParam String city, @RequestParam String userID) {
        List<Weather> weathers = weatherSvc.getWeatherWithName(city, userID);
        model.addAttribute("city", city);
        model.addAttribute("weather", weathers);
        return "country";
    }

}
