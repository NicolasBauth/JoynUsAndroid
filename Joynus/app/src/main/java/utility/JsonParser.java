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
    public static String jsonStringFromConnection (HttpURLConnection connection) throws Exception
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
    public static Object getJavaObjectFromJsonString(String jsonString, Object objectToFill) throws Exception
    {
            JSONObject jsonObject = new JSONObject(jsonString);
            Gson object = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Class classOfObject = objectToFill.getClass();
            Object objectToReturn;
            objectToReturn = object.fromJson(jsonObject.toString(),classOfObject);
            return objectToReturn;
    }
}
