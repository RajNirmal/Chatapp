package com.example.nirmalrajm.chatapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Elcot on 8/29/2015.
 */

public class chatPage extends AppCompatActivity {
    Toolbar iclist;
    EditText etMessage;
    Button  btSend;
    public static final String MY_APPLICATION_ID = "WP74ib9AoAQtnKbJxgz5FkMSQLDq1raIIVyqIqup";
    public static final String MY_CLIENT_KEY = "FUXJl7JcyGP7cJuktrdoBr8WtnpzjpIQ9UK1UWc6";
    //final SharedPreferences Prefs=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    //String USER_ID_KEY=(Prefs.getString("Username", "NULL"));
    String USER;

    public void onCreate(Bundle savedInstanceState) {
        //ParseCrashReporting.enable(getBaseContext());
        //Parse.enableLocalDatastore(getBaseContext());
//        Parse.initialize(getApplicationContext(), MY_APPLICATION_ID, MY_CLIENT_KEY);
        super.onCreate(savedInstanceState);
        Bundle frag = getIntent().getExtras();
        USER = frag.getString("msg");
        setContentView(R.layout.activity_chat_page);
        //iclist = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(iclist);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        startWithCurrentUser();
    }
        private void startWithCurrentUser() {
            String sUserId = ParseUser.getCurrentUser().getObjectId();
            setupMessagePosting(sUserId);
        }

// Setup button event handler which posts the entered message to Parse
        private void setupMessagePosting(final String sUserId) {
            // Find the text field and button
            etMessage = (EditText) findViewById(R.id.etMessage);
            btSend = (Button) findViewById(R.id.btSend);
            // When send button is clicked, create message object on Parse
            btSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data = etMessage.getText().toString();
                    ParseObject message = ParseObject.create("Message");
                    message.put("USER", sUserId);
                    message.put("body", data);
                    message.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(chatPage.this, "Successfully created message on Parse",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    etMessage.setText("");
                }
            });
        }




    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_chat_page, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.back:
                super.onBackPressed();
                return true;
            case R.id.exit:
            {


                System.gc();
                android.os.Process.killProcess(android.os.Process.myPid());
               /* Bundle bundle=new Bundle();
                bundle.putBoolean("over", true);
                Intent frag=new Intent(this,Login_Activity.class);
                frag.putExtras(bundle);
                startActivity(frag);*/
                Toast.makeText(getApplicationContext(),"Exit Pressed",Toast.LENGTH_SHORT).show();
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}


