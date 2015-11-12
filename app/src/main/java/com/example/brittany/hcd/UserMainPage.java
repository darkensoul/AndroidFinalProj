package com.example.brittany.hcd;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;

import java.util.Random;

public class UserMainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);
        TextView nameview = (TextView) findViewById(R.id.username_text);

        SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String _username = prefs.getString("username", "");
        // MIGHT NOT NEED
//        if (_username == "") {
//            Toast.makeText(UserMainPage.this, "No username found",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }
        nameview.setText(_username);
    }
    @Override
    protected void onResume() {
        super.onResume();
//        // Get the intent with the extra parameter
//        Intent intent = getIntent();
//        username = intent.getStringExtra("username");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_main_page, menu);
        return true;


    }

    public void Symptoms_clicked(View view)
    {
        Intent intent_Symptoms = new Intent(this, Symptoms_Main.class);
        startActivity(intent_Symptoms);
    }

    public void Workout_clicked(View view)
    {
        Intent intent_workout = new Intent(this, Workout_Main.class);
//        intent_workout.putExtra("username", username);
        startActivity(intent_workout);
    }
    public void Food_diary_clicked(View view)
    {
        Intent intent_food = new Intent(this, Food_Diary_Takeimage.class);
        startActivity(intent_food);
    }
    public void Report_clicked(View view)
    {
        Intent intent_Report = new Intent(this, Report_main.class);
        startActivity(intent_Report);
    }
    public void Logout_clicked(View view)
    {
        Intent intent_logout = new Intent(this, MainActivity.class);
        startActivity(intent_logout);
    }
    public void Heartrate_clicked(View view)
    {
        Intent intent_heart =new Intent(this,Heartrate_details.class);
        startActivity(intent_heart);
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
