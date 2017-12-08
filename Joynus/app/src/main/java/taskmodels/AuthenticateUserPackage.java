package taskmodels;


import dtomodels.userDTO.UserCredentialsDTO;
import model.AuthenticatedUser;

public class AuthenticateUserPackage
{
    private UserCredentialsDTO userCredentials;
    private AuthenticatedUser userResponse;
    private int responseCode;
    public AuthenticateUserPackage()
    {
        userCredentials = new UserCredentialsDTO();
        userResponse = new AuthenticatedUser();
    }
    public UserCredentialsDTO getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentialsDTO userCredentials) {
        this.userCredentials = userCredentials;
    }

    public AuthenticatedUser getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(AuthenticatedUser userResponse) {
        this.userResponse = userResponse;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
