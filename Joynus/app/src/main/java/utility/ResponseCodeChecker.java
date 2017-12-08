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
    public static String getResponseCodeErrorMessage(int responseCode)
    {
        switch(responseCode)
        {
            case 401:
                return "session perdue ou mauvaise combinaison de login/mot de passe. Veuillez réessayer de vous connecter.";
            case 404:
                return "Il semble que vous ne soyez pas connecté à internet. Veuillez vérifier votre connexion à internet";
            default:
                return "Une erreur de transmission s'est produite. Essayez de relancer l'application.";
        }
    }
}
