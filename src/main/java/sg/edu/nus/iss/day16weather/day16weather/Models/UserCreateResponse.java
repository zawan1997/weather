package sg.edu.nus.iss.day16weather.day16weather.Models;

import jakarta.json.JsonObject;

public class UserCreateResponse {

    //items in the JSON api we want
    private String userID;
    private String id;
    private String createdAt;

   

    public void setId(String id) {
        this.id = id;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getId() {
        return id;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
  
    //deserialisation. 
    //JSON to model
    //creating a JSON object with the following contents
    //creating as a java object becuase java cannot read JSON directly
    public static UserCreateResponse create(JsonObject jo) {
        UserCreateResponse w = new UserCreateResponse();
        w.setId(jo.getString("id"));
        w.setUserID(jo.getString("userID"));
        w.setCreatedAt(jo.getString("createdAt"));
        return w;

    }
    //serialisation
    //forming it back to JSON after reading it in the JSON READER
    // public JsonObject toJson() {
    //     return Json.createObjectBuilder()
    //     .add("name", name)
    //     .add("job", job)
    //     .build();
    // }

}
