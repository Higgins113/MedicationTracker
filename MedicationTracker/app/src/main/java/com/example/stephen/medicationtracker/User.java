package com.example.stephen.medicationtracker;


public class User
{
    String username, password, email;

    public User (String username, String password, String email)
    {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User (String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public User(){

    }



    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}