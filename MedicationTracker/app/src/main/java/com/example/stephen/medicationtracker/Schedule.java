package com.example.stephen.medicationtracker;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.DialogFragment;

import android.widget.Toast;


/**
 * Created by Stephen on 06-03-2017.
 */
public class Schedule extends AppCompatActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener{

    TextView textViewStartTime, frequency;
    TextView dose;
    TextView textViewDatePick,addTime;
    Calendar calendar;
    MyDBHandler dbHandler;
    String username,choice;
    private DatePicker datePicker;

    Button finish;
    ImageButton addTimeButton, dosePlusButton, doseMinusButton,startDateButton, supplyButtonPlus, supplyButtonMinus;
    int year, month, day;
    String medName, medType, medQuantity, ingestion;
    int pickedHour,pickedMinute;
    String time;
    String duration;
TextView supply;
    ArrayList<String> timesList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule2);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        medName = intent.getStringExtra("medname");
        medQuantity = intent.getStringExtra("medquantity");
        medType = intent.getStringExtra("medtype");
        ingestion = intent.getStringExtra("ingestion");

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dbHandler = new MyDBHandler(this,null,null,12);

        textViewStartTime = (TextView) findViewById(R.id.textView20);

        dose = (TextView) findViewById(R.id.textViewDose);
        textViewDatePick = (TextView) findViewById(R.id.textViewDatePicks);
        textViewDatePick.setText(String.valueOf(day) +"/" +String.valueOf(month + 1) +"/"+ String.valueOf(year) );
        finish = (Button) findViewById(R.id.finishButton);
        addTimeButton = (ImageButton) findViewById(R.id.addTimeButton);
        dosePlusButton = (ImageButton) findViewById(R.id.dosePlusButton);
        doseMinusButton = (ImageButton) findViewById(R.id.doseMinusButton);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);


        addTimeButton.setOnClickListener(this);
        dosePlusButton.setOnClickListener(this);
        doseMinusButton.setOnClickListener(this);
        startDateButton.setOnClickListener(this);
       // textViewStartTime.setOnClickListener(this);
        finish.setOnClickListener(this);
        textViewDatePick.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.addTimeButton:
                DialogFragment newFragment1 = new TimePickerFragment();
                newFragment1.show(getFragmentManager(),"timePicker");
                break;
            case R.id.startDateButton:
                new DatePickerDialog(this,myDateListener, year, month,day).show();
                break;
            case R.id.dosePlusButton:
                int dosagePlus;
                String getDosePlus = dose.getText().toString();
                dosagePlus = Integer.parseInt(getDosePlus);
                dosagePlus ++;
                dose.setText(String.valueOf(dosagePlus));
                break;
            case R.id.doseMinusButton:
                int dosageMinus;
                String getDoseMinus = dose.getText().toString();
                dosageMinus = Integer.parseInt(getDoseMinus);
                dosageMinus --;
                dose.setText(String.valueOf(dosageMinus));
                break;
           /* case R.id.textViewFrequency:

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
                builderSingle.setTitle("Select a frequency");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Set Times");
                arrayAdapter.add("Daily");
                arrayAdapter.add("Weekly");
                arrayAdapter.add("Every two weeks");
                arrayAdapter.add("Monthly");
                arrayAdapter.add("Every X hours");

                builderSingle.setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = arrayAdapter.getItem(which);

                    }
                });

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
               /* builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = arrayAdapter.getItem(which);
                        duration = choice;

                    }

                });*/
              /*  builderSingle.setPositiveButton
                        (
                                "Confirm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        duration = choice;

                                        intervalSet();
                                        dialog.dismiss();
                                    }
                                });



                builderSingle.show();



                break;*/


            case R.id.finishButton:
                String startTime = timesList.get(0).toString();
                String dosage = dose.getText().toString();

                String date = textViewDatePick.getText().toString();
                String[] dateSplit = date.split("/");
                int day = Integer.valueOf(dateSplit[0]);
                int month = Integer.valueOf(dateSplit[1]);
                month = month -1;
                // String test = "5 days";
                int finalDosage = Integer.parseInt(dosage);
                int i = 0;

                //calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
                //calendar.set(Calendar.MINUTE, pickedMinute);
                // calendar.set(Calendar.SECOND, 0);

              /*  if(duration.equals("Daily")) {
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                }

                if(duration.equals("Weekly")) {
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
                }

                if(duration.equals("Every two weeks")) {
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*14, pendingIntent);
                }

                if(duration.equals("Monthly")) {
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*30, pendingIntent);
                }

                if (duration.indexOf("hours")!=-1){
                   // Toast.makeText(this, duration ,Toast.LENGTH_LONG).show();
                    int parseHour=0;
                   parseHour = Integer.parseInt(duration.replaceAll("[\\D]",""));
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR * parseHour, pendingIntent);
                }*/

              //  if(duration.equals("Set Times")) {

                    for(String times: timesList) {

                        Intent alarmIntent = new Intent(this, Receiver.class);
                        alarmIntent.putExtra("username",username);
                        alarmIntent.putExtra("medname",medName);
                        //  alarmIntent.putExtra("username",username);


                        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,i, alarmIntent, 0);

                        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,day);

                        String[] time = times.split(":");
                        pickedHour = Integer.valueOf(time[0]);
                        pickedMinute = Integer.valueOf(time[1]);

                        calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
                        calendar.set(Calendar.MINUTE, pickedMinute);
                        calendar.set(Calendar.SECOND, 0);

                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        Toast.makeText(getApplicationContext(),"Alarm set" , Toast.LENGTH_LONG).show();
                        i++;
              //      }

                    }

                String startDate = textViewDatePick.getText().toString();
                Medication medication = new Medication(username,medName,medType,ingestion,medQuantity,startDate,finalDosage,null);//duration);


                dbHandler.addMedication(medication);
                BackgroundTask backgroundTask = new BackgroundTask();
                backgroundTask.execute(username, medName, medType,ingestion,medQuantity,startDate,null,null);//,duration);

                Intent newIntent = new Intent(this,Home.class);
                newIntent.putExtra("username", username);
                startActivity(newIntent);
                finish();
                break;

        }
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

        if(textViewStartTime.getText().toString().equals("Pick Times")) {
            textViewStartTime.setText( time);
        }

        else {
            textViewStartTime.setText(textViewStartTime.getText().toString() + " " + time);
        }
        timesList.add(time);

    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int year, int month, int day ) {

                    textViewDatePick.setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
                }
            };

    public void intervalSet(){
        if(choice!=null && choice.equals("Every X hours")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setMessage("How many hours between medications?");
            builder.setCancelable(true);

            builder.setPositiveButton
                    (
                            "Confirm",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String hours = input.getText().toString();

                                    duration = duration.replace("X", hours);

                                    dialog.dismiss();
                                }
                            });

            builder.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    class BackgroundTask extends AsyncTask<String,Void,String>
    {

        String add_info_url;
        @Override
        protected void onPreExecute() {
            add_info_url= "http://sample-env-1.s8fqw4jpzp.us-east-1.elasticbeanstalk.com/AddMedication.php";
        }

        @Override
        protected String doInBackground(String... args) {

            String username,medName, medType, medQuantity, ingestion,startDate,finalDosage,duration;
            username = args[0];
            medName = args[1];
            medType = args[2];
            medQuantity = args[3];
            ingestion = args[4];
            startDate = args[5];
          //  finalDosage = args[6];
          //  duration = args[6];


            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("Username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("MedName", "UTF-8") + "=" + URLEncoder.encode(medName, "UTF-8") + "&" +
                        URLEncoder.encode("MedType", "UTF-8") + "=" + URLEncoder.encode(medType, "UTF-8") + "&" +
                        URLEncoder.encode("MedQuantity","UTF-8") + "=" + URLEncoder.encode(medQuantity, "UTF-8") + "&" +
                        URLEncoder.encode("Ingestion","UTF-8") + "=" + URLEncoder.encode(ingestion, "UTF-8") + "&" +
                        URLEncoder.encode("StartDate","UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8") + "&" +
                        URLEncoder.encode("Dosage","UTF-8") + "=" + URLEncoder.encode("test", "UTF-8") + "&" +
                        URLEncoder.encode("Duration","UTF-8") + "=" + URLEncoder.encode("set", "UTF-8")
                        ;
               ;
               bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Added";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }


    }
}
