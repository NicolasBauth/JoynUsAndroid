package utility;


public class ConnectionStringsManager
{
    public ConnectionStringsManager()
    {

    }
    public String getApiGeneralConnectionString()
    {
        return "http://apijoynus.azurewebsites.net";
    }
    public String getApiUserProfilesConnectionString()
    {
        return getApiGeneralConnectionString()+"/api/UserProfiles";
    }
    public String getApiEventsConnectionString()
    {
        return getApiGeneralConnectionString()+"/api/Events";
    }
    public String getTokenConnectionString()
    {
        return getApiGeneralConnectionString()+"/token";
    }
    public String getCategoriesConnectionString()
    {
        return getApiGeneralConnectionString()+"/api/Categories";
    }
}
