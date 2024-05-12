package com.example.myapplication.common;

public class OffreValide {

    public boolean isNameEmpty(String value){
        return value.isEmpty() || value.equals(" ");
    }

    public boolean isEmailValid(String value){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return (value).matches(emailPattern);
    }

    public boolean isPasswordValid(String password){
        String passwordPatter = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        return password.matches(passwordPatter);
    }

    public boolean isTpValid(String tp){
        return (tp.length() == 10);
    }

}
