package com.example.stephen.medicationtracker;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class PickTimes extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,AdapterView.OnItemClickListener, View.OnClickListener {
    int pickedHour,pickedMinute;
    ArrayList<String> list = new ArrayList<String>();
    PickTimesCustomAdapter adapter;
    String time;
    ArrayList<String> timesList = new ArrayList<String>();
    Button addTime;
    ListView listView ;
    TextView listItemText ;
    ImageButton dosePlusButtonAdd;
    private ArrayList<String> dosages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_times);

        //generate list
      //  ArrayAdapter<Medication> adapter;

       // list.add("item1");


        addTime = (Button) findViewById(R.id.buttonAddTime);
        adapter= new PickTimesCustomAdapter(timesList, this, dosages);
        listView= (ListView) findViewById(R.id.listView3);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        addTime.setOnClickListener(this);
        Button returnButton = (Button)findViewById(R.id.buttonReturn);
        listItemText = (TextView)findViewById(R.id.textViewDoseAdd);
        returnButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddTime:
                DialogFragment newFragment1 = new TimePickerFragment();
                newFragment1.show(getFragmentManager(), "timePicker");

                break;
            case R.id.buttonReturn:
                for(String times: timesList){
                    Toast.makeText(getApplicationContext(), times, Toast.LENGTH_LONG).show();
                }
                //finish();


                break;

        }
    }

    public void onItemClick(AdapterView<?> l, View v, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Set Time?");
        builder.setCancelable(true);

        builder.setPositiveButton
                (
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DialogFragment newFragment1 = new TimePickerFragment();
                                newFragment1.show(getFragmentManager(), "timePicker");
                                list.add("new");

                                adapter.notifyDataSetChanged();
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
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){

        pickedHour=hourOfDay;
        pickedMinute=minute;
        if(minute <10){
            time = (String.valueOf(pickedHour)+ ":0" + String.valueOf(minute));
        }

        else{
            time = (String.valueOf(pickedHour)+ ":" + String.valueOf(minute));
        }
        // textViewStartTime.setText(time);
        timesList.add(time);
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Select a Dosage");
        builderSingle.setMessage("Dosage");
        final EditText edittext = new EditText(getApplicationContext());
        builderSingle.setView(edittext);

        builderSingle.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String YouEditTextValue = edittext.getText().toString();
                dosages.add(YouEditTextValue);

            }
        });

        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.show();
        adapter= new PickTimesCustomAdapter(timesList, this, dosages);
        listView.setAdapter(adapter);

    }


}
