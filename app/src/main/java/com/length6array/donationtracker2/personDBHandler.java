package com.length6array.donationtracker2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonDBHandler extends SQLiteOpenHelper {


    /**
     * Hey! This is the database, its actually not that complicated really its just a table of values
     * that correspond to attributes of the Donation class.
     * Hopefully my comments will help you figure out what's going on, otherwise here are the two
     * resources I used to get this thing working
     *
     *
     * https://www.simplifiedcoding.net/sqliteopenhelper-tutorial/#Creating-the-Employee
     * //this was more helpful in learning how to grab the information stored in the database and
     * put it into an arraylist
     *
     * https://www.youtube.com/watch?v=bTWJ8d1hbvw&index=53&list=PL6gx4Cwl9DGBsvRxJJOzG4r4k_zLKrnxl
     * This is the last in a series of short youtube videos I watched in order to set this up
     *
     * Go to DonationActivity to see how I put donations in
     * Go to DonationListActivity to see how I pull these values out
     *
     * Side Note: there will most likely be an error after you make Location database, go to
     * Donation class to setLocation() and I'll explain what's up and how to fix it
     */

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "users.db";
        public static final String TABLE_USERS = "users";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_USERTYPE = "userType";

        public PersonDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
            String query = "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_EMAIL + " TEXT ," +
                    COLUMN_PASSWORD + " TEXT , " +
                    COLUMN_USERTYPE + " TEXT " +
                    ") ;";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE_USERS);
            onCreate(db);
        }

        /**
         *add a new row to the database (a new donation)
         *I changed this from the youtube example to the website one that has a return of
         *boolean just so I'd know if it worked when I added to the table
         */

        public boolean addPerson(Person user){
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, user.getEmail()); //1
            values.put(COLUMN_PASSWORD, user.getPassword()); //2
            values.put(COLUMN_USERTYPE, user.getUserType()); //3
            SQLiteDatabase db = getWritableDatabase();
            //true if accurately added to table
            return  (db.insert(TABLE_USERS, null, values) != -1);
        }

        //delete product from database
        public void deleteUser(String user){
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_USERS + " WHERE "
                    + COLUMN_EMAIL + "=\"" + user + "\";" );

        }

        /**
         * I got some errors using this in other classes, I have to go back in later and figure out
         * what's going on but if you wanna check what's happening I've been using
         * Log.i("CLASS_NAME DATABASE", cursor.getString(COL #) + "" ) and that works too
         */

        public String databaseToString(){
            String dbString = "";
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE 1 ";

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

        Cursor getAllUsers(){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(" SELECT * FROM " + TABLE_USERS, null);
        }

}
