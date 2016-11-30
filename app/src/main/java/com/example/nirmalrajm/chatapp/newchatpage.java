package com.example.nirmalrajm.chatapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.*;
import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/**
 * Created by Elcot on 9/5/2015.
 */
public class newchatpage extends Activity {
    private static final String TAG = newchatpage.class.getName();
    private static String sUserId;
    Toolbar iclist;
    public static final String USER_ID_KEY = "userId";
    private EditText etMessage;
    private Button btSend;

   private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    public static final String MY_APPLICATION_ID = "WP74ib9AoAQtnKbJxgz5FkMSQLDq1raIIVyqIqup";
    public static final String MY_CLIENT_KEY = "FUXJl7JcyGP7cJuktrdoBr8WtnpzjpIQ9UK1UWc6";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ChatListAdapter mAdapter;
    // Keep track of initial load to scroll to the bottom of the ListView
    private boolean mFirstLoad;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
 ///       iclist = (Toolbar) findViewById(R.id.toolbar);
   //     setSupportActionBar(iclist);
     //   getSupportActionBar().setDisplayShowHomeEnabled(true);
       // User login
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, MY_APPLICATION_ID, MY_CLIENT_KEY);
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            startWithCurrentUser();
        } else { // If not logged in, login as a new anonymous user
            login();
        }
        handler.postDelayed(runnable, 100);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };

    private void refreshMessages() {
        receiveMessage();
    }
    // Get the userId from the cached currentUser object
    private void startWithCurrentUser() {
        sUserId = ParseUser.getCurrentUser().getObjectId();
        setupMessagePosting();
    }
    private void setupMessagePosting() {
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        lvChat = (ListView) findViewById(R.id.lvChat);
        mMessages = new ArrayList<Message>();
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
        mAdapter = new ChatListAdapter(this,sUserId, mMessages);
        lvChat.setAdapter(mAdapter);
        // Create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                ParseObject message = ParseObject.create("Message");
                message.put(USER_ID_KEY, sUserId);
                message.put("body", data);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(newchatpage.this, "Successfully created message on Parse",
                                Toast.LENGTH_SHORT).show();
                    }
                });


                etMessage.setText("");
            }
        });
    }
    private void receiveMessage() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed: " + e.toString());
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }
/*
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
   //             Toast.makeText(getApplicationContext(), "Exit Pressed", Toast.LENGTH_SHORT).show();
     //       }
       //     return true;
         //   default:
           //     return super.onOptionsItemSelected(item);


        //}//
    //}
}