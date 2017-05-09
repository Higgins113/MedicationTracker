package com.example.stephen.medicationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText username, password, confirmPassword, email;
    Button buttonRegister;
    MyDBHandler dbHandler;
    String registerUsername, registerPassword, registerConfirmPassword, registerEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.editTextRegisterUsername);
        password = (EditText) findViewById(R.id.editTextRegisterPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextPasswordConfirm);
        email = (EditText) findViewById(R.id.editTextEmail);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        dbHandler = new MyDBHandler(this,null,null,12);

        buttonRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonRegister:
                reg();
                break;
        }
    }

    public void reg() {

        registerUsername = username.getText().toString();
        registerPassword = password.getText().toString();
        registerConfirmPassword = confirmPassword.getText().toString();
        registerEmail = email.getText().toString();

        if(registerPassword.equals(registerConfirmPassword)) {
            User user = new User(registerUsername, registerPassword, registerEmail);
            dbHandler.addUser(user);
            finish();
        }

        else{
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
        }


    }

}
