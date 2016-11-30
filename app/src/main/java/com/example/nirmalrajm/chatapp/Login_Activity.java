package com.example.nirmalrajm.chatapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import bolts.Task;


public class Login_Activity extends Activity {
    public static final String MY_APPLICATION_ID = "WP74ib9AoAQtnKbJxgz5FkMSQLDq1raIIVyqIqup";
    public static final String MY_CLIENT_KEY = "FUXJl7JcyGP7cJuktrdoBr8WtnpzjpIQ9UK1UWc6";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    EditText logid, pass;
    ImageView logo;
    ImageButton login;
    TextView idmsg, passmsg;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_page);

        login = (ImageButton) findViewById(R.id.login);
        logid = (EditText) findViewById(R.id.logid);

        logo = (ImageView) findViewById(R.id.logo);
        idmsg = (TextView) findViewById(R.id.Errorid);

        logid.getBackground().setColorFilter(getResources().getColor(R.color.background_floating_material_light), android.graphics.PorterDuff.Mode.SRC_ATOP);


        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                if ((!(logid.getText().toString().isEmpty()))) {
                    Toast.makeText(getApplicationContext(), "Registering Please Wait", Toast.LENGTH_SHORT).show();
                    idmsg.setText("");

                    changeactivity();
                } else if ((logid.getText().toString().equals("")) ) {
                    idmsg.setText("Enter username");

                    //Toast.makeText(getApplicationContext(), "Please Enter UserName and Password", Toast.LENGTH_LONG).show();
                } // Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_LONG).show();
                else if (logid.getText().toString().equals("")) {
                    idmsg.setText("Enter username");

                    //   Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Entry", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "TRY AGAIN", Toast.LENGTH_SHORT).show();
                    idmsg.setText("");


                    logid.setText("");
                }
            }
        });}


    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login() {
        ParseUser user = new ParseUser();

       final String newusername=prefs.getString("Username","NULL");
        final String newpassword=prefs.getString("Password","NULL");
        if((!(newusername.equals("NULL")))&&(!(newpassword.equals("NULL")))) {
            user.setUsername(newusername);
            user.setPassword(newpassword);
            user.setEmail("NULL");
        }
// other fields can be set just like with ParseObject
        user.signUpInBackground(new SignUpCallback(){
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    changeactivity(newusername);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Toast.makeText(getApplicationContext(),"Somewthing went wrong Try again Later",Toast.LENGTH_LONG);
                }
            }
        });
    }


    public void changeactivity() {
        Toast.makeText(getApplicationContext(), "Function Call Success", Toast.LENGTH_SHORT).show();
        String str;
        Bundle bundle = new Bundle();
        str = logid.getText().toString();
        bundle.putString("msg", str);
        Intent frag = new Intent(this, newchatpage.class);
        frag.putExtras(bundle);
        startActivity(frag);
    }

    public void changeactivity(String x) {
        Toast.makeText(getApplicationContext(), "Function Call Success", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("msg", x);
        Intent frag = new Intent(this,newchatpage.class);
        frag.putExtras(bundle);
        startActivity(frag);


    }
};



