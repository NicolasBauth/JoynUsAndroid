package dtomodels.userDTO;


import java.util.Date;

public class RegisterFormDTO
{
    private String firstName;
    private String lastName;
    private String confirmedPassword;
    private String email;
    private Date birthDate;
    private String password;

    public RegisterFormDTO(String firstName, String lastName, String confirmedPassword, String email, Date birthDate, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.confirmedPassword = confirmedPassword;
        this.email = email;
        this.birthDate = birthDate;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
