package com.length6array.donationtracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


/**
 * Hey!
 * This is the Welcome screen! Literally the first thing you see when you open the app
 * It contains two buttons, login and register.
 */
public class Welcome extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //login button. This takes you to the LoginActivity.
        Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Welcome", "Login button clicked");
                startActivity(new Intent(Welcome.this, LoginActivity.class));
            }
        });


        //register button. This takes you to the Registration Activity.
        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Welcome", "Register button clicked");
                startActivity(new Intent(Welcome.this, Registration.class));
            }
        });
    }

    private void loadPersonFromPersonDatabase() {
        Cursor cursor = PersonDBHandler.getAllUsers();


        if (cursor.moveToFirst()) { //if there/s a line to be read
            do {
                Person p =  new Person();
                p.setEmail(cursor.getString(1));
                p.setPassword(cursor.getString(2));
                p.setUserType( cursor.getString(3));


                LoginActivity.allUsers.add(p); //putting into an arraylist to be used now
                LoginActivity.credentials.put(p.getEmail(), p.getPassword()); //into a map to be used for other activities
            } while (cursor.moveToNext());//this line basically just says "do while there's lines to read
        }
    }
}
