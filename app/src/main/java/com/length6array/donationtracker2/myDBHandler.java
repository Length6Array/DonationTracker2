package com.length6array.donationtracker2;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.media.Image;

public class myDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "donation.db";
    public static final String TABLE_DONATIONS = "donations";
    public static final String COLUMN_ID = "_id"; //id may be an issue
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_LOCATION = "_location";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_TYPE = "_type";
    public static final String COLUMN_DESCRIPTION = "_description";
    public static final String COLUMN_VALUE = "_value";

    public myDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_DONATIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT ," +
                COLUMN_LOCATION + " TEXT , " +
                COLUMN_DATE + " TEXT ," +
                COLUMN_TYPE + " TEXT ," +
                COLUMN_DESCRIPTION + " TEXT ," +
                COLUMN_VALUE + " TEXT " +
                ") ;";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_DONATIONS);
        onCreate(db);
    }

    //add a new row to the database
    public boolean addDonation(Donation donation){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, donation.getName()); //1
        values.put(COLUMN_LOCATION, donation.getLocation()); //2
        values.put(COLUMN_DATE, donation.getDateAdded()); //3
        values.put(COLUMN_TYPE, donation.getType()); //4
        values.put(COLUMN_DESCRIPTION, donation.getDescription()); //5
        values.put(COLUMN_VALUE, donation.getValue()); //6
        SQLiteDatabase db = getWritableDatabase();
        //true if accurately added to table
       return  (db.insert(TABLE_DONATIONS, null, values) != -1);
    }

    //delete product from database
    public void deleteDonation(String donationName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DONATIONS + " WHERE "
                + COLUMN_NAME + "=\"" + donationName + "\";" );

    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DONATIONS + " WHERE 1";

        //cursor point to a location in results
        Cursor c = db.rawQuery(query, null);
        //move to the first row in results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("_name")) != null){
                dbString += c.getString(c.getColumnIndex("_name"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }

    Cursor getAllDonations(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_DONATIONS, null);
    }




}
