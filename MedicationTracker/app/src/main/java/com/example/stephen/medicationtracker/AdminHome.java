package com.example.stephen.medicationtracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;


public class AdminHome extends AppCompatActivity implements View.OnClickListener{

    Button medicationsDate;
    private DatePicker datePicker;
    int year, month, day;
Calendar calendar;
    ArrayList<String> names = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        medicationsDate = (Button) findViewById(R.id.buttonMedicationsDate);
        medicationsDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonMedicationsDate:
                new DatePickerDialog(this,myDateListener, year, month,day).show();
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int year, int month, int day ) {
                    String startDate = (String.valueOf(day) +"/" +String.valueOf(month + 1) +"/"+ String.valueOf(year) );
                    BackgroundTask backgroundTask = new BackgroundTask();
                       backgroundTask.execute(startDate);

                }
            };


    class BackgroundTask extends AsyncTask<String,Void,String> {

        String add_info_url;

        @Override
        protected void onPreExecute() {
            add_info_url = "http://sample-env-1.s8fqw4jpzp.us-east-1.elasticbeanstalk.com/GetMedication.php";
        }

        @Override
        protected String doInBackground(String... args) {

            String username, medName, medType, medQuantity, ingestion, startDate, finalDosage, duration;
            startDate = args[0];
           // startDate = "2/5/2017";
           /* username = args[0];
            medName = args[1];
            medType = args[2];
            medQuantity = args[3];
            ingestion = args[4];
            startDate = args[5];
            //  finalDosage = args[6];
            //  duration = args[6];*/


            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("StartDate", "UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8");
                ;
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(httpURLConnection.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    names.add(line);
                    sb.append(line);
                    break;
                }
                httpURLConnection.disconnect();
                return sb.toString();
                //InputStream inputStream = httpURLConnection.getInputStream();
                //  inputStream.close();


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

            Bundle b=new Bundle();
            b.putStringArrayList("names", names);
            Intent i = new Intent(getApplicationContext(),Display.class);
            i.putExtras(b);
            startActivity(i);
            startActivity(getIntent());

        }

    }
}
