package com.example.stephen.medicationtracker;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ImageButton buttonAdd;
    String username;
    MyDBHandler dbHandler;
    ArrayList<Medication> medications = new ArrayList<Medication>();
    ArrayAdapter<Medication> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        dbHandler = new MyDBHandler(this, null, null, 12);
        buttonAdd = (ImageButton) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        medications = dbHandler.getAllMedicationsByUser(username);

        adapter = new MedicationAdapter(this,medications);

        //adapter = new ArrayAdapter<Medication>(this, R.layout.activity_list_view, medications);

        ListView listView = (ListView) findViewById(R.id.listView2);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }


    public void onItemClick(AdapterView<?> l, View v, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this medication?");
        builder.setCancelable(true);

        builder.setPositiveButton
                (
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Medication medication = adapter.getItem(position);
                                medication.setUsername(username);

                                dbHandler.deleteMedication(medication);
                                finish();
                                 startActivity(getIntent());

                            }
                        });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

      //  Toast.makeText(this, position + " selected", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonAdd:

                Intent i = new Intent(this,Inventory.class);
                i.putExtra("username", username);
                startActivity(i);
                break;
        }
    }


}
