package utility;
import com.google.gson.*;

import org.json.JSONObject;


public class JsonParser
{
    public static <T> String objectToJson (T object)
    {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        return jsonString;
    }
}
