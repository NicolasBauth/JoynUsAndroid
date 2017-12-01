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
        }
        catch(Exception e)
        {

        }
    }
}
