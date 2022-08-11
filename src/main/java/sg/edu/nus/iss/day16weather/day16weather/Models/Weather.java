package sg.edu.nus.iss.day16weather.day16weather.Models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Weather {

    //items in the JSON api we want
    private String main;
    private String description;
    private String icon;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    //JSON to model
    //creating a JSON object with the following contents
    //creating as a java object becuase java cannot read JSON directly
    public static Weather create(JsonObject jo) {
        Weather w = new Weather();
        w.setMain(jo.getString("main"));
        w.setDescription(jo.getString("description"));
        w.setIcon(jo.getString("icon"));
        return w;

    }
    //forming it back to JSON after reading it in the JSON READER
    public JsonObject toJson() {
        return Json.createObjectBuilder()
        .add("main", main)
        .add("description", description)
        .add("icon", icon)
        .build();
    }

}
