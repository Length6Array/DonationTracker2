package com.length6array.donationtracker2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This is a class that handles the DB and SQL functionality for Locations
 */
public class locationDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "locations.db";
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_ZIPCODE = "zipcode";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_WEBSITE = "website";


    public locationDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * This makes the database
     * PLEASE PLEASE PLEASE NOTE THE SPACING OF THINGS. LIKE HOW " TEXT , " IS WRITTEN LIKE
     * THAT AS OPPOSED TO "TEXT,". SQL IS WEIRD AND THIS WILL CAUSE ERRORS IF YOU DON'T PUT SPACING
     * (pretty sure it's bc its using whitespace as a delimitter so it needs those spaces.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_LOCATIONS + "(" +
                COLUMN_NAME + " TEXT ," +
                COLUMN_LATITUDE + " FLOAT , " +
                COLUMN_LONGITUDE + " FLOAT ," +
                COLUMN_ADDRESS + " TEXT ," +
                COLUMN_CITY + " TEXT ," +
                COLUMN_STATE + " TEXT ," +
                COLUMN_ZIPCODE + " INTEGER ," +
                COLUMN_TYPE + " TEXT ," +
                COLUMN_PHONE + " TEXT ," +
                COLUMN_WEBSITE + " TEXT " +
                ") ;";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_LOCATIONS);
        onCreate(db);
    }

    /**
     *add a new row to the database (a new donation)
     *I changed this from the youtube example to the website one that has a return of
     *boolean just so I'd know if it worked when I added to the table
     *
     * method to know wether or not a new location has been added
     * @param location of type Location is the one being checked to see if it has been added
     * @return boolean of whether or not location has been added
     */
    public boolean addLocation(Location location){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, location.getName()); //0
        values.put(COLUMN_LATITUDE, location.getLatitude()); //1
        values.put(COLUMN_LONGITUDE, location.getLongitude()); //2
        values.put(COLUMN_ADDRESS, location.getAddress()); //3
        values.put(COLUMN_CITY, location.getCity()); //4
        values.put(COLUMN_STATE, location.getState()); //5
        values.put(COLUMN_ZIPCODE, location.getZipCode()); //6
        values.put(COLUMN_TYPE, location.getType()); //7
        values.put(COLUMN_PHONE, location.getPhone()); //8
        values.put(COLUMN_WEBSITE, location.getWebsite()); //9
        SQLiteDatabase db = getWritableDatabase();
        //true if accurately added to table
        return  (db.insert(TABLE_LOCATIONS, null, values) != -1);
    }

    //delete product from database

    /**
     * method to tell where to delete a location from
     * @param user
     */
    public void deleteLocation(String user){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LOCATIONS + " WHERE "
                + COLUMN_NAME + "=\"" + user + "\";" );

    }

    /**
     * I got some errors using this in other classes, I have to go back in later and figure out
     * what's going on but if you wanna check what's happening I've been using
     * Log.i("CLASS_NAME DATABASE", cursor.getString(COL #) + "" ) and that works too
     *
     * converts location database selection to string
     * @return the string of the database
     */
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LOCATIONS + " WHERE 1 ";

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
        c.close();
        db.close();
        return dbString;
    }

    Cursor getAllLocations(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_LOCATIONS, null);
    }



}
