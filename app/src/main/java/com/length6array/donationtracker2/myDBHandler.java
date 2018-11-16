package com.length6array.donationtracker2;

import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Hey! This is the database, its actually not that complicated really its just a table of values
 * that correspond to attributes of the Donation class. Hopefully my comments will help you figure
 * out what's going on, otherwise here are the two resources I used to get this thing working
 *
 * <p>https://www.simplifiedcoding.net/sqliteopenhelper-tutorial/#Creating-the-Employee //this was
 * more helpful in learning how to grab the information stored in the database and put it into an
 * arraylist
 *
 * <p>https://www.youtube.com/watch?v=bTWJ8d1hbvw&index=53&list=PL6gx4Cwl9DGBsvRxJJOzG4r4k_zLKrnxl
 * This is the last in a series of short youtube videos I watched in order to set this up
 *
 * <p>Go to DonationActivity to see how I put donations in Go to DonationListActivity to see how I
 * pull these values out
 *
 * <p>Side Note: there will most likely be an error after you make Location database, go to Donation
 * class to setLocation() and I'll explain what's up and how to fix it
 */
public class myDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "donation.db";
    public static final String TABLE_DONATIONS = "donations";
    public static final String COLUMN_ID = "_id"; // id may be an issue
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_LOCATION = "_location";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_TYPE = "_type";
    public static final String COLUMN_DESCRIPTION = "_description";
    public static final String COLUMN_VALUE = "_value";

    /**
     *
     * @param context Context object
     * @param name String representing name
     * @param factory SQLiteDatabase representing factory
     * @param version int representing version
     */
    public myDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * This makes the database PLEASE PLEASE PLEASE NOTE THE SPACING OF THINGS. LIKE HOW " TEXT , "
     * IS WRITTEN LIKE THAT AS OPPOSED TO "TEXT,". SQL IS WEIRD AND THIS WILL CAUSE ERRORS IF YOU
     * DON'T PUT SPACING (pretty sure it's bc its using whitespace as a delimitter so it needs those
     * spaces.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE "
                        + TABLE_DONATIONS
                        + "("
                        + COLUMN_ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_NAME
                        + " TEXT ,"
                        + COLUMN_LOCATION
                        + " TEXT , "
                        + COLUMN_DATE
                        + " TEXT ,"
                        + COLUMN_TYPE
                        + " TEXT ,"
                        + COLUMN_DESCRIPTION
                        + " TEXT ,"
                        + COLUMN_VALUE
                        + " TEXT "
                        + ") ;";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONATIONS);
        onCreate(db);
    }

    /**
     *add a new row to the database (a new donation)
     *I changed this from the youtube example to the website one that has a return of
     *boolean just so I'd know if it worked when I added to the table
     *
     * @param donation Donation that is added to the Donation table
     * @return Boolean representing whether or not the donation was successfully added
     */
    public boolean addDonation(Donation donation) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, donation.getName()); // 1
        values.put(COLUMN_LOCATION, donation.getLocation()); // 2
        values.put(COLUMN_DATE, donation.getDateAdded()); // 3
        values.put(COLUMN_TYPE, donation.getType()); // 4
        values.put(COLUMN_DESCRIPTION, donation.getDescription()); // 5
        values.put(COLUMN_VALUE, donation.getValue()); // 6
        SQLiteDatabase db = getWritableDatabase();
        // true if accurately added to table
        return (db.insert(TABLE_DONATIONS, null, values) != -1);
    }

    /**
     * This method deletes donations from the Donation database using the donationName
     *
     * @param donationName String representing the donationName of the donation that will be deleted
     */
    public void deleteDonation(String donationName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(
                "DELETE FROM "
                        + TABLE_DONATIONS
                        + " WHERE "
                        + COLUMN_NAME
                        + "=\""
                        + donationName
                        + "\";");
    }

    /**
     * I got some errors using this in other classes, I have to go back in later and figure out
     * what's going on but if you wanna check what's happening I've been using
     * Log.i("CLASS_NAME DATABASE", cursor.getString(COL #) + "" ) and that works too
     *
     *
     * @return String of the database that is being represented
     */
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DONATIONS + " WHERE 1 ";

        // cursor point to a location in results
        Cursor c = db.rawQuery(query, null);
        // move to the first row in results
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("_name")) != null) {
                dbString += c.getString(c.getColumnIndex("_name"));
                dbString += "\n";
            }
        }
        c.close();
        db.close();
        return dbString;
    }

    /**
     *
     * @return Cursor connection to the Donation Table Database
     */
    public Cursor getAllDonations() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_DONATIONS, null);
    }
}
