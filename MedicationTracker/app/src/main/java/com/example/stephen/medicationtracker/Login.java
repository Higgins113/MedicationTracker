package com.example.stephen.medicationtracker;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    Button buttonLogin, buttonTestMap;
    TextView textViewRegister;
    MyDBHandler dbHandler;
    Boolean check;

    String loginUsername, loginPassword;
String username1= "S";
    String password1= "t";

    String query = "SELECT * FROM user WHERE USERNAME = " + username1 + " AND Password = " + password1 + ";" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.editTextRegisterUsername);
        password = (EditText) findViewById(R.id.editTextRegisterPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonTestMap = (Button) findViewById(R.id.buttonTestMap);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        dbHandler = new MyDBHandler(this,null,null,12);
     //   username.setText("s"); //TESTING PURPOSES
    //    password.setText("s"); // FOR REMOVAL
        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        buttonTestMap.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonLogin:
                loginUsername = username.getText().toString();
                loginPassword = password.getText().toString();
                User user = new User(loginUsername,loginPassword);

                if(loginUsername.equals("admin") && loginPassword.equals("admin")){
                    Intent i = new Intent(this,AdminHome.class);
                    //Intent i = new Intent(this,PickTimes.class);
                    startActivity(i);
                }

                else{
                    check = authenticate(user);
                    if (check == true)
                    {
                        //    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this,Home.class);
                        i.putExtra("username", loginUsername);
                        startActivity(i);
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    }
                }

                break;
            case R.id.textViewRegister:
                startActivity(new Intent(this, Register.class));

                break;
            case R.id.buttonTestMap:

                startActivity(new Intent(this,MapsActivity.class));
                break;
        }
    }

    private boolean authenticate(User user)
    {
        //String username = user.getUsername();
        //Boolean found = dbHandler.getUser(user);
        //User foundUser = dbHandler.getUser(user);
        String username = dbHandler.databaseToString(user);
        String password = dbHandler.databaseToStringPassword(user);

        if(username!="" && password!="")
        {
            return true;
        }

        else
        {
            return false;
        }
    }


}
