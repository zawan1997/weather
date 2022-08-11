package sg.edu.nus.iss.day16weather.day16weather.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.day16weather.day16weather.Models.UserCreateResponse;
import sg.edu.nus.iss.day16weather.day16weather.Models.Weather;
import sg.edu.nus.iss.day16weather.day16weather.Services.UserService;
import sg.edu.nus.iss.day16weather.day16weather.Services.WeatherService;

@Controller
@RequestMapping(path={"/user"})
public class UserController {

    @Autowired
    private UserService userSvc;

    @PostMapping("/register")
    public String createUser(Model model, @RequestBody String userData) {
        boolean res = userSvc.createUser(userData);
        if(res)
            return "UserResponseRegister";
        else
        return "UserResponseRegisterFail";
    }

    @PostMapping("/login")
    //city is the key
    public String loginUser(Model model, @RequestBody String userData) {
        boolean isValid = userSvc.login(userData);
        
        if(isValid)
            return "UserResponseSuccess";
        return "UserResponseFailure";
    }
    @GetMapping("/logout")
    //city is the key
    public String logoutUser(Model model,  @RequestParam String userID) {
        boolean isValid = userSvc.logout(userID);
        
        if(isValid)
            return "UserLogoutSuccess";
        return "UserLogoutFailure";
    }

}
