package taskmodels;


import dtomodels.userDTO.UserCredentialsDTO;
import model.User;

public class AuthenticateUserPackage
{
    private UserCredentialsDTO userCredentials;
    private User userResponse;
    private int responseCode;
    public AuthenticateUserPackage()
    {
        userCredentials = new UserCredentialsDTO();
        userResponse = new User();
    }
    public UserCredentialsDTO getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentialsDTO userCredentials) {
        this.userCredentials = userCredentials;
    }

    public User getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(User userResponse) {
        this.userResponse = userResponse;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
