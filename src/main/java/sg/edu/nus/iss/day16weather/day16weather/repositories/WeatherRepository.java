package sg.edu.nus.iss.day16weather.day16weather.repositories;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.joran.conditional.ElseAction;


@Repository
public class WeatherRepository {

    @Value("${weather.cache.duration}")
    private Long cacheTime;
    
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String,String> redisTemplate;

    //Redis is KEY VALUE
    //setting the value for the key city
    public void save(String city, String payload) {
        //valueOp now carries the value 
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        valueOp.set(city.toLowerCase(), payload, Duration.ofMinutes(cacheTime));
    }
    public boolean saveUser(String userID, String password)
    {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        System.out.println("Check if if user already registered "+valueOp.get(userID));
        if(valueOp.get(userID)==null)
        {
            valueOp.set(userID.toLowerCase(), password, Duration.ofMinutes(cacheTime));
            System.out.println("password being stored "+password);
            return true;
        }
        else
        return false;
    }

    public Optional<String> get(String city) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        String value = valueOp.get(city.toLowerCase());
        if(null == value) {
            return Optional.empty(); //empty box
        }
        return Optional.of(value); //box with data
    }
    public boolean isValidUser(String userID, String password) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        System.out.println(valueOp.get(userID.toLowerCase()));
        String passwordStored = valueOp.get(userID.toLowerCase());
        System.out.println("passwordStored "+passwordStored);
        if(password.equals(passwordStored)) {
            saveLoggedInUser(userID);
            return true;
        }
        return false;
    }
    public void saveLoggedInUser(String userID) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        valueOp.set(userID.toLowerCase()+"is_loggedin", "1", Duration.ofMinutes(cacheTime));
        System.out.println("Token is set for user "+userID);
    }
    public boolean logoutUser(String userID) {
        boolean isLogout = false;
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        if(isLoggedInUser(userID))
        {
            String actualResult = valueOp.getAndDelete(userID.toLowerCase()+"is_loggedin");
            System.out.println("user logged out "+userID);  
            System.out.println("actualResult "+actualResult);  
            isLogout=true;
         
        }
        else{
            System.out.println("No need to logout as user is not logged in");  
            isLogout=true;
        }
        return isLogout;
    }
    public boolean isLoggedInUser(String userID) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        
        String actualResult = valueOp.get(userID.toLowerCase()+"is_loggedin");
        String isPresent = "1";
        System.out.println("Is User Logged In "+actualResult);
       if(isPresent.equals(actualResult))
       {

            System.out.println( "User is currently logged In");
            return true;
        }
        System.out.println( "User is currently NOT logged In");
        return false;
    }
}
