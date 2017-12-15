package com.example.nicol.joynus;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.UserDAO;
import dtomodels.userDTO.RegisterFormDTO;
import taskmodels.RegisterUserPackage;
import utility.ResponseCodeChecker;

public class RegisterActivity extends AppCompatActivity
{
    private Button goBackButton;
    private Button registerButton;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private EditText eMailInput;
    private EditText editDate;
    private Context context = this;
    private UserDAO userDAO;
    String dateFormat = "dd.MM.yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.FRENCH);
    DatePickerDialog.OnDateSetListener date;
    private Calendar selectedDate = Calendar.getInstance();
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDAO = new UserDAO();
        goBackButton = (Button) findViewById(R.id.RegisterGoBackButton);
        registerButton = (Button) findViewById(R.id.RegisterCreateAccountButton);
        firstNameInput = (EditText) findViewById(R.id.RegisterFirstNameInput);
        lastNameInput = (EditText) findViewById(R.id.RegisterLastNameInput);
        passwordInput = (EditText) findViewById(R.id.RegisterPasswordInput);
        confirmPasswordInput = (EditText) findViewById(R.id.RegisterConfirmPasswordInput);
        eMailInput = (EditText) findViewById(R.id.RegisterEmailInput);
        editDate = (EditText) findViewById(R.id.RegisterEditDate);
        goBackButton.setOnClickListener(goBackButtonOnClickListener());
        registerButton.setOnClickListener(registerButtonOnClickListener());
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        editDate.setText(dateString);
        date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                selectedDate.set(Calendar.YEAR,year);
                selectedDate.set(Calendar.MONTH,monthOfYear);
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                editDate.setText(sdf.format(selectedDate.getTime()));
            }
        };

        editDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(context,date,selectedDate.get(Calendar.YEAR),selectedDate.get(Calendar.MONTH),selectedDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public View.OnClickListener goBackButtonOnClickListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        };
        return listener;
    }
    public View.OnClickListener registerButtonOnClickListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String firstNameValue = firstNameInput.getText().toString();
                String lastNameValue= lastNameInput.getText().toString();
                String passwordValue = passwordInput.getText().toString();
                String confirmPasswordValue = confirmPasswordInput.getText().toString();
                String eMailValue = eMailInput.getText().toString();
                Date parsedSelectedDate= new Date(selectedDate.get(Calendar.YEAR)-1900,selectedDate.get(Calendar.MONTH),selectedDate.get(Calendar.DAY_OF_MONTH));
                tryToRegister(firstNameValue,lastNameValue,eMailValue,passwordValue,confirmPasswordValue,parsedSelectedDate);
            }
        };
        return listener;
    }
    public void tryToRegister(String firstName,String lastName, String eMail, String password, String confirmedPassword, Date birthDate)
    {
        RegisterFormDTO form = new RegisterFormDTO(firstName,lastName,confirmedPassword,eMail,birthDate,password);
        boolean succeeded = validateRegisterFormData(form);
        if(succeeded)
        {
            RegisterUserPackage packageToSend = new RegisterUserPackage();
            packageToSend.setFormToSend(form);
            packageToSend.setSender(this);
            userDAO.registerUser(packageToSend);
        }
    }
    public boolean validateRegisterFormData(RegisterFormDTO form)
    {
        String message;

        if(firstNameCheck(form.getFirstName()))
        {
            if(lastNameCheck(form.getLastName()))
            {
               if(passwordCheck(form.getPassword()))
               {
                   if(confirmPasswordCheck(form.getPassword(),form.getConfirmedPassword()))
                   {
                       if(emailCheck(form.getEmail()))
                       {
                           if(birthdateCheck(form.getBirthDate()))
                           {
                               message = getString(R.string.register_processing);
                               ViewStaticMethods.displayMessage(message);
                               return true;
                           }
                       }
                   }
               }
            }
        }
        return false;
    }
    public boolean firstNameCheck(String firstName)
    {
        String message;
        if(firstName.equals(""))
        {
            message = getString(R.string.register_error_first_name_required);
            ViewStaticMethods.displayMessage(message);
            return false;
        }

        if(firstName.length()<2 || firstName.length()>100)
        {
            message = getString(R.string.register_error_first_name_length);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        int firstNameLetterCount = 0;
        for(int i = 0, n = firstName.length() ; i < n ; i++)
        {
            char c = firstName.charAt(i);
            if(!Character.isLetter(c) && c != ' ' && c!='-')
            {
                message = message = getString(R.string.register_error_first_name_composition);
                ViewStaticMethods.displayMessage(message);
                return false;
            }
            if(Character.isLetter(c))
            {
                firstNameLetterCount ++;
            }
        }
        if(firstNameLetterCount < 2)
        {
            message = getString(R.string.register_error_first_name_two_letters);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        return true;
    }
    public boolean lastNameCheck(String lastName)
    {
        String message;
        if(lastName.equals(""))
        {
            message = getString(R.string.register_error_last_name_required);
            ViewStaticMethods.displayMessage(message);
            return false;
        }

        if(lastName.length()<2 || lastName.length()>100)
        {
            message = getString(R.string.register_error_last_name_length);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        int lastNameLetterCount = 0;
        for(int i = 0, n = lastName.length() ; i < n ; i++)
        {
            char c = lastName.charAt(i);
            if(!Character.isLetter(c) && c != ' ' && c!='-')
            {
                message = getString(R.string.register_error_last_name_composition);
                ViewStaticMethods.displayMessage(message);
                return false;
            }
            if(Character.isLetter(c))
            {
                lastNameLetterCount ++;
            }
        }
        if(lastNameLetterCount < 2)
        {
            message = getString(R.string.register_error_last_name_two_letters);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        return true;
    }
    public boolean passwordCheck(String password) {
        String message;
        if (password.equals("")) {
            message = getString(R.string.register_error_password_required);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if (password.length() < 6 || password.length() > 100) {
            message = getString(R.string.register_error_password_length);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        boolean containsASpecialCharacter = false;
        boolean containsACapitalLetter = false;
        boolean containsASmallLetter = false;
        boolean containsADigit = false;
        int i;
        for (i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                containsADigit = true;
            } else {
                if (!Character.isLetter(password.charAt(i))) {
                    containsASpecialCharacter = true;
                } else {
                    if (Character.isLowerCase(password.charAt(i))) {
                        containsASmallLetter = true;
                    } else {
                        if (Character.isUpperCase(password.charAt(i))) {
                            containsACapitalLetter = true;
                        }
                    }
                }
            }
        }
        if(!containsADigit)
        {
            message = getString(R.string.register_error_password_digit);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(!containsASpecialCharacter)
        {
            message = getString(R.string.register_error_password_special_character);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(!containsACapitalLetter)
        {
            message = getString(R.string.register_error_password_capital_letter);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(!containsASmallLetter)
        {
            message = getString(R.string.register_error_password_small_letter);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        return true;
    }
    public boolean confirmPasswordCheck(String password, String confirmPassword)
    {
        String message;
        if(confirmPassword.equals(""))
        {
            message = getString(R.string.register_error_confirm_password_required);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        if(!confirmPassword.equals(password))
        {
            message = getString(R.string.register_error_confirm_password_not_same);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        return true;
    }

    public boolean emailCheck(String email)
    {
        String message;
        if(email.equals(""))
        {
            message = getString(R.string.register_error_email_required);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher emailMatch = emailPattern.matcher(email);
        if(!emailMatch.matches())
        {
            message = getString(R.string.register_error_email_not_valid);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        return true;
    }
    public boolean birthdateCheck(Date birthdateToCheck)
    {
        String message;
        Date now = new Date();
        if(now.before(birthdateToCheck))
        {
            message = getString(R.string.register_error_birthdate_in_future);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        int age = now.getYear() - birthdateToCheck.getYear();
        if(now.getMonth() < birthdateToCheck.getMonth() || (now.getMonth() == birthdateToCheck.getMonth() && now.getDay() < birthdateToCheck.getDay()))
        {
            age--;
        }
        if(age < 12)
        {
            message = getString(R.string.register_error_birthdate_too_young);
            ViewStaticMethods.displayMessage(message);
            return false;
        }
        return true;
    }
    public void notifyRegisterTaskDone(RegisterUserPackage receivedPackage)
    {
        if(!ResponseCodeChecker.checkWhetherTaskSucceeded(receivedPackage.getResponseCode()))
        {
            ViewStaticMethods.displayMessage(ResponseCodeChecker.getResponseCodeErrorMessage(receivedPackage.getResponseCode()));
        }
        else
        {
            ViewStaticMethods.displayMessage(getString(R.string.register_succeeded));
        }
    }

    public void notifyRegisterTaskFailed(int responseCode)
    {
        String message = getString(R.string.authentication_failed);
        message += " : ";
        message += ResponseCodeChecker.getResponseCodeErrorMessage(responseCode);
        ViewStaticMethods.displayMessage(message);
    }
}
