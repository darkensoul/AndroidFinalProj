package com.example.brittany.hcd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
public class Workout_Main extends AppCompatActivity {


//    String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout__main);


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
        getMenuInflater().inflate(R.menu.menu_workout__main, menu);
        return true;
    }

    public void New_Routine_clicked(View view)
    {
        Intent intent4 = new Intent(this, New_work_Rountine.class);
        startActivity(intent4);
    }
   public void Start_workout_clicked(View view)
   {
       Intent intent5 = new Intent(this, User_add_exercise.class);
//       intent5.putExtra("username", username);
       startActivity(intent5);
   }
    public void overview_clicked(View view)
    {
        Intent intent6 = new Intent(this, Workout_overview.class);
        startActivity(intent6);
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
