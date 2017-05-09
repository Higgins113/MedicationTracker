package com.example.stephen.medicationtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper

{
    private static final int DATABASE_VERSION = 13;
    public static final String DATABASE_NAME = "medication.db";

    public static final String TABLE_USER = "user";
    public static final String TABLE_MEDICATION = "medication";

    //COMMON COLUMNS
    public static final String COLUMN_USERNAME = "Username";

    //USER TABLE COLUMNS
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_EMAIL = "Email";

    //MEDICATION COLUMNS
    public static final String COLUMN_MEDNAME = "MedName";
    public static final String COLUMN_MEDTYPE = "MedType";
    public static final String COLUMN_INGESTION = "Ingestion";
    public static final String COLUMN_MEDQUANTITY = "MedQuantity";
        public static final String COLUMN_MEDSTARTDATE = "StartDate";
    public static final String COLUMN_MEDDOSAGE = "Dosage";
    public static final String COLUMN_MEDDURATION = "Duration";




    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD
            + " TEXT," + COLUMN_EMAIL + " TEXT" +   ")";

    private static final String CREATE_TABLE_MEDICATION = "CREATE TABLE " + TABLE_MEDICATION +
            "(" + COLUMN_USERNAME + " TEXT," + COLUMN_MEDNAME + " TEXT," + COLUMN_MEDTYPE +
            " TEXT," + COLUMN_INGESTION + " TEXT," + COLUMN_MEDQUANTITY + " TEXT," + COLUMN_MEDSTARTDATE + " TEXT,"
            + COLUMN_MEDDOSAGE + " INTEGER," + COLUMN_MEDDURATION + " TEXT" + ");";



    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
       db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MEDICATION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATION);
        onCreate(db);
    }

    public void addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME,user.getUsername());
        values.put(COLUMN_PASSWORD,user.getPassword());
        values.put(COLUMN_EMAIL, user.getEmail());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addMedication(Medication medication)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME,medication.getUsername());
        values.put(COLUMN_MEDNAME,medication.getMedName());
        values.put(COLUMN_MEDTYPE,medication.getMedType());
        values.put(COLUMN_INGESTION,medication.getIngestion());
        values.put(COLUMN_MEDQUANTITY,medication.getMedQuantity());
        values.put(COLUMN_MEDDOSAGE,medication.getDosage());
        values.put(COLUMN_MEDSTARTDATE,medication.getMedStartDate());
        values.put(COLUMN_MEDDURATION,medication.getDuration());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MEDICATION, null, values);
        db.close();
    }

    public void deleteMedication(Medication medication)
    {
        String username = medication.getUsername();
        String medName = medication.getMedName();
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MEDICATION + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\" AND " + COLUMN_MEDNAME + "=\"" + medName + "\";   ");
        db.close();
    }

    public void reduceSupply(String username, String supply)
    {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_MEDICATION + " SET " + COLUMN_MEDQUANTITY + "=\"" + supply + "\"" + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\";   ");
        db.close();
    }

    public String checkSupply(String username,String medName)
    {
        String query =  "SELECT MEDQUANTITY FROM " + TABLE_MEDICATION + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\" AND " + COLUMN_MEDNAME + "=\"" + medName + "\" ";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        String quantity ="";
        c.moveToFirst();
        //if (c.moveToFirst()) {
                 quantity = c.getString(c.getColumnIndex(COLUMN_MEDQUANTITY));

           // } while (c.moveToNext());

        return quantity;
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setUsername(c.getString(c.getColumnIndex(COLUMN_USERNAME)));
                user.setPassword(c.getString(c.getColumnIndex(COLUMN_PASSWORD)));
                user.setEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
                users.add(user);
            } while (c.moveToNext());
        }

        return users;
    }

    public ArrayList<Medication> getAllMedicationsByUser(String username){
        ArrayList<Medication> medications = new ArrayList<Medication>();
        String query = "SELECT * FROM " + TABLE_MEDICATION + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"; ";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Medication medication = new Medication();
                medication.setMedName(c.getString(c.getColumnIndex(COLUMN_MEDNAME)));
                medication.setMedType(c.getString(c.getColumnIndex(COLUMN_MEDTYPE)));
                medication.setMedQuantity(c.getString(c.getColumnIndex(COLUMN_MEDQUANTITY)));
                medication.setIngestion(c.getString(c.getColumnIndex(COLUMN_INGESTION)));
                medication.setDosage(c.getInt(c.getColumnIndex(COLUMN_MEDDOSAGE)));
                medication.setMedStartDate(c.getString(c.getColumnIndex(COLUMN_MEDSTARTDATE)));
                medication.setDuration(c.getString(c.getColumnIndex(COLUMN_MEDDURATION)));
                medications.add(medication);
            } while (c.moveToNext());
        }

        return medications;
    }



    public Boolean getUser(User user)
    {
        SQLiteDatabase db = getReadableDatabase();
        String username = user.getUsername();
        String password = user.getPassword();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\" AND " + COLUMN_PASSWORD + "=\"" + password + "\"; ";

      //  String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\" + ; ";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToNext();
            return true;
        }

        else{
            //User user = new User();
            return false;
        }
    }

    public void deleteUser(String username)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM" + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\";   ");
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATION);
    }




    public String databaseToString(User user)
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String username = user.getUsername();

        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + " = '" + username + "';";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex("Username"))!=null)
            {
                dbString += c.getString(c.getColumnIndex("Username"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;

    }



    public String databaseToStringPassword(User user)
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String password = user.getPassword();

        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_PASSWORD + " = '" + password + "';";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex("Password"))!=null)
            {
                dbString += c.getString(c.getColumnIndex("Password"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }


}