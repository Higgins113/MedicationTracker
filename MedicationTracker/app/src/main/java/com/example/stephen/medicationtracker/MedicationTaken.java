package com.example.stephen.medicationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MedicationTaken extends AppCompatActivity implements View.OnClickListener{
    ImageButton amountTakenPlus, amountTakenMinus;
    TextView textViewAmountTaken, timeTaken, dateTaken;
    RadioButton taken, notTaken;
    Button confirmButton;
    RadioGroup radioGroup;
    RadioButton check;
    MyDBHandler dbHandler;
    Intent intent = getIntent();

    String username;
    String medName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_taken);
        dbHandler = new MyDBHandler(this,null,null,12);

        onNewIntent(getIntent());
        amountTakenPlus = (ImageButton) findViewById(R.id.amountTakenPlus);
        amountTakenMinus = (ImageButton) findViewById(R.id.amountTakenMinus);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        textViewAmountTaken = (TextView) findViewById(R.id.textViewAmountTaken);

        taken = (RadioButton) findViewById(R.id.taken);
        notTaken = (RadioButton) findViewById(R.id.notTaken);

        confirmButton = (Button) findViewById(R.id.confirmButton);

        amountTakenPlus.setOnClickListener(this);
        amountTakenMinus.setOnClickListener(this);

     //   taken.setOnClickListener(this);
      //  notTaken.setOnClickListener(this);
        confirmButton.setOnClickListener(this);


    }

    @Override
    public void onNewIntent(Intent intent){
        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey("username"))
            {
                username = extras.getString("username");
            }

            if(extras.containsKey("medName"))
            {
                medName = extras.getString("medName");
            }
        }


    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.amountTakenPlus:
                int dosagePlus;
                String getDosePlus = textViewAmountTaken.getText().toString();
                dosagePlus = Integer.parseInt(getDosePlus);
                dosagePlus ++;
                textViewAmountTaken.setText(String.valueOf(dosagePlus));
                break;

            case R.id.amountTakenMinus:
                int dosageMinus;
                String getDoseMinus = textViewAmountTaken.getText().toString();
                dosageMinus = Integer.parseInt(getDoseMinus);
                dosageMinus --;
                textViewAmountTaken.setText(String.valueOf(dosageMinus));
                break;

           // case R.id.timeTaken:
           ///     break;

         //   case R.id.dateTaken:
         //       break;


            case R.id.confirmButton:
                int selectedId = radioGroup.getCheckedRadioButtonId();
                check = (RadioButton) findViewById(selectedId);
                if(check.getText().equals("Taken")){

                    String taken = textViewAmountTaken.getText().toString();
                    int amountTaken = Integer.parseInt(taken);
                    Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),medName, Toast.LENGTH_LONG).show();

                     String supply = dbHandler.checkSupply(username, medName);
                    int quantity = Integer.parseInt(supply);
                    quantity = quantity - amountTaken;
                    String newQuantity = String.valueOf(quantity);
                    dbHandler.reduceSupply(username,newQuantity);
                    finish();
                }

                else if(check.getText().equals("Not Taken")){
                   // Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),medName, Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

}
