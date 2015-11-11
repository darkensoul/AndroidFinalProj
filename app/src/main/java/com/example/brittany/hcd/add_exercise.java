package com.example.brittany.hcd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class add_exercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView routineInputview = (TextView) findViewById(R.id.User_input_exercise);
        SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String _routine = prefs.getString("routine", "");

        routineInputview.setText(_routine);

    }
    public void Exercise_entered(View view){

        // CLICK SAVE AND GO BACK TO ANOTHER SCREEN, GET _name AND PASS IT BACK TO CHANGE THE TEXT

        EditText _name = (EditText) findViewById(R.id.editText_name);
        EditText Reps = (EditText) findViewById(R.id.editText_reps);
        EditText Duration = (EditText) findViewById(R.id.editText_duration);

        // Check for Empty Boxes
        if( TextUtils.isEmpty(_name.getText().toString().trim()) ) {
            _name.setError("Please enter a name for your exercise.");
            return;
        }
        if( TextUtils.isEmpty(Reps.getText().toString().trim()) ) {
            Reps.setError("Please enter the number of repetitions performed.");
            return;
        }
        if( TextUtils.isEmpty(Duration.getText().toString().trim()) ) {
            Duration.setError("Please enter how long the exercise will last.");
            return;
        }

        // Turn reps/duration into integers
        Integer _Reps = Integer.parseInt(Reps.getText().toString());
        Integer _Duration = Integer.parseInt(Duration.getText().toString());

        // Motivation message
        if( (_Reps == 0)  || (_Duration == 0) )
        {
            Toast t = Toast.makeText(this, "You can do more than that. Come on, push it!", Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        // Parse
        ParseObject Add_Exercise = new ParseObject("Add_Exercise");

//        String username = prefs.getString("username", "");
//        Toast.makeText(add_exercise.this, "Username: " + username,
//                Toast.LENGTH_SHORT).show();
//        if (username == "") {
//            Toast.makeText(add_exercise.this, "No username defined",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }
        SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String _username = prefs.getString("username","");
//        String userss = usernameview.getText().toString().trim();
//        Toast t = Toast.makeText(MainActivity.this, "SAVED " + userss + " OTHER TEST SHARED: " + users, Toast.LENGTH_LONG);
//        t.show();

        if (_username == "") {
            Toast.makeText(add_exercise.this, "No username found",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Add_Exercise.put("username", _username);
        Add_Exercise.put("Name", _name.getText().toString().trim());
        Add_Exercise.put("Reps", _Reps);
        Add_Exercise.put("Duration", _Duration);


        // Master Table
        Temp_Table item = new Temp_Table();
        item.setName("Exercise: " + _name.getText().toString().trim()); // Name of Exercise
        item.setName2("Repetitions: " + String.valueOf(_Reps)); // Reps
        item.setUsername(_username); // Username
        item.setType("Exercise"); // Category
        item.saveInBackground();


        // Save the data
        Add_Exercise.saveInBackground();

        // Check if it saved, will display on the app
        Add_Exercise.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    // Log.e("PARSE.COM", "FAILED" + e.getMessage());
                    Toast.makeText(add_exercise.this, "Has not been saved!",
                            Toast.LENGTH_SHORT).show();

                } else {
                    // Log.e("PARSE.COM", "SUCCESS");
                    Toast.makeText(add_exercise.this, "Has been saved!",
                            Toast.LENGTH_SHORT).show();

                    // Go to the back a screen // intent_exercise.putExtra("parameter_name", "WorkoutName Maybe);
                    Intent intent_exercise = new Intent(add_exercise.this, User_add_exercise.class);
                    startActivity(intent_exercise);

                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_exercise, menu);
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
