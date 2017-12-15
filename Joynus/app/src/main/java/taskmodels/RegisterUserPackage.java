package taskmodels;


import com.example.nicol.joynus.RegisterActivity;

import dtomodels.userDTO.RegisterFormDTO;

public class RegisterUserPackage
{
    private RegisterFormDTO formToSend;
    private int responseCode;
    private RegisterActivity sender;

    public RegisterUserPackage()
    {

    }

    public RegisterFormDTO getFormToSend() {
        return formToSend;
    }

    public void setFormToSend(RegisterFormDTO formToSend) {
        this.formToSend = formToSend;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public RegisterActivity getSender() {
        return sender;
    }

    public void setSender(RegisterActivity sender) {
        this.sender = sender;
    }
}
