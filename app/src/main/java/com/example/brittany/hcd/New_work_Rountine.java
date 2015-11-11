package com.example.brittany.hcd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

public class New_work_Rountine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_work__rountine);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_work__rountine, menu);
        return true;
    }

    public void Create_click(View view)
    {
        Intent intent_create = new Intent(this, User_add_exercise.class);

        EditText _routine = (EditText) findViewById(R.id.editText_routine);

        // Check for Empty Boxes
        if( TextUtils.isEmpty(_routine.getText().toString().trim()) ) {
            _routine.setError("Please enter a new routine to add to your exercise.");
            return;
        }
        // PREFERENCES FILE and SUCCESSFUL LOGIN
        SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("routine", _routine.getText().toString().trim());
        editor.commit();

        startActivity(intent_create);
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
