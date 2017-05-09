package com.example.stephen.medicationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Stephen on 06-03-2017.
 */
public class Inventory extends AppCompatActivity implements View.OnClickListener {

    Button buttonSchedule;
    EditText medicationName,medicationType,ingestionMethod,medicationQuantity;
    String medName,medType,ingestion,medQuantity,username;
   // MyDBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");


        buttonSchedule = (Button) findViewById(R.id.buttonSchedule);
        medicationName = (EditText) findViewById(R.id.editTextMedicationName);
        medicationType = (EditText) findViewById(R.id.editTextMedicationType);
        ingestionMethod = (EditText) findViewById(R.id.editTextMedicationIngestion);
        medicationQuantity = (EditText) findViewById(R.id.editTextMedicationQuantity);

       // medicationName.setText("Paracetamol"); //TESTING
       // medicationType.setText("Tablet"); //TESTING
       // ingestionMethod.setText("Mouth"); //TESTING
      //  medicationQuantity.setText("20"); //TESTING

     //   dbHandler = new MyDBHandler(this,null,null,3);

        buttonSchedule.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonSchedule:
                medName = medicationName.getText().toString();
                medType = medicationType.getText().toString();
                ingestion = ingestionMethod.getText().toString();
                medQuantity = medicationQuantity.getText().toString();

              //  Medication medication = new Medication(username,medName,medType,ingestion,medQuantity);
              //  dbHandler.addMedication(medication);

                Intent i = new Intent(this,Schedule.class);
                i.putExtra("username", username);
                i.putExtra("medname", medName);
                i.putExtra("medtype", medType);
                i.putExtra("ingestion", ingestion);
                i.putExtra("medquantity", medQuantity);

                startActivity(i);

                finish();
                break;
        }
    }
}