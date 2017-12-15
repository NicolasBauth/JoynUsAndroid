package utility;


import android.content.Context;
import android.content.res.Resources;

import com.example.nicol.joynus.LoginActivity;
import com.example.nicol.joynus.R;

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
    public static String getResponseCodeErrorMessage(int responseCode)
    {
        Context applicationContext = LoginActivity.getContextOfApplication();
        switch(responseCode)
        {
            case 400:
                return applicationContext.getString(R.string.error_username_or_password);
            case 401:
                return applicationContext.getString(R.string.error_authentication);
            case 404:
                return applicationContext.getString(R.string.error_no_internet);
            default:
                return applicationContext.getString(R.string.error_unidentified);
        }
    }
}
