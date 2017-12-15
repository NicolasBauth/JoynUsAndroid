package controller;


import android.util.Log;

import com.example.nicol.joynus.LoginActivity;
import com.example.nicol.joynus.ViewStaticMethods;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.UserDAO;
import dtomodels.userDTO.RegisterFormDTO;
import dtomodels.userDTO.UserCredentialsDTO;
import taskmodels.AuthenticateUserPackage;
import utility.ResponseCodeChecker;

public class UserController
{
    private UserDAO userDAO;
    public UserController()
    {
        userDAO = new UserDAO();
    }
    public void authenticateUser(String username,String password)
    {
        UserCredentialsDTO userCredentials = new UserCredentialsDTO();
        userCredentials.setUsername(username);
        userCredentials.setPassword(password);
        AuthenticateUserPackage packageToFill = new AuthenticateUserPackage();
        packageToFill.setUserCredentials(userCredentials);
        userDAO.authenticateUser(packageToFill);
    }
    public static void notifyAuthenticatedUser(AuthenticateUserPackage response)
    {
        if(!ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
        {
            ViewStaticMethods.displayMessage(ResponseCodeChecker.getResponseCodeErrorMessage(response.getResponseCode()));
        }
        else
        {
            Log.i("UserDAOTag","Authentification réussie pour " +response.getUserResponse().getUsername());
        }
    }
    public static void tryToRegister(String firstName,String lastName, String eMail, String password, String confirmedPassword, Date birthDate)
    {
        RegisterFormDTO form = new RegisterFormDTO(firstName,lastName,confirmedPassword,eMail,birthDate,password);
        boolean succeeded = validateRegisterFormData(form);
        if(succeeded)
        {

        }
    }
    public static boolean validateRegisterFormData(RegisterFormDTO form)
    {
        String message;
        if(form.getFirstName() == null)
        {
            message = "Erreur : Votre Prénom est requis";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(form.getFirstName().length()<2 || form.getFirstName().length()>100)
        {
            message = "Erreur : la longueur de votre prénom doit se situer entre 2 et 100 caractères.";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        for(int i = 0, n = form.getFirstName().length() ; i < n ; i++)
        {
            char c = form.getFirstName().charAt(i);
            if(!Character.isLetter(c) && c != ' ' && c!='-')
            {
                message = "Erreur: votre prénom ne peut être composé que de lettres, d'espaces et de traits d'union.";
                ViewStaticMethods.displayMessage(message);
                return false;
            }
        }
        if(form.getLastName() == null)
        {
            message = "Erreur : Votre nom est requis";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(form.getLastName().length()<2 || form.getLastName().length()>100)
        {
            message = "Erreur : la longueur de votre nom doit se situer entre 2 et 100 caractères.";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        for(int i = 0, n = form.getLastName().length() ; i < n ; i++)
        {
            char c = form.getLastName().charAt(i);
            if(!Character.isLetter(c) && c != ' ' && c!='-')
            {
                message = "Erreur: votre nom de famille ne peut être composé que de lettres, d'espaces et de traits d'union.";
                ViewStaticMethods.displayMessage(message);
                return false;
            }
        }
        if(form.getPassword() == null)
        {
            message = "Erreur : Votre mot de passe est requis";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(form.getPassword().length() < 6 || form.getPassword().length() > 100)
        {
            message = "Erreur : La longueur de votre mot de passe doit impérativement se situer entre 6 et 100 caractères.";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(form.getConfirmedPassword() == null)
        {
            message = "Erreur : la confirmation de mot de passe est requise.";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(!form.getConfirmedPassword().equals(form.getPassword()))
        {
            message = "Erreur : le mot de passe et la confirmation de mot de passe doivent être identiques.";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(form.getEmail() == null)
        {
            message = "Erreur : votre e-mail est requis";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher emailMatch = emailPattern.matcher(form.getEmail());
        if(!emailMatch.matches())
        {
            message = "Erreur : l'adresse e-mail que vous avez rentrée n'est pas valide";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        Date now = new Date();
        if(now.before(form.getBirthDate()))
        {
            message = "Erreur : la date de naissance que vous avez rentrée est dans le futur!";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        int age = now.getYear() - form.getBirthDate().getYear();
        if(now.getMonth() < form.getBirthDate().getMonth() || (now.getMonth() == form.getBirthDate().getMonth() && now.getDay() < form.getBirthDate().getDay()))
        {
            age--;
        }
        if(age < 12)
        {
            message = "Erreur : vous devez être agé de 12 ans au minimum pour pouvoir utiliser l'application";
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        message = "Formulaire envoyé. Veuillez patienter...";
        ViewStaticMethods.displayMessage(message);
        return true;
    }
}
