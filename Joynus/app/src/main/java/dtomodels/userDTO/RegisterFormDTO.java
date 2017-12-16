package dtomodels.userDTO;


import java.util.Date;

public class RegisterFormDTO
{
    private String FirstName;
    private String LastName;
    private String ConfirmPassword;
    private String Email;
    private Date Birthdate;
    private String Password;

    public RegisterFormDTO(String firstName, String lastName, String confirmedPassword, String email, Date birthDate, String password)
    {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.ConfirmPassword = confirmedPassword;
        this.Email = email;
        this.Birthdate = birthDate;
        this.Password = password;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.ConfirmPassword = confirmPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public Date getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.Birthdate = birthdate;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
