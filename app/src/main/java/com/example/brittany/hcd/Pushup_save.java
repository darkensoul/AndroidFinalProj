package com.example.brittany.hcd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class Pushup_save extends AppCompatActivity {

    TextView pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup_save);

        pass = (TextView) findViewById(R.id.textView_dbentry);


        // Get the parameters passed
        Bundle extras = getIntent().getExtras();
        String pushdone_string = extras.getString("EXTRA_PUSHDONE");
        int pushgoal_integer = extras.getInt("EXTRA_PUSHGOAL", 0);

        // Display Value
        TextView displayGoal = (TextView) findViewById(R.id.textView_goalSave);
        displayGoal.setText(pushdone_string);

        // Parse Save Data
         ParseObject PushupCount = new ParseObject("PushupCount");

        // Share Preference
        SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String _username = prefs.getString("username","");
        if (_username == "") {
            Toast.makeText(Pushup_save.this, "No username found",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        PushupCount.put("Count", pushdone_string);
        PushupCount.put("username", _username);
        PushupCount.put("Goal", pushgoal_integer);

        // Master Table
        Temp_Table item = new Temp_Table();
        item.setName("Goal: " + String.valueOf(pushgoal_integer)); // Goal
        item.setName2("Achieved: " + pushdone_string); // What you did
        item.setUsername(_username); // Username
        item.setType("Push Up"); // Category

        PushupCount.saveInBackground();
        item.saveInBackground();

        // Check if it saved, will display on the app
        PushupCount.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    // Log.e("PARSE.COM", "FAILED" + e.getMessage());
                    pass.setText("Did not save! ");
                } else {
                    // Log.e("PARSE.COM", "SUCCESS");
                    pass.setText("Has been saved! ");

                }
            }
        });


        if(Integer.parseInt(pushdone_string) > pushgoal_integer) {
            Toast t = Toast.makeText(this, "CONGRATULATIONS YOU BEAT YOUR GOAL!!!", Toast.LENGTH_SHORT);
            t.show();
        }
        if(Integer.parseInt(pushdone_string) == pushgoal_integer)
        {
            Toast t = Toast.makeText(this, "CONGRATULATIONS YOU REACHED YOUR GOAL!!!", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pushup_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
