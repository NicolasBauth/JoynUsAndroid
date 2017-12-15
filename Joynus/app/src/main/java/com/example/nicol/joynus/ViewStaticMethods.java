package com.example.nicol.joynus;


import android.widget.Toast;

public class ViewStaticMethods
{
    public static void displayMessage(String message)
    {
        Toast.makeText(LoginActivity.getContextOfApplication(),message,Toast.LENGTH_LONG).show();
    }
}
