package controller;


import dao.UserDAO;
import dtomodels.userDTO.UserCredentialsDTO;
import taskmodels.AuthenticateUserPackage;

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
}
