package com.example.stephen.medicationtracker;

import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;


public class AddingMedication extends AppCompatActivity implements View.OnClickListener{

    Button addMedication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_medication);
        addMedication = (Button) findViewById(R.id.buttonAddMedication);

        addMedication.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonAddMedication:
                startActivity(new Intent(this, Inventory.class));
                break;
        }
    }


}

