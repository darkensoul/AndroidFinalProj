package com.example.brittany.hcd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Workout_overview_Details extends AppCompatActivity {
    // Declare Variables
    String routine;
    String objID;
    int reps;
    int duration;

    TextView textView_routine;
    TextView textView_reps;
    TextView textView_duration;

    Button btn_delete;
    boolean del = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_overview__details);

        // Get the parameters passed
        Bundle extras = getIntent().getExtras();

        // Get the routine name, reps, and duration
        routine = extras.getString("Name", "");
        objID = extras.getString("objectId", "");
        reps = extras.getInt("Repetition", 0);
        duration = extras.getInt("Duration", 0);

        // Locate the TextView in singleitemview.xml
        textView_routine = (TextView) findViewById(R.id.routine);
        textView_reps = (TextView) findViewById(R.id.reps);
        textView_duration = (TextView) findViewById(R.id.duration);

        // Load the text into the TextView
        textView_routine.setText(routine);
        textView_reps.setText(String.valueOf(reps));
        textView_duration.setText(String.valueOf(duration));
    }
    public void routine_delete(View view) {

        if(del == true)
        {
            SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String _username = prefs.getString("username","");

            ParseQuery<Temp_Table> tquery = ParseQuery.getQuery("Temp_Table");
            tquery.whereEqualTo("username", _username);
            tquery.whereEqualTo("Name", "Exercise: " + routine);
            tquery.whereEqualTo("Name2", "Repetitions: " + reps);
            tquery.getFirstInBackground(new GetCallback<Temp_Table>() {

                @Override
                public void done(Temp_Table objects, ParseException e) {
                    if (objects == null) {
                        // Toast.makeText(Workout_overview_Details.this, "Not Found", Toast.LENGTH_SHORT).show();
                    } else {

                        objects.deleteInBackground(new DeleteCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Toast.makeText(Workout_overview_Details.this, "Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Toast.makeText(Workout_overview_Details.this, "Not Successful", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });


            // http://stackoverflow.com/questions/24748037/parse-object-is-not-getting-deleted-android
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Add_Exercise");
            query.whereEqualTo("objectId", objID);
            query.getFirstInBackground(new GetCallback<ParseObject>() {

                @Override
                public void done(ParseObject objects, ParseException e) {
                    if (objects == null) {
                        Toast.makeText(Workout_overview_Details.this, "Not Found", Toast.LENGTH_SHORT).show();
                    } else {

                        objects.deleteInBackground(new DeleteCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(Workout_overview_Details.this, "Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Workout_overview_Details.this, "Not Successful", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });

            btn_delete = (Button) findViewById(R.id.button_del);
            btn_delete.setText("DELETED!");
            del = false;
        }
        Intent intent = new Intent(Workout_overview_Details.this, Workout_overview.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout_overview__details, menu);
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
