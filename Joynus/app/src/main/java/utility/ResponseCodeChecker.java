package utility;



public class ResponseCodeChecker
{
    public static boolean checkWhetherTaskSucceeded(int responseCode)
    {
        if(responseCode >= 200 && responseCode <=299)
        {
            return true;
        }
        return false;
    }
}
