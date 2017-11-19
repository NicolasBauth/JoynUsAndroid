package model;


import java.util.ArrayList;
import java.util.Date;

public class AuthenticatedUser extends User
{
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
