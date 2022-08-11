package sg.edu.nus.iss.day16weather.day16weather.Services;


import java.io.StringReader;
import java.rmi.server.UID;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.day16weather.day16weather.Models.UserCreateRequest;
import sg.edu.nus.iss.day16weather.day16weather.Models.UserCreateResponse;
import sg.edu.nus.iss.day16weather.day16weather.Models.UserLoginRequest;
import sg.edu.nus.iss.day16weather.day16weather.repositories.WeatherRepository;



@Service
public class UserService {

    @Autowired
    private WeatherRepository weatherRepo;
    
    public boolean createUser(String userData)
    {
        JsonObject jo = Json.createReader(new StringReader(userData)).readObject();
        UserCreateRequest req  = UserCreateRequest.create(jo);

        //  save in redis or save in db  input = req, ouput = resp 
        boolean isRegisterSuccess = weatherRepo.saveUser(req.getUserID(), req.getPassword());
        if(isRegisterSuccess)
        {
        
        System.out.println("Registration is successful");
        }
        else{
            System.out.println("user already exists");
        }
        return isRegisterSuccess;
    }
    public boolean login(String userData)
    {
        
        JsonObject jo = Json.createReader(new StringReader(userData)).readObject();
        UserLoginRequest req  = UserLoginRequest.create(jo);

        //  save in redis or save in db  input = req, ouput = resp 
        boolean isvalid = weatherRepo.isValidUser(req.getUserID(), req.getPassword());
        System.out.println("User ID "+req.getUserID());
        System.out.println("Pasword "+req.getPassword());

        System.out.println("User Valid? " +isvalid);
        return isvalid;
    }

    public boolean logout(String userID)
    {
        
        boolean isLogout = weatherRepo.logoutUser(userID);
        return isLogout;
    }

   
}
