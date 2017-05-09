package com.example.stephen.medicationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Display extends AppCompatActivity {
    ArrayList<String> names ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent = getIntent();
         names = intent.getStringArrayListExtra("names");
        ListView listView  = (ListView) findViewById(R.id.listViewDisplay);

        //names.add("test");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
    }
}
