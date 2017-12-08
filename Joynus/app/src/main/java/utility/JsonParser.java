package utility;
import com.google.gson.*;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class JsonParser
{
    public static <T> String objectToJson (T object)
    {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        return jsonString;
    }
    public static String jsonStringFromConnection (HttpURLConnection connection)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String jsonResponseString = "";
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            connection.disconnect();
            jsonResponseString = stringBuilder.toString();
            return jsonResponseString;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public static Object getJavaObjectFromJsonString(String jsonString, Object objectToFill)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(jsonString);
            Gson object = new GsonBuilder().create();
            Class classOfObject = objectToFill.getClass();
            objectToFill = object.fromJson(jsonObject.toString(),classOfObject);
            return objectToFill;
        }
        catch(Exception e)
        {
            String message = e.getMessage();
            return null;
        }
    }
}
